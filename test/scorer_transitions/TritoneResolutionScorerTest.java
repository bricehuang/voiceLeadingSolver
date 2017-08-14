package scorer_transitions;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import chord_data.ChordWithContext;
import score_data.TransitionPenaltyType;
import test_framework.MusicTestFramework;

public class TritoneResolutionScorerTest extends MusicTestFramework {

    private Map<TransitionPenaltyType, Integer> computePenalties(
        ChordWithContext previous, ChordWithContext current
    ) {
        return computeTransitionPenalties(
            new TritoneResolutionScorer(), previous, current
        );
    }

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
        
    }

    @Test
    public void testTritoneInDim7Good(){
        ChordWithContext previous = makeChordWithContext(
            Ab4, F4, B3, D3, B_DIM7_65, C_MAJOR, NO_CONTEXTS
        );
        ChordWithContext current = makeChordWithContext(
            G4, E4, C4, E3, C_MAJ_6, C_MAJOR, NO_CONTEXTS
        );
        Map<TransitionPenaltyType, Integer> penaltyCount = 
            computePenalties(previous, current);
        assertEquals(0, penaltyCount.keySet().size());
    }

    @Test
    public void testTritoneInDim7Bad(){
        ChordWithContext previous = makeChordWithContext(
            D5, Ab4, B3, F3, B_DIM7_43, C_MAJOR, NO_CONTEXTS
        );
        ChordWithContext current = makeChordWithContext(
            C5, G4, C4, E3, C_MAJ_6, C_MAJOR, NO_CONTEXTS
        );
        Map<TransitionPenaltyType, Integer> penaltyCount = 
            computePenalties(previous, current);
        assertEquals(1, penaltyCount.keySet().size());
        assertEquals((Integer) 1, penaltyCount.get(BAD_TRITONE_RESOLUTION));
    }

    @Test
    public void testTritoneInDom7Good(){
        ChordWithContext previous = makeChordWithContext(
            D5, B4, E4, Gs3, E_DOM7_65, A_MAJOR, NO_CONTEXTS
        );
        ChordWithContext current = makeChordWithContext(
            Cs5, A4, E4, A3, A_MAJ_ROOT, A_MAJOR, NO_CONTEXTS
        );
        Map<TransitionPenaltyType, Integer> penaltyCount = 
            computePenalties(previous, current);
        assertEquals(0, penaltyCount.keySet().size());
    }

    @Test
    public void testTritoneInDom7GoodButDom7Bad(){
        ChordWithContext previous = makeChordWithContext(
            B4, Gs4, E4, D3, E_DOM7_42, A_MINOR, NO_CONTEXTS
        );
        ChordWithContext current = makeChordWithContext(
            A4, A4, E4, C3, A_MIN_6, A_MINOR, NO_CONTEXTS
        );
        Map<TransitionPenaltyType, Integer> penaltyCount = 
            computePenalties(previous, current);
        assertEquals(0, penaltyCount.keySet().size());
    }

    @Test
    public void testDomSevenRootBad(){
        ChordWithContext previous = makeChordWithContext(
            D5, Gs4, B3, E3, E_DOM7_ROOT, A_MAJOR, NO_CONTEXTS
        );
        ChordWithContext current = makeChordWithContext(
            Cs5, E4, A3, A2, A_MAJ_ROOT, A_MAJOR, NO_CONTEXTS
        );
        Map<TransitionPenaltyType, Integer> penaltyCount = 
            computePenalties(previous, current);
        assertEquals(1, penaltyCount.keySet().size());
        assertEquals((Integer) 1, penaltyCount.get(BAD_TRITONE_RESOLUTION));
    }

}
