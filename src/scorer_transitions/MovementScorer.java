package scorer_transitions;

import java.util.List;

import chord_data.ChordWithContext;
import music.Interval;
import score_data.TransitionPenaltyType;
import score_data.TransitionScore;
import solver.SolverUtils;

public class MovementScorer implements TransitionScorer {
    
    private static final int THIRD = 2;
    private static final int FOURTH = 3;
    private static final int FIFTH = 4;
    private static final int OCTAVE = 7; 

    @Override
    public TransitionScore scoreTransition(ChordWithContext previous, ChordWithContext current) {
        TransitionScore score = new TransitionScore();
        List<Interval> movements = SolverUtils.getVoiceMovements(
            previous.getChord(), current.getChord()
        );
        Interval movementBass = movements.get(3);
        if (Math.abs(movementBass.getScaleDegrees()) > OCTAVE) {
            score.addPenalty(TransitionPenaltyType.MOVE_BIG_BASS);
        }
        for (int i=0; i<3; i++){ // non-bass voices
            int moveScaleDegrees = Math.abs(movements.get(i).getScaleDegrees());
            if (moveScaleDegrees == THIRD) {
                score.addPenalty(TransitionPenaltyType.MOVE_THIRD);
            } else if (moveScaleDegrees == FOURTH) {
                score.addPenalty(TransitionPenaltyType.MOVE_FOURTH);
            } else if (moveScaleDegrees == FIFTH) {
                score.addPenalty(TransitionPenaltyType.MOVE_FIFTH);
            } else if (moveScaleDegrees > FIFTH) {
                score.addPenalty(TransitionPenaltyType.MOVE_BIG);
            }
        }
        return score;
    }

}
