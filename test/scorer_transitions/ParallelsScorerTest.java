package scorer_transitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import chord_data.ChordWithContext;
import score_data.TransitionPenaltyType;
import test_framework.MusicTestFramework;

public class ParallelsScorerTest extends MusicTestFramework {

    private Map<TransitionPenaltyType, Integer> computePenalties(
        ChordWithContext previous, ChordWithContext current
    ) {
        return computeTransitionPenalties(
            new ParallelsScorer(), previous, current
        );
    }

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;   
    }

    @Test
    public void testParallelsGood(){
        ChordWithContext previous = makeChordWithContext(
            C5, G4, E4, G3, C_MAJ_64, C_MAJOR, NO_CONTEXTS
        );
        ChordWithContext current = makeChordWithContext(
            B4, F4, D4, G3, G_DOM7_ROOT, C_MAJOR, NO_CONTEXTS
        );
        Map<TransitionPenaltyType, Integer> penaltyCount = 
            computePenalties(previous, current);        
        assertEquals(0, penaltyCount.keySet().size());
    }

    @Test
    public void testParallelsBad(){
        ChordWithContext previous = makeChordWithContext(
            F5, A4, D4, F3, D_MIN_6, C_MAJOR, NO_CONTEXTS
        );
        ChordWithContext current = makeChordWithContext(
            E5, G4, C4, C3, C_MAJ_ROOT, C_MAJOR, NO_CONTEXTS
        );
        Map<TransitionPenaltyType, Integer> penaltyCount = 
            computePenalties(previous, current);        
        assertEquals(1, penaltyCount.keySet().size());
        assertTrue(penaltyCount.get(PARALLEL) == 1);
    }

    @Test
    public void testParallelsExtraBad(){
        ChordWithContext previous = makeChordWithContext(
            F5, A4, D4, D3, D_MIN_ROOT, C_MAJOR, NO_CONTEXTS
        );
        ChordWithContext current = makeChordWithContext(
            E5, G4, C4, C3, C_MAJ_ROOT, C_MAJOR, NO_CONTEXTS
        );
        Map<TransitionPenaltyType, Integer> penaltyCount = 
            computePenalties(previous, current);        
        assertEquals(1, penaltyCount.keySet().size());
        assertTrue(penaltyCount.get(PARALLEL) == 3);
    }

}
