package scorer_transitions;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import chord_data.ChordWithContext;
import score_data.TransitionPenaltyType;
import test_framework.MusicTestFramework;

public class DirectsScorerTest extends MusicTestFramework {

    private Map<TransitionPenaltyType, Integer> computePenalties(
        ChordWithContext previous, ChordWithContext current
    ) {
        return computeTransitionPenalties(
            new DirectsScorer(), previous, current
        );
    }

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;   
    }

    @Test
    public void testDirectsBad(){
        ChordWithContext previous = makeChordWithContext(
            G4, D4, G3, B2, G_MAJ_6, C_MAJOR, NO_CONTEXTS
        );
        ChordWithContext current = makeChordWithContext(
            C5, E4, G3, C3, C_MAJ_ROOT, C_MAJOR, NO_CONTEXTS
        );
        Map<TransitionPenaltyType, Integer> penaltyCount = 
            computePenalties(previous, current);
        assertEquals(1, penaltyCount.keySet().size());
        assertEquals((Integer) 1, penaltyCount.get(DIRECT));
    }

    @Test
    public void testDirectsGood(){
        ChordWithContext previous = makeChordWithContext(
            B4, D4, G3, G2, G_MAJ_ROOT, C_MAJOR, NO_CONTEXTS
        );
        ChordWithContext current = makeChordWithContext(
            C5, E4, G3, C3, C_MAJ_ROOT, C_MAJOR, NO_CONTEXTS
        );
        Map<TransitionPenaltyType, Integer> penaltyCount = 
            computePenalties(previous, current);
        assertEquals(0, penaltyCount.keySet().size());
    }

}
