package scorer_transitions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import chord_data.ChordWithContext;
import music.BasicInterval;
import music.Interval;
import music.IntervalQuality;
import score_data.TransitionPenaltyType;
import score_data.TransitionScoreNew;
import solver.SolverUtils;

public class MelodicIntervalScorer implements TransitionScorer {

    private static final BasicInterval SEMITONE = new BasicInterval(0,1);
    private static final Interval SEMITONE_UP = new Interval(SEMITONE, 0, true);
    private static final Interval SEMITONE_DOWN = new Interval(SEMITONE, 0, false);
    
    private static final Set<Interval> SEMITONE_STEPS = new HashSet<>(
        Arrays.asList(SEMITONE_UP, SEMITONE_DOWN)
    );
    private static final Set<IntervalQuality> ACCEPTABLE_QUALITIES = new HashSet<>(
        Arrays.asList(IntervalQuality.MAJ, IntervalQuality.MIN, IntervalQuality.PFT)
    );

    @Override
    public TransitionScoreNew scoreTransition(
        ChordWithContext previous, ChordWithContext current
    ){
        TransitionScoreNew score = new TransitionScoreNew();
        List<Interval> movements = SolverUtils.getVoiceMovements(
            previous.getChord(), current.getChord()
        );
        for (Interval movement : movements) {
            // Half-steps (``augmented unisons") are fine.  All other melodic intervals 
            // should be major, minor, or perfect.   
            boolean isSemitoneStep = SEMITONE_STEPS.contains(movement);
            boolean qualityGood = ACCEPTABLE_QUALITIES.contains(movement.getQuality());
            if (! (isSemitoneStep || qualityGood)) {
                score.addPenalty(TransitionPenaltyType.MELODIC_INTERVAL);
            }
        }
        return score;
    }

}
