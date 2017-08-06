package scorer_transitions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import chord_data.ChordWithContext;
import chord_data.ContextTag;
import music.Interval;
import score_data.TransitionPenaltyType;
import score_data.TransitionScore;
import solver.SolverUtils;

public class CadenceMovementScorer implements TransitionScorer {

    private static Set<ContextTag> CADENCE_TAGS = new HashSet<>(
        Arrays.asList(
            ContextTag.CADENTIAL_PREDOMINANT,
            ContextTag.CADENTIAL_I64,
            ContextTag.CADENTIAL_V,
            ContextTag.CADENCE
        )
    );
    
    private static final int WHOLE_STEP = 1;
    
    private static boolean containsCadenceTag(Set<ContextTag> tags) {
        return (SolverUtils.intersect(tags, CADENCE_TAGS).size() > 0);
    }

    @Override
    public TransitionScore scoreTransition(ChordWithContext previous, ChordWithContext current) {
        TransitionScore score = new TransitionScore();
        if (
            containsCadenceTag(previous.getContextTags()) && 
            containsCadenceTag(previous.getContextTags())
        ) {
            List<Interval> movements = SolverUtils.getVoiceMovements(
                previous.getChord(), current.getChord()
            );
            for (int i=0; i<3; i++) { // all voices except bass
                if (Math.abs(movements.get(i).getScaleDegrees()) > WHOLE_STEP) {
                    score.addPenalty(TransitionPenaltyType.MOVE_BIG_CADENCE);
                }
            }
        }
        return score;
    }

}
