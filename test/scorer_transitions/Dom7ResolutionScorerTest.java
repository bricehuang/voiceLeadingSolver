package scorer_transitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import chord_data.ChordWithContext;
import score_data.TransitionPenaltyType;
import test_framework.MusicTestFramework;

public class Dom7ResolutionScorerTest extends MusicTestFramework {

    private Map<TransitionPenaltyType, Integer> computePenalties(
        ChordWithContext previous, ChordWithContext current
    ) {
        return computeTransitionPenalties(
            new Dom7ResolutionScorer(), previous, current
        );
    }
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
        
    }

    @Test
    public void testDomSevenRootGood(){
        ChordWithContext eDom7 = makeChordWithContext(
            D5, Gs4, B3, E3, E_DOM7_ROOT, A_MINOR, NO_CONTEXTS
        );
        ChordWithContext aMin = makeChordWithContext(
            C5, A4, A3, A2, A_MIN_ROOT, A_MINOR, NO_CONTEXTS
        );
        Map<TransitionPenaltyType, Integer> penaltyCount = 
            computePenalties(eDom7, aMin);
        assertEquals(0, penaltyCount.keySet().size());
    }

    @Test
    public void testDomSevenRootBad(){
        ChordWithContext eDom7 = makeChordWithContext(
            D5, Gs4, B3, E3, E_DOM7_ROOT, A_MAJOR, NO_CONTEXTS
        );
        ChordWithContext aMaj = makeChordWithContext(
            Cs5, E4, A3, A2, A_MAJ_ROOT, A_MAJOR, NO_CONTEXTS
        );
        Map<TransitionPenaltyType, Integer> penaltyCount = 
            computePenalties(eDom7, aMaj);
        assertEquals(1, penaltyCount.keySet().size());
        assertTrue(penaltyCount.get(DOM_SEVEN_RES) == 1);
    }

    @Test
    public void testDomSevenInv1Good(){
        ChordWithContext eDom7 = makeChordWithContext(
            D5, B4, E4, Gs3, E_DOM7_65, A_MAJOR, NO_CONTEXTS
        );
        ChordWithContext aMaj = makeChordWithContext(
            Cs5, A4, E4, A3, A_MAJ_ROOT, A_MAJOR, NO_CONTEXTS
        );
        Map<TransitionPenaltyType, Integer> penaltyCount = 
            computePenalties(eDom7, aMaj);
        assertEquals(0, penaltyCount.keySet().size());
    }

    @Test
    public void testDomSevenInv1Bad(){
        ChordWithContext eDom7 = makeChordWithContext(
            D5, B4, E4, Gs3, E_DOM7_65, A_MINOR, NO_CONTEXTS
        );
        ChordWithContext aMin = makeChordWithContext(
            E5, C5, E4, A3, A_MIN_ROOT, A_MINOR, NO_CONTEXTS
        );
        Map<TransitionPenaltyType, Integer> penaltyCount = 
            computePenalties(eDom7, aMin);
        assertEquals(1, penaltyCount.keySet().size());
        assertTrue(penaltyCount.get(DOM_SEVEN_RES) == 1);
    }

    @Test
    public void testDomSevenInv2Good(){
        ChordWithContext eDom7 = makeChordWithContext(
            E5, Gs4, D4, B2, E_DOM7_43, A_MINOR, NO_CONTEXTS
        );
        ChordWithContext aMin = makeChordWithContext(
            E5, A4, C4, A2, A_MIN_ROOT, A_MINOR, NO_CONTEXTS
        );
        Map<TransitionPenaltyType, Integer> penaltyCount = 
            computePenalties(eDom7, aMin);
        assertEquals(0, penaltyCount.keySet().size());
    }

    @Test
    public void testDomSevenInv2GoodAlt(){
        ChordWithContext eDom7 = makeChordWithContext(
            E5, Gs4, D4, B2, E_DOM7_43, A_MAJOR, NO_CONTEXTS
        );
        ChordWithContext aMaj = makeChordWithContext(
            E5, A4, Cs4, Cs3, A_MAJ_6, A_MAJOR, NO_CONTEXTS
        );
        Map<TransitionPenaltyType, Integer> penaltyCount = 
            computePenalties(eDom7, aMaj);
        assertEquals(0, penaltyCount.keySet().size());
    }

    @Test
    public void testDomSevenInv2Bad(){
        ChordWithContext eDom7 = makeChordWithContext(
            E5, Gs4, D4, B2, E_DOM7_43, A_MAJOR, NO_CONTEXTS
        );
        ChordWithContext aMaj = makeChordWithContext(
            E5, A4, E4, Cs3, A_MAJ_6, A_MAJOR, NO_CONTEXTS
        );
        Map<TransitionPenaltyType, Integer> penaltyCount = 
            computePenalties(eDom7, aMaj);
        assertEquals(1, penaltyCount.keySet().size());
        assertTrue(penaltyCount.get(DOM_SEVEN_RES) == 1);
    }

    @Test
    public void testDomSevenInv3Good(){
        ChordWithContext eDom7 = makeChordWithContext(
            B4, Gs4, E4, D3, E_DOM7_42, A_MAJOR, NO_CONTEXTS
        );
        ChordWithContext aMaj = makeChordWithContext(
            Cs5, A4, E4, Cs3, A_MAJ_6, A_MAJOR, NO_CONTEXTS
        );
        Map<TransitionPenaltyType, Integer> penaltyCount = 
            computePenalties(eDom7, aMaj);
        assertEquals(0, penaltyCount.keySet().size());
    }

    @Test
    public void testDomSevenInv3Bad(){
        ChordWithContext eDom7 = makeChordWithContext(
            B4, Gs4, E4, D3, E_DOM7_42, A_MINOR, NO_CONTEXTS
        );
        ChordWithContext aMin = makeChordWithContext(
            A4, A4, E4, C3, A_MIN_6, A_MINOR, NO_CONTEXTS
        );
        Map<TransitionPenaltyType, Integer> penaltyCount = 
            computePenalties(eDom7, aMin);
        assertEquals(1, penaltyCount.keySet().size());
        assertTrue(penaltyCount.get(DOM_SEVEN_RES) == 1);
    }    

    @Test
    public void testNotDomSeven(){
        ChordWithContext eMaj = makeChordWithContext(
            E5, Gs4, B3, E3, E_MAJ_ROOT, A_MINOR, NO_CONTEXTS
        );
        ChordWithContext aMin = makeChordWithContext(
            A4, C4, A3, A2, A_MIN_ROOT, A_MINOR, NO_CONTEXTS
        );
        Map<TransitionPenaltyType, Integer> penaltyCount = 
            computePenalties(eMaj, aMin);
        assertEquals(0, penaltyCount.keySet().size());
    }

}
