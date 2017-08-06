package scorer_transitions;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import chord_data.ChordWithContext;
import music.BasicInterval; 
import music.BasicNote;
import score_data.TransitionPenaltyType;
import score_data.TransitionScore;
import solver.SolverUtils;

public class ParallelsScorer implements TransitionScorer {
    
    private static final BasicInterval UNISON = new BasicInterval(0,0);
    private static final BasicInterval PERFECT_FIFTH = new BasicInterval(4,7);
    private static final Set<BasicInterval> PERFECT_INTERVALS = 
        Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList(UNISON, PERFECT_FIFTH))
        );

    private boolean isParallelPerfectInterval(
        BasicNote previousLower,
        BasicNote previousUpper,
        BasicNote currentLower,
        BasicNote currentUpper
    ){
        BasicInterval previousInterval = BasicInterval.intervalBetween(
            previousLower, previousUpper
        );
        BasicInterval currentInterval = BasicInterval.intervalBetween(
            currentLower, currentUpper
        );
        return ( previousInterval.equals(currentInterval) && 
                 PERFECT_INTERVALS.contains(currentInterval) && 
                 ! previousLower.equals(currentLower) );
    }

    @Override
    public TransitionScore scoreTransition(
        ChordWithContext previous, ChordWithContext current
    ) {
        TransitionScore score = new TransitionScore();
        List<BasicNote> previousNotes = SolverUtils.spellBasicNotes(
            previous.getChord()
        );
        List<BasicNote> currentNotes = SolverUtils.spellBasicNotes(
            current.getChord()
        );
        for (int upper=0; upper<3; upper++) {
            for (int lower=upper+1; lower<4; lower++) {
                if (isParallelPerfectInterval(
                    previousNotes.get(lower), previousNotes.get(upper),
                    currentNotes.get(lower), currentNotes.get(upper)
                )){
                    score.addPenalty(TransitionPenaltyType.PARALLEL);
                }
            }
        }
        return score;
    }

}
