package scorer_chords;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import chord_data.ChordWithContext;
import chords.Chord;
import score_data.ChordPenaltyType;
import test_framework.MusicTestFramework;

public class DoublingCadenceScorerTest extends MusicTestFramework {
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
        
    }

    @Test
    public void testCadenceDoublingIGood(){
        Chord goodDoubling = new Chord(C5, E4, G3, C3, C_MAJ_ROOT);
        ChordWithContext goodDoublingCadence = 
            new ChordWithContext(goodDoubling, C_MAJOR, CADENCE);
        Map<ChordPenaltyType, Integer> penaltyCount = 
            new DoublingCadenceScorer().scoreChord(goodDoublingCadence)
            .getPenaltyCount();
        
        assertEquals(0, penaltyCount.keySet().size());
    }
    
    @Test
    public void testCadenceDoublingIBad(){
        Chord badDoubling = new Chord(G4, E4, G3, C3, C_MAJ_ROOT);
        ChordWithContext badDoublingCadence = 
            new ChordWithContext(badDoubling, C_MAJOR, CADENCE);
        Map<ChordPenaltyType, Integer> penaltyCount = 
            new DoublingCadenceScorer().scoreChord(badDoublingCadence)
            .getPenaltyCount();

        assertEquals(1, penaltyCount.keySet().size());
        assertTrue(penaltyCount.get(CADENCE_DOUBLING)==1);
    }

    @Test
    public void testCadenceDoublingVGood(){
        Chord goodDoubling = new Chord(B4, G4, D4, G3, G_MAJ_ROOT);
        ChordWithContext goodDoublingCadence = 
            new ChordWithContext(goodDoubling, C_MAJOR, CADENTIAL_V);
        Map<ChordPenaltyType, Integer> penaltyCount = 
            new DoublingCadenceScorer().scoreChord(goodDoublingCadence)
            .getPenaltyCount();
            
        assertEquals(0, penaltyCount.keySet().size());
    }
    
    @Test
    public void testCadenceDoublingVBad(){
        Chord badDoubling = new Chord(D5, B4, D4, G3, G_MAJ_ROOT);
        ChordWithContext badDoublingCadence = 
            new ChordWithContext(badDoubling, C_MAJOR, CADENTIAL_V);
        Map<ChordPenaltyType, Integer> penaltyCount = 
            new DoublingCadenceScorer().scoreChord(badDoublingCadence)
            .getPenaltyCount();

        assertEquals(1, penaltyCount.keySet().size());
        assertTrue(penaltyCount.get(CADENCE_DOUBLING) == 1);
    }
    
    @Test
    public void testCadenceDoublingI64Good(){
        Chord goodDoubling = new Chord(C5, G4, E4, G3, C_MAJ_64);
        ChordWithContext goodDoublingCadence = 
                new ChordWithContext(goodDoubling, C_MAJOR, CADENTIAL_I64);
        Map<ChordPenaltyType, Integer> penaltyCount = 
            new DoublingCadenceScorer().scoreChord(goodDoublingCadence)
            .getPenaltyCount();
            
        assertEquals(0, penaltyCount.keySet().size());
    }
    
    @Test
    public void testCadenceDoublingI64Bad(){
        Chord badDoubling = new Chord(C5, E4, C4, G3, C_MAJ_64);
        ChordWithContext badDoublingCadence = 
            new ChordWithContext(badDoubling, C_MAJOR, CADENTIAL_I64);
        Map<ChordPenaltyType, Integer> penaltyCount = 
            new DoublingCadenceScorer().scoreChord(badDoublingCadence)
            .getPenaltyCount();

        assertEquals(1, penaltyCount.keySet().size());
        assertTrue(penaltyCount.get(CADENCE_DOUBLING) == 1);
    }

    @Test
    public void testCadenceDoublingCadentialPredominantGood(){
        Chord goodDoubling = new Chord(D5, A4, F4, F3, D_MIN_6);
        ChordWithContext goodDoublingCadence = 
            new ChordWithContext(goodDoubling, C_MAJOR, CADENTIAL_PREDOMINANT);
        Map<ChordPenaltyType, Integer> penaltyCount = 
            new DoublingCadenceScorer().scoreChord(goodDoublingCadence)
            .getPenaltyCount();
            
        assertEquals(0, penaltyCount.keySet().size());
    }
    
    @Test
    public void testCadenceDoublingCadentialPredominantBad(){
        Chord badDoubling = new Chord(D5, A4, D4, F3, D_MIN_6);
        ChordWithContext badDoublingCadence = 
            new ChordWithContext(badDoubling, C_MAJOR, CADENTIAL_PREDOMINANT);
        Map<ChordPenaltyType, Integer> penaltyCount = 
            new DoublingCadenceScorer().scoreChord(badDoublingCadence)
            .getPenaltyCount();

        assertEquals(1, penaltyCount.keySet().size());
        assertTrue(penaltyCount.get(CADENCE_DOUBLING) == 1);
    }

    @Test
    public void testCadenceDoublingNotCadenceDoubling(){
        Chord badDoubling = new Chord(G4, E4, G3, C3, C_MAJ_ROOT);
        ChordWithContext badDoublingCadence = 
            new ChordWithContext(badDoubling, C_MAJOR, NO_CONTEXTS);
        Map<ChordPenaltyType, Integer> penaltyCount = 
            new DoublingCadenceScorer().scoreChord(badDoublingCadence)
            .getPenaltyCount();

        assertEquals(0, penaltyCount.keySet().size());
    }

}