package scorer_transitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import chord_data.ChordWithContext;
import score_data.TransitionPenaltyType;
import test_framework.MusicTestFramework;

public class MelodicIntervalScorerTest extends MusicTestFramework {

    private Map<TransitionPenaltyType, Integer> computePenalties(
        ChordWithContext previous, ChordWithContext current
    ) {
        return MusicTestFramework.computeTransitionPenalties(
            new MelodicIntervalScorer(), previous, current
        );
    }
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
        
    }

    @Test
    public void testMelodicIntervalGood(){
        ChordWithContext previous = MusicTestFramework.makeChordWithContext(
            Ab4, F4, C4, F2, F_MIN_ROOT, C_MAJOR, NO_CONTEXTS
        );
        ChordWithContext current = MusicTestFramework.makeChordWithContext(
            G4, D4, B3, G2, G_MAJ_ROOT, C_MAJOR, NO_CONTEXTS
        );
        Map<TransitionPenaltyType, Integer> penaltyCounts = 
            computePenalties(previous, current);
        
        assertEquals(0, penaltyCounts.keySet().size());
    }    
    
    @Test
    public void testMelodicIntervalBad(){
        ChordWithContext previous = MusicTestFramework.makeChordWithContext(
            Ab4, F4, C4, F2, F_MIN_ROOT, C_MAJOR, NO_CONTEXTS
        );
        ChordWithContext current = MusicTestFramework.makeChordWithContext(
            B4, D4, G3, G2, G_MAJ_ROOT, C_MAJOR, NO_CONTEXTS
        );
        Map<TransitionPenaltyType, Integer> penaltyCounts = 
            computePenalties(previous, current);
        
        assertEquals(1, penaltyCounts.keySet().size());
        assertTrue(penaltyCounts.get(MELODIC_INTERVAL) == 1);
    }

}
