package scorer_chords;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import chord_data.ChordWithContext;
import score_data.ChordPenaltyType;
import test_framework.MusicTestFramework;

public class DoublingScorerTest extends MusicTestFramework {

    private Map<ChordPenaltyType, Integer> computePenalties(ChordWithContext chord) {
        return MusicTestFramework.computeChordPenalties(
            new DoublingScorer(), chord
        );
    }

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
        
    }

    @Test
    public void testGoodDoublingSD1(){
        ChordWithContext doubleSD1 = MusicTestFramework.makeChordWithContext(
            C5, E4, G3, C3, C_MAJ_ROOT, C_MAJOR, NO_CONTEXTS
        );
        Map<ChordPenaltyType, Integer> penaltyCount = 
            computePenalties(doubleSD1);
        
        assertEquals(0, penaltyCount.keySet().size());
    }

    @Test
    public void testBadDoublingSD2(){
        ChordWithContext doubleSD2 = MusicTestFramework.makeChordWithContext(
            D5, F4, A3, D3, D_MIN_ROOT, C_MAJOR, NO_CONTEXTS
        );
        Map<ChordPenaltyType, Integer> penaltyCount = 
                computePenalties(doubleSD2);
        
        assertEquals(1, penaltyCount.keySet().size());
        assertTrue(penaltyCount.get(ChordPenaltyType.BAD_DOUBLING) == 1); 
    }

    @Test
    public void testBadDoublingSD3(){
        ChordWithContext doubleSD3 = MusicTestFramework.makeChordWithContext(
            E5, E4, G3, C3, C_MAJ_ROOT, C_MAJOR, NO_CONTEXTS
        );
        Map<ChordPenaltyType, Integer> penaltyCount = 
            computePenalties(doubleSD3);

        assertEquals(1, penaltyCount.keySet().size());
        assertTrue(penaltyCount.get(ChordPenaltyType.BAD_DOUBLING) == 1); 
    }

    @Test
    public void testGoodDoublingSD4(){
        ChordWithContext doubleSD4 = MusicTestFramework.makeChordWithContext(
            A4, F4, D4, F3, D_MIN_6, C_MAJOR, NO_CONTEXTS
        );
        Map<ChordPenaltyType, Integer> penaltyCount = 
            computePenalties(doubleSD4);
        
        assertEquals(0, penaltyCount.keySet().size());
    }
    
    @Test
    public void testGoodDoublingSD5(){
        ChordWithContext doubleSD5 = MusicTestFramework.makeChordWithContext(
            B4, D4, G3, G2, G_MAJ_ROOT, C_MAJOR, NO_CONTEXTS
        );
        Map<ChordPenaltyType, Integer> penaltyCount = 
            computePenalties(doubleSD5);
        
        assertEquals(0, penaltyCount.keySet().size());
    }
    
    @Test
    public void testTripledSD1(){
        ChordWithContext tripleSD1 = MusicTestFramework.makeChordWithContext(
            C5, C4, E3, C3, C_MAJ_ROOT, C_MAJOR, NO_CONTEXTS
        );
        Map<ChordPenaltyType, Integer> penaltyCount = 
            computePenalties(tripleSD1);
        
        assertEquals(1, penaltyCount.keySet().size());
        assertTrue(penaltyCount.get(ChordPenaltyType.OMITTED_FIFTH) == 1); 
    }

    @Test
    public void testBadDoubleDouble(){
        ChordWithContext doubleSD1SD3 = MusicTestFramework.makeChordWithContext(
            E5, E4, C4, C3, C_MAJ_ROOT, C_MAJOR, NO_CONTEXTS
        );    
        Map<ChordPenaltyType, Integer> penaltyCount = 
            computePenalties(doubleSD1SD3);
        
        assertEquals(2, penaltyCount.keySet().size());
        assertTrue(penaltyCount.get(ChordPenaltyType.OMITTED_FIFTH) == 1);
        assertTrue(penaltyCount.get(ChordPenaltyType.DOUBLE_DOUBLING) == 1);
    }
    
    @Test
    public void testDoubleLeadingTone(){
        ChordWithContext doubleSD7 = MusicTestFramework.makeChordWithContext(
            B4, D4, B3, G3, G_MAJ_ROOT, C_MAJOR, NO_CONTEXTS
        );                
        Map<ChordPenaltyType, Integer> penaltyCount = 
            computePenalties(doubleSD7);
        
        assertEquals(2, penaltyCount.keySet().size());
        assertTrue(penaltyCount.get(ChordPenaltyType.BAD_DOUBLING) == 1);
        assertTrue(penaltyCount.get(ChordPenaltyType.DOUBLED_LEADING_TONE) == 1);
    }
    
}
