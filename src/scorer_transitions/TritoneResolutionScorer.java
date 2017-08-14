package scorer_transitions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import chord_data.ChordWithContext;
import chords.Chord;
import music.BasicInterval;
import music.BasicNote;
import music.Interval;
import score_data.TransitionPenaltyType;
import score_data.TransitionScore;
import solver.SolverUtils;

public class TritoneResolutionScorer implements TransitionScorer {
    
    private static final BasicInterval AUG_4TH = new BasicInterval(3,6);
    private static final BasicInterval DIM_5TH = new BasicInterval(4,6); 
    private static final Set<BasicInterval> TRITONES = new HashSet<>(Arrays.asList(
        AUG_4TH, DIM_5TH
    ));
    
    private static boolean isValidTritoneResolution(
        BasicInterval interval, Interval lowerMove, Interval upperMove
    ){
        if (interval.equals(AUG_4TH)) {
            // should resolve outwards to maj/min 6th
            return (
                lowerMove.getScaleDegrees() == -1 && 
                upperMove.getScaleDegrees() == 1
            );
        } else if (interval.equals(DIM_5TH)) {
            // should resolve inwards to maj/min 3rd
            return (
                lowerMove.getScaleDegrees() == 1 && 
                upperMove.getScaleDegrees() == -1
            );
        } else {
            throw new RuntimeException("Should not get here.");
        }
    }

    @Override
    public TransitionScore scoreTransition(ChordWithContext previous, ChordWithContext current) {
        TransitionScore score = new TransitionScore();
        Chord prevChord = previous.getChord();
        List<BasicNote> prevSpelled = SolverUtils.spellBasicNotes(prevChord);
        List<Interval> movements = SolverUtils.getVoiceMovements(
            prevChord, current.getChord()
        );
        for (int upper=0; upper<3; upper++) {
            for (int lower=upper+1; lower<4; lower++) {
                BasicInterval interval = BasicInterval.intervalBetween(
                    prevSpelled.get(lower), prevSpelled.get(upper)
                );
                if (TRITONES.contains(interval)) {
                    if (! isValidTritoneResolution(
                        interval, movements.get(lower), movements.get(upper)
                    )){
                        score.addPenalty(TransitionPenaltyType.BAD_TRITONE_RESOLUTION);
                    }
                }
            }
        }
        return score;
        
    }

}
