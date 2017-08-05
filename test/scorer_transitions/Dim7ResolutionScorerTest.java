package scorer_transitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import chord_data.ChordWithContext;
import score_data.TransitionPenaltyType;
import test_framework.MusicTestFramework;

public class Dim7ResolutionScorerTest extends MusicTestFramework {

    private Map<TransitionPenaltyType, Integer> computePenalties(
        ChordWithContext previous, ChordWithContext current
    ) {
        return computeTransitionPenalties(
            new Dim7ResolutionScorer(), previous, current
        );
    }

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
        
    }
    
    @Test
    public void testDimSevenGood1(){
        ChordWithContext previous = makeChordWithContext(
            Ab4, F4, B3, D3, B_DIM7_65, C_MAJOR, NO_CONTEXTS
        );
        ChordWithContext current = makeChordWithContext(
            G4, E4, C4, E4, C_MAJ_6, C_MAJOR, NO_CONTEXTS
        );
        Map<TransitionPenaltyType, Integer> penaltyCount = 
            computePenalties(previous, current);
        assertEquals(0, penaltyCount.keySet().size());
    }
    
    @Test
    public void testDimSevenGood2(){
        ChordWithContext previous = makeChordWithContext(
            F4, D4, B3, Ab2, B_DIM7_42, C_MAJOR, NO_CONTEXTS
        );
        ChordWithContext current = makeChordWithContext(
            E4, E4, C4, G2, C_MAJ_64, C_MAJOR, NO_CONTEXTS
        );
        Map<TransitionPenaltyType, Integer> penaltyCount = 
            computePenalties(previous, current);
        assertEquals(0, penaltyCount.keySet().size());        
    }

    
    @Test
    public void testDimSevenBad1(){
        ChordWithContext previous = makeChordWithContext(
            Ab4, F4, B3, D3, B_DIM7_65, C_MAJOR, NO_CONTEXTS
        );
        ChordWithContext current = makeChordWithContext(
            G4, E4, C4, C4, C_MAJ_ROOT, C_MAJOR, NO_CONTEXTS
        );
        Map<TransitionPenaltyType, Integer> penaltyCount = 
            computePenalties(previous, current);
        assertEquals(1, penaltyCount.keySet().size());
        assertTrue(penaltyCount.get(DIM_SEVEN_RES) == 1);
    }
    
    @Test
    public void testDimSevenBad2(){
        ChordWithContext previous = makeChordWithContext(
            D5, Ab4, B3, F3, B_DIM7_43, C_MAJOR, NO_CONTEXTS
        );
        ChordWithContext current = makeChordWithContext(
            C5, G4, C4, E3, C_MAJ_6, C_MAJOR, NO_CONTEXTS
        );
        Map<TransitionPenaltyType, Integer> penaltyCount = 
            computePenalties(previous, current);
        assertEquals(1, penaltyCount.keySet().size());
        assertTrue(penaltyCount.get(DIM_SEVEN_RES) == 1);
    }    
    
    @Test
    public void testDimSevenNotDimSeven(){
        ChordWithContext previous = makeChordWithContext(
            D5, B4, E4, Gs3, E_DOM7_65, A_MINOR, NO_CONTEXTS
        );
        ChordWithContext current = makeChordWithContext(
            E5, C5, E4, A3, A_MIN_ROOT, A_MINOR, NO_CONTEXTS
        );
        Map<TransitionPenaltyType, Integer> penaltyCount = 
            computePenalties(previous, current);
        assertEquals(0, penaltyCount.keySet().size());
    }
    
}
