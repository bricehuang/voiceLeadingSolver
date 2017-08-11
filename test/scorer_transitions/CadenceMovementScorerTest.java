package scorer_transitions;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import chord_data.ChordWithContext;
import score_data.TransitionPenaltyType;
import test_framework.MusicTestFramework;

public class CadenceMovementScorerTest extends MusicTestFramework {
        
    private Map<TransitionPenaltyType, Integer> computePenalties(
        ChordWithContext previous, ChordWithContext current
    ) {
        return computeTransitionPenalties(
            new CadenceMovementScorer(), previous, current
        );
    }

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
        
    }

    @Test
    public void testCadenceMovementGood(){
        ChordWithContext previous = makeChordWithContext(
            B4, D4, G3, G2, G_MAJ_ROOT, C_MINOR, CADENTIAL_V
        );
        ChordWithContext current = makeChordWithContext(
            C5, Eb4, G3, C3, C_MIN_ROOT, C_MINOR, CADENCE
        );
        
        Map<TransitionPenaltyType, Integer> penaltyCount = computePenalties(
            previous, current
        );
                
        assertEquals(0, penaltyCount.keySet().size());
    }
    
    @Test
    public void testCadenceMovementBad(){
        ChordWithContext previous = makeChordWithContext(
            F5, Ab4, F4, F3, F_MIN_ROOT, C_MINOR, CADENTIAL_PREDOMINANT
        );
        ChordWithContext current = makeChordWithContext(
            D5, B4, D4, G3, G_MAJ_ROOT, C_MINOR, CADENTIAL_V
        );
        
        Map<TransitionPenaltyType, Integer> penaltyCount = computePenalties(
            previous, current
        );
                
        assertEquals(1, penaltyCount.keySet().size());
        assertEquals((Integer) 2, penaltyCount.get(MOVE_BIG_CADENCE));
    }

}
