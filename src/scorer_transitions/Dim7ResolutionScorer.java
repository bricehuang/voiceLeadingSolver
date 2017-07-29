package scorer_transitions;

import java.util.Map;

import chord_data.ChordWithContext;
import score_data.TransitionPenaltyType;

public class Dim7ResolutionScorer implements TransitionScorer {

    @Override
    public Map<TransitionPenaltyType, Integer> scoreTransition(ChordWithContext previous, ChordWithContext current) {
        throw new RuntimeException("Unimplemented");
    }

}
