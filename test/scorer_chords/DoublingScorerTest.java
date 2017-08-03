package scorer_chords;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import chord_data.ChordWithContext;
import score_data.ChordPenaltyType;
import test_framework.MusicTestFramework;

public class DoublingScorerTest extends MusicTestFramework {

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
        
    }

    @Test
    public void testGoodDoublingSD1(){
        ChordWithContext doubleSD1 = MusicTestFramework.makeChordWithContext(
            C5, E4, G3, C3, C_MAJ_ROOT, C_MAJOR, NO_CONTEXTS
        );
        Map<ChordPenaltyType, Integer> penaltyCounts = 
            new DoublingScorer().scoreChord(doubleSD1)
            .getPenaltyCount();
        
        assertEquals(0, penaltyCounts.keySet().size());
    }

    @Test
    public void testBadDoublingSD2(){
        ChordWithContext doubleSD2 = MusicTestFramework.makeChordWithContext(
            D5, F4, A3, D3, D_MIN_ROOT, C_MAJOR, NO_CONTEXTS
        );
        Map<ChordPenaltyType, Integer> penaltyCounts = 
            new DoublingScorer().scoreChord(doubleSD2)
            .getPenaltyCount();
        
        assertEquals(1, penaltyCounts.keySet().size());
        assertTrue(penaltyCounts.get(ChordPenaltyType.BAD_DOUBLING) == 1); 
    }

    @Test
    public void testBadDoublingSD3(){
        ChordWithContext doubleSD3 = MusicTestFramework.makeChordWithContext(
            E5, E4, G3, C3, C_MAJ_ROOT, C_MAJOR, NO_CONTEXTS
        );
        Map<ChordPenaltyType, Integer> penaltyCounts = 
            new DoublingScorer().scoreChord(doubleSD3)
            .getPenaltyCount();

        assertEquals(1, penaltyCounts.keySet().size());
        assertTrue(penaltyCounts.get(ChordPenaltyType.BAD_DOUBLING) == 1); 
    }

    @Test
    public void testGoodDoublingSD4(){
        ChordWithContext doubleSD4 = MusicTestFramework.makeChordWithContext(
            A4, F4, D4, F3, D_MIN_6, C_MAJOR, NO_CONTEXTS
        );
        Map<ChordPenaltyType, Integer> penaltyCounts = 
            new DoublingScorer().scoreChord(doubleSD4)
            .getPenaltyCount();
        
        assertEquals(0, penaltyCounts.keySet().size());
    }
    
    @Test
    public void testGoodDoublingSD5(){
        ChordWithContext doubleSD5 = MusicTestFramework.makeChordWithContext(
            B4, D4, G3, G2, G_MAJ_ROOT, C_MAJOR, NO_CONTEXTS
        );
        Map<ChordPenaltyType, Integer> penaltyCounts = 
            new DoublingScorer().scoreChord(doubleSD5)
            .getPenaltyCount();
        
        assertEquals(0, penaltyCounts.keySet().size());
    }
    
    @Test
    public void testTripledSD1(){
        ChordWithContext tripleSD1 = MusicTestFramework.makeChordWithContext(
            C5, C4, E3, C3, C_MAJ_ROOT, C_MAJOR, NO_CONTEXTS
        );
        Map<ChordPenaltyType, Integer> penaltyCounts = 
            new DoublingScorer().scoreChord(tripleSD1)
            .getPenaltyCount();
        
        assertEquals(1, penaltyCounts.keySet().size());
        assertTrue(penaltyCounts.get(ChordPenaltyType.OMITTED_FIFTH) == 1); 
    }

    @Test
    public void testBadDoubleDouble(){
        ChordWithContext doubleSD1SD3 = MusicTestFramework.makeChordWithContext(
            E5, E4, C4, C3, C_MAJ_ROOT, C_MAJOR, NO_CONTEXTS
        );    
        Map<ChordPenaltyType, Integer> penaltyCounts = 
            new DoublingScorer().scoreChord(doubleSD1SD3)
            .getPenaltyCount();
        
        assertEquals(2, penaltyCounts.keySet().size());
        assertTrue(penaltyCounts.get(ChordPenaltyType.OMITTED_FIFTH) == 1);
        assertTrue(penaltyCounts.get(ChordPenaltyType.DOUBLE_DOUBLING) == 1);
    }
    
    @Test
    public void testDoubleLeadingTone(){
        ChordWithContext doubleSD7 = MusicTestFramework.makeChordWithContext(
            B4, D4, B3, G3, G_MAJ_ROOT, C_MAJOR, NO_CONTEXTS
        );                
        Map<ChordPenaltyType, Integer> penaltyCounts = 
            new DoublingScorer().scoreChord(doubleSD7)
            .getPenaltyCount();
        
        assertEquals(2, penaltyCounts.keySet().size());
        assertTrue(penaltyCounts.get(ChordPenaltyType.BAD_DOUBLING) == 1);
        assertTrue(penaltyCounts.get(ChordPenaltyType.DOUBLED_LEADING_TONE) == 1);
    }
    
}
