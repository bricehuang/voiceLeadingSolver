package scorer_transitions;

import chord_data.ChordWithContext;
import score_data.TransitionScoreNew;

public class MelodicIntervalScorer implements TransitionScorer {

    @Override
    public TransitionScoreNew scoreTransition(ChordWithContext previous, ChordWithContext current) {
        throw new RuntimeException("Unimplemented");
    }

}
