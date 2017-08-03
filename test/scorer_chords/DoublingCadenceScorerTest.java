package scorer_chords;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import chord_data.ChordWithContext;
import score_data.ChordPenaltyType;
import test_framework.MusicTestFramework;

public class DoublingCadenceScorerTest extends MusicTestFramework {
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
        
    }

    @Test
    public void testCadenceDoublingIGood(){
        ChordWithContext goodDoublingCadence = MusicTestFramework.makeChordWithContext(
            C5, E4, G3, C3, C_MAJ_ROOT, C_MAJOR, CADENCE
        );
        Map<ChordPenaltyType, Integer> penaltyCount = 
            new DoublingCadenceScorer().scoreChord(goodDoublingCadence)
            .getPenaltyCount();
        
        assertEquals(0, penaltyCount.keySet().size());
    }
    
    @Test
    public void testCadenceDoublingIBad(){
        ChordWithContext badDoublingCadence = MusicTestFramework.makeChordWithContext(
            G4, E4, G3, C3, C_MAJ_ROOT, C_MAJOR, CADENCE
        );
        Map<ChordPenaltyType, Integer> penaltyCount = 
            new DoublingCadenceScorer().scoreChord(badDoublingCadence)
            .getPenaltyCount();

        assertEquals(1, penaltyCount.keySet().size());
        assertTrue(penaltyCount.get(CADENCE_DOUBLING)==1);
    }

    @Test
    public void testCadenceDoublingVGood(){
        ChordWithContext goodDoublingCadence = MusicTestFramework.makeChordWithContext(
            B4, G4, D4, G3, G_MAJ_ROOT, C_MAJOR, CADENTIAL_V
        );
        Map<ChordPenaltyType, Integer> penaltyCount = 
            new DoublingCadenceScorer().scoreChord(goodDoublingCadence)
            .getPenaltyCount();
            
        assertEquals(0, penaltyCount.keySet().size());
    }
    
    @Test
    public void testCadenceDoublingVBad(){
        ChordWithContext badDoublingCadence = MusicTestFramework.makeChordWithContext(
            D5, B4, D4, G3, G_MAJ_ROOT, C_MAJOR, CADENTIAL_V
        );
        Map<ChordPenaltyType, Integer> penaltyCount = 
            new DoublingCadenceScorer().scoreChord(badDoublingCadence)
            .getPenaltyCount();

        assertEquals(1, penaltyCount.keySet().size());
        assertTrue(penaltyCount.get(CADENCE_DOUBLING) == 1);
    }
    
    @Test
    public void testCadenceDoublingI64Good(){
        ChordWithContext goodDoublingCadence = MusicTestFramework.makeChordWithContext(
            C5, G4, E4, G3, C_MAJ_64, C_MAJOR, CADENTIAL_I64
        );
        Map<ChordPenaltyType, Integer> penaltyCount = 
            new DoublingCadenceScorer().scoreChord(goodDoublingCadence)
            .getPenaltyCount();
            
        assertEquals(0, penaltyCount.keySet().size());
    }
    
    @Test
    public void testCadenceDoublingI64Bad(){
        ChordWithContext badDoublingCadence = MusicTestFramework.makeChordWithContext(
            C5, E4, C4, G3, C_MAJ_64, C_MAJOR, CADENTIAL_I64
        );
        Map<ChordPenaltyType, Integer> penaltyCount = 
            new DoublingCadenceScorer().scoreChord(badDoublingCadence)
            .getPenaltyCount();

        assertEquals(1, penaltyCount.keySet().size());
        assertTrue(penaltyCount.get(CADENCE_DOUBLING) == 1);
    }

    @Test
    public void testCadenceDoublingCadentialPredominantGood(){
        ChordWithContext goodDoublingCadence = MusicTestFramework.makeChordWithContext(
                D5, A4, F4, F3, D_MIN_6, C_MAJOR, CADENTIAL_PREDOMINANT
        );
        Map<ChordPenaltyType, Integer> penaltyCount = 
            new DoublingCadenceScorer().scoreChord(goodDoublingCadence)
            .getPenaltyCount();
            
        assertEquals(0, penaltyCount.keySet().size());
    }
    
    @Test
    public void testCadenceDoublingCadentialPredominantBad(){
        ChordWithContext badDoublingCadence = MusicTestFramework.makeChordWithContext(
            D5, A4, D4, F3, D_MIN_6, C_MAJOR, CADENTIAL_PREDOMINANT
        );
        Map<ChordPenaltyType, Integer> penaltyCount = 
            new DoublingCadenceScorer().scoreChord(badDoublingCadence)
            .getPenaltyCount();

        assertEquals(1, penaltyCount.keySet().size());
        assertTrue(penaltyCount.get(CADENCE_DOUBLING) == 1);
    }

    @Test
    public void testCadenceDoublingNotCadenceDoubling(){
        ChordWithContext badDoublingCadence = MusicTestFramework.makeChordWithContext(
            G4, E4, G3, C3, C_MAJ_ROOT, C_MAJOR, NO_CONTEXTS
        );
        Map<ChordPenaltyType, Integer> penaltyCount = 
            new DoublingCadenceScorer().scoreChord(badDoublingCadence)
            .getPenaltyCount();

        assertEquals(0, penaltyCount.keySet().size());
    }

}
