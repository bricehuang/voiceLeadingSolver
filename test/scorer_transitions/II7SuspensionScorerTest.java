package scorer_transitions;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import chord_data.ChordWithContext;
import score_data.TransitionPenaltyType;
import test_framework.MusicTestFramework;

public class II7SuspensionScorerTest extends MusicTestFramework {
    
    private Map<TransitionPenaltyType, Integer> computePenalties(
        ChordWithContext previous, ChordWithContext current
    ) {
        return computeTransitionPenalties(
            new II7SuspensionScorer(), previous, current
        );
    }
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
        
    }

    @Test
    public void testII7SuspensionMajorGood() {
        ChordWithContext previous = makeChordWithContext(
            C5, D4, A3, F2, D_MIN7_65, C_MAJOR, CADENTIAL_PREDOMINANT
        );
        ChordWithContext current = makeChordWithContext(
            C5, E4, G3, G2, C_MAJ_64, C_MAJOR, CADENTIAL_I64
        );
        Map<TransitionPenaltyType, Integer> penaltyCount = 
            computePenalties(previous, current);        
        assertEquals(0, penaltyCount.keySet().size());
    }

    @Test
    public void testII7SuspensionMinorGood() {
        ChordWithContext previous = makeChordWithContext(
            C5, D4, A3, F2, D_MIN7_65, C_MINOR, CADENTIAL_PREDOMINANT
        );
        ChordWithContext current = makeChordWithContext(
            C5, Eb4, G3, G2, C_MIN_64, C_MINOR, CADENTIAL_I64
        );
        Map<TransitionPenaltyType, Integer> penaltyCount = 
            computePenalties(previous, current);        
        assertEquals(0, penaltyCount.keySet().size());
    }
    
    
    @Test
    public void testII7SuspensionBad(){
        ChordWithContext previous = makeChordWithContext(
            D5, A4, C4, F2, D_MIN7_65, C_MAJOR, CADENTIAL_PREDOMINANT
        );
        ChordWithContext current = makeChordWithContext(
            C5, G4, E4, G2, C_MAJ_64, C_MAJOR, CADENTIAL_I64
        );
        Map<TransitionPenaltyType, Integer> penaltyCount = 
            computePenalties(previous, current);        
        assertEquals(1, penaltyCount.keySet().size());
        assertEquals((Integer) 1, penaltyCount.get(CADENTIAL_II7_SUSPEND));
    }    
    
    @Test
    public void testII7SuspensionNotCadential(){
        ChordWithContext previous = makeChordWithContext(
            D5, A4, C4, F2, D_MIN7_65, C_MAJOR, NO_CONTEXTS
        );
        ChordWithContext current = makeChordWithContext(
            C5, G4, E4, G2, C_MAJ_64, C_MAJOR, NO_CONTEXTS
        );
        Map<TransitionPenaltyType, Integer> penaltyCount = 
            computePenalties(previous, current);        
        assertEquals(0, penaltyCount.keySet().size());
    }    

    @Test(expected=AssertionError.class)
    public void testII7SuspensionSanityChecks(){
        ChordWithContext previous = makeChordWithContext(
            D5, A4, C4, F2, D_MIN7_65, C_MINOR, CADENTIAL_PREDOMINANT
        );
        ChordWithContext current = makeChordWithContext(
            C5, G4, E4, G2, C_MAJ_64, C_MINOR, CADENTIAL_I64
        ); 
        new II7SuspensionScorer().scoreTransition(previous, current);        
    }    

}
