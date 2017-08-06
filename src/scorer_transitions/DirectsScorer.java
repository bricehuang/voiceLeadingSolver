package scorer_transitions;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import chord_data.ChordWithContext;
import music.BasicInterval;
import music.Interval;
import score_data.TransitionPenaltyType;
import score_data.TransitionScore;
import solver.SolverUtils;

public class DirectsScorer implements TransitionScorer {
    
    private static final BasicInterval UNISON = new BasicInterval(0,0);
    private static final BasicInterval PERFECT_FIFTH = new BasicInterval(4,7);
    private static final Set<BasicInterval> PERFECT_INTERVALS = 
        Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList(UNISON, PERFECT_FIFTH))
        );
    
    @Override
    public TransitionScore scoreTransition(
        ChordWithContext previous, ChordWithContext current
    ) {
        TransitionScore score = new TransitionScore();
        BasicInterval bassSopranoInterval = BasicInterval.intervalBetween(
            current.getChord().getBass().getBasicNote(), 
            current.getChord().getSoprano().getBasicNote()
        ); 
        // only relevant when current bass/soprano form perfect interval  
        if (! PERFECT_INTERVALS.contains(bassSopranoInterval)) {
            return score;
        }

        List<Interval> movements = SolverUtils.getVoiceMovements(
            previous.getChord(), current.getChord()
        );
        Interval sopranoMovement = movements.get(0);
        Interval bassMovement = movements.get(3);

        int sopranoSteps = sopranoMovement.getIncreasing() ? 
            sopranoMovement.getScaleDegrees() : 
            -sopranoMovement.getScaleDegrees(); 
        int bassSteps = bassMovement.getIncreasing() ? 
            bassMovement.getScaleDegrees() : 
            -bassMovement.getScaleDegrees();

        // Badness condition: soprano and bass move same direction, 
        // soprano by leap
        if (sopranoSteps*bassSteps > 0 && Math.abs(sopranoSteps) > 1) {
            score.addPenalty(TransitionPenaltyType.DIRECT);
        }
        return score;
    }

}
