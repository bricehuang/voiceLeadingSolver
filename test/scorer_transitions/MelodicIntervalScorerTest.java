package scorer_transitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import chord_data.ChordWithContext;
import chords.Chord;
import score_data.TransitionPenaltyType;
import test_framework.MusicTestFramework;

public class MelodicIntervalScorerTest extends MusicTestFramework {

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
        
    }

    @Test
    public void testMelodicIntervalGood(){
        Chord previous = new Chord(Ab4, F4, C4, F2, F_MIN_ROOT);
        ChordWithContext previousContext = new ChordWithContext(
            previous, C_MAJOR, NO_CONTEXTS
        );
        Chord current = new Chord(G4, D4, B3, G2, G_MAJ_ROOT);
        ChordWithContext currentContext = new ChordWithContext(
            current, C_MAJOR, NO_CONTEXTS
        );
        Map<TransitionPenaltyType, Integer> penaltyCounts = 
            new MelodicIntervalScorer()
            .scoreTransition(previousContext, currentContext)
            .getPenaltyCount();
        assertEquals(0, penaltyCounts.keySet().size());
    }    
    
    @Test
    public void testMelodicIntervalBad(){
        Chord previous = new Chord(Ab4, F4, C4, F2, F_MIN_ROOT);
        ChordWithContext previousContext = new ChordWithContext(
            previous, C_MAJOR, NO_CONTEXTS
        );
        Chord current = new Chord(B4, D4, G3, G2, G_MAJ_ROOT);
        ChordWithContext currentContext = new ChordWithContext(
            current, C_MAJOR, NO_CONTEXTS
        );
        Map<TransitionPenaltyType, Integer> penaltyCounts = 
            new MelodicIntervalScorer()
            .scoreTransition(previousContext, currentContext)
            .getPenaltyCount();
        assertEquals(1, penaltyCounts.keySet().size());
        assertTrue(penaltyCounts.get(MELODIC_INTERVAL) == 1);
    }

}
