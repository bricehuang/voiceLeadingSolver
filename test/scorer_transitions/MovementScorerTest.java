package scorer_transitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import chord_data.ChordWithContext;
import score_data.TransitionPenaltyType;
import test_framework.MusicTestFramework;

public class MovementScorerTest extends MusicTestFramework {
    
    private Map<TransitionPenaltyType, Integer> computePenalties(
        ChordWithContext previous, ChordWithContext current
    ) {
        return computeTransitionPenalties(
            new MovementScorer(), previous, current
        );
    }

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
        
    }

    @Test
    public void testMovement1(){
        ChordWithContext previous = makeChordWithContext(
            B4, G4, D4, G2, G_MAJ_ROOT, C_MAJOR, NO_CONTEXTS
        );
        ChordWithContext current = makeChordWithContext(
            F4, C4, C4, A2, F_MAJ_6, C_MAJOR, NO_CONTEXTS
        );
        Map<TransitionPenaltyType, Integer> penaltyCount = computePenalties(
            previous, current
        );
        
        assertEquals(2, penaltyCount.keySet().size());
        assertTrue(penaltyCount.get(MOVE_FOURTH) == 1);
        assertTrue(penaltyCount.get(MOVE_FIFTH) == 1);
    }

    @Test
    public void testMovement2(){
        ChordWithContext previous = makeChordWithContext(
            B4, G4, D4, G2, G_MAJ_ROOT, C_MAJOR, NO_CONTEXTS
        );
        ChordWithContext current = makeChordWithContext(
            C5, G4, E4, C4, C_MAJ_ROOT, C_MAJOR, NO_CONTEXTS
        );
        Map<TransitionPenaltyType, Integer> penaltyCount = computePenalties(
            previous, current
        );
        
        assertEquals(1, penaltyCount.keySet().size());
        assertTrue(penaltyCount.get(MOVE_BIG_BASS) == 1);
    }
    
    @Test
    public void testMovement3(){
        ChordWithContext previous = makeChordWithContext(
            B4, G4, D4, G3, G_MAJ_ROOT, C_MAJOR, NO_CONTEXTS
        );
        ChordWithContext current = makeChordWithContext(
            G5, C5, E4, C4, C_MAJ_ROOT, C_MAJOR, NO_CONTEXTS
        );
        Map<TransitionPenaltyType, Integer> penaltyCount = computePenalties(
            previous, current
        );
        
        assertEquals(2, penaltyCount.keySet().size());
        assertTrue(penaltyCount.get(MOVE_FOURTH) == 1);
        assertTrue(penaltyCount.get(MOVE_BIG) == 1);
    }
    
}
