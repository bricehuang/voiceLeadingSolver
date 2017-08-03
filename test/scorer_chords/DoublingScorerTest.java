package scorer_chords;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import chord_data.ChordWithContext;
import chords.Chord;
import score_data.ChordPenaltyType;
import test_framework.MusicTestFramework;

public class DoublingScorerTest extends MusicTestFramework {

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
        
    }

    @Test
    public void testGoodDoublingSD1(){
        Chord doubleSD1 = new Chord(C5, E4, G3, C3, C_MAJ_ROOT);
        ChordWithContext doubleSD1Context = new ChordWithContext(
            doubleSD1, C_MAJOR, NO_CONTEXTS
        );
        Map<ChordPenaltyType, Integer> penaltyCounts = 
            new DoublingScorer().scoreChord(doubleSD1Context)
            .getPenaltyCount();
        
        assertEquals(0, penaltyCounts.keySet().size());
    }

    @Test
    public void testBadDoublingSD2(){
        Chord doubleSD2 = new Chord(D5, F4, A3, D3, D_MIN_ROOT);
        ChordWithContext doubleSD2Context = new ChordWithContext(
            doubleSD2, C_MAJOR, NO_CONTEXTS
        );
        Map<ChordPenaltyType, Integer> penaltyCounts = 
            new DoublingScorer().scoreChord(doubleSD2Context)
            .getPenaltyCount();
        
        assertEquals(1, penaltyCounts.keySet().size());
        assertTrue(penaltyCounts.get(ChordPenaltyType.BAD_DOUBLING) == 1); 
    }

    @Test
    public void testBadDoublingSD3(){
        Chord doubleSD3 = new Chord(E5, E4, G3, C3, C_MAJ_ROOT);
        ChordWithContext doubleSD3Context = new ChordWithContext(
            doubleSD3, C_MAJOR, NO_CONTEXTS
        );
        Map<ChordPenaltyType, Integer> penaltyCounts = 
            new DoublingScorer().scoreChord(doubleSD3Context)
            .getPenaltyCount();

        assertEquals(1, penaltyCounts.keySet().size());
        assertTrue(penaltyCounts.get(ChordPenaltyType.BAD_DOUBLING) == 1); 
    }

    @Test
    public void testGoodDoublingSD4(){
        Chord doubleSD4 = new Chord(A4, F4, D4, F3, D_MIN_6);
        ChordWithContext doubleSD4Context = new ChordWithContext(
            doubleSD4, C_MAJOR, NO_CONTEXTS
        );
        Map<ChordPenaltyType, Integer> penaltyCounts = 
            new DoublingScorer().scoreChord(doubleSD4Context)
            .getPenaltyCount();
        
        assertEquals(0, penaltyCounts.keySet().size());
    }
    
    @Test
    public void testGoodDoublingSD5(){
        Chord doubleSD5= new Chord(B4, D4, G3, G2, G_MAJ_ROOT);
        ChordWithContext doubleSD5Context = new ChordWithContext(
            doubleSD5, C_MAJOR, NO_CONTEXTS
        );
        Map<ChordPenaltyType, Integer> penaltyCounts = 
            new DoublingScorer().scoreChord(doubleSD5Context)
            .getPenaltyCount();
        
        assertEquals(0, penaltyCounts.keySet().size());
    }
    
    @Test
    public void testTripledSD1(){
        Chord tripleSD1= new Chord(C5, C4, E3, C3, C_MAJ_ROOT);
        ChordWithContext tripleSD1Context = new ChordWithContext(
            tripleSD1, C_MAJOR, NO_CONTEXTS
        );
        Map<ChordPenaltyType, Integer> penaltyCounts = 
            new DoublingScorer().scoreChord(tripleSD1Context)
            .getPenaltyCount();
        
        assertEquals(1, penaltyCounts.keySet().size());
        assertTrue(penaltyCounts.get(ChordPenaltyType.OMITTED_FIFTH) == 1); 
    }

    @Test
    public void testBadDoubleDouble(){
        Chord doubleSD1SD3 = new Chord(E5, E4, C4, C3, C_MAJ_ROOT);
        ChordWithContext doubleSD1SD3Context = new ChordWithContext(
            doubleSD1SD3, C_MAJOR, NO_CONTEXTS
        );
        
        Map<ChordPenaltyType, Integer> penaltyCounts = 
            new DoublingScorer().scoreChord(doubleSD1SD3Context)
            .getPenaltyCount();
        
        assertEquals(2, penaltyCounts.keySet().size());
        assertTrue(penaltyCounts.get(ChordPenaltyType.OMITTED_FIFTH) == 1);
        assertTrue(penaltyCounts.get(ChordPenaltyType.DOUBLE_DOUBLING) == 1);
    }
    
    @Test
    public void testDoubleLeadingTone(){
        Chord doubleSD7= new Chord(B4, D4, B3, G3, G_MAJ_ROOT);
        ChordWithContext doubleSD7Context = new ChordWithContext(
            doubleSD7, C_MAJOR, NO_CONTEXTS
        );
                
        Map<ChordPenaltyType, Integer> penaltyCounts = 
            new DoublingScorer().scoreChord(doubleSD7Context)
            .getPenaltyCount();
        
        assertEquals(2, penaltyCounts.keySet().size());
        assertTrue(penaltyCounts.get(ChordPenaltyType.BAD_DOUBLING) == 1);
        assertTrue(penaltyCounts.get(ChordPenaltyType.DOUBLED_LEADING_TONE) == 1);
    }
    
}
