package scorer_chords;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import chord_data.ChordWithContext;
import score_data.ChordPenaltyType;
import test_framework.MusicTestFramework;

public class DoublingCadenceScorerTest extends MusicTestFramework {
    
    private Map<ChordPenaltyType, Integer> computePenalties(ChordWithContext chord) {
        return computeChordPenalties(new DoublingCadenceScorer(), chord);
    }

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
        
    }
    
    @Test
    public void testCadenceDoublingIGood(){
        ChordWithContext goodDoublingCadence = makeChordWithContext(
            C5, E4, G3, C3, C_MAJ_ROOT, C_MAJOR, CADENCE
        );
        Map<ChordPenaltyType, Integer> penaltyCount = 
            computePenalties(goodDoublingCadence);
        
        assertEquals(0, penaltyCount.keySet().size());
    }
    
    @Test
    public void testCadenceDoublingIBad(){
        ChordWithContext badDoublingCadence = makeChordWithContext(
            G4, E4, G3, C3, C_MAJ_ROOT, C_MAJOR, CADENCE
        );
        Map<ChordPenaltyType, Integer> penaltyCount = 
            computePenalties(badDoublingCadence);
        
        assertEquals(1, penaltyCount.keySet().size());
        assertTrue(penaltyCount.get(CADENCE_DOUBLING)==1);
    }

    @Test
    public void testCadenceDoublingVGood(){
        ChordWithContext goodDoublingCadence = makeChordWithContext(
            B4, G4, D4, G3, G_MAJ_ROOT, C_MAJOR, CADENTIAL_V
        );
        Map<ChordPenaltyType, Integer> penaltyCount = 
            computePenalties(goodDoublingCadence);   
        
        assertEquals(0, penaltyCount.keySet().size());
    }
    
    @Test
    public void testCadenceDoublingVBad(){
        ChordWithContext badDoublingCadence = makeChordWithContext(
            D5, B4, D4, G3, G_MAJ_ROOT, C_MAJOR, CADENTIAL_V
        );
        Map<ChordPenaltyType, Integer> penaltyCount = 
            computePenalties(badDoublingCadence);
        
        assertEquals(1, penaltyCount.keySet().size());
        assertTrue(penaltyCount.get(CADENCE_DOUBLING) == 1);
    }
    
    @Test
    public void testCadenceDoublingI64Good(){
        ChordWithContext goodDoublingCadence = makeChordWithContext(
            C5, G4, E4, G3, C_MAJ_64, C_MAJOR, CADENTIAL_I64
        );
        Map<ChordPenaltyType, Integer> penaltyCount = 
            computePenalties(goodDoublingCadence);
        
        assertEquals(0, penaltyCount.keySet().size());
    }
    
    @Test
    public void testCadenceDoublingI64Bad(){
        ChordWithContext badDoublingCadence = makeChordWithContext(
            C5, E4, C4, G3, C_MAJ_64, C_MAJOR, CADENTIAL_I64
        );
        Map<ChordPenaltyType, Integer> penaltyCount = 
            computePenalties(badDoublingCadence);
        
        assertEquals(1, penaltyCount.keySet().size());
        assertTrue(penaltyCount.get(CADENCE_DOUBLING) == 1);
    }

    @Test
    public void testCadenceDoublingCadentialPredominantGood(){
        ChordWithContext goodDoublingCadence = makeChordWithContext(
                D5, A4, F4, F3, D_MIN_6, C_MAJOR, CADENTIAL_PREDOMINANT
        );
        Map<ChordPenaltyType, Integer> penaltyCount = 
            computePenalties(goodDoublingCadence);
        
        assertEquals(0, penaltyCount.keySet().size());
    }
    
    @Test
    public void testCadenceDoublingCadentialPredominantBad(){
        ChordWithContext badDoublingCadence = makeChordWithContext(
            D5, A4, D4, F3, D_MIN_6, C_MAJOR, CADENTIAL_PREDOMINANT
        );
        Map<ChordPenaltyType, Integer> penaltyCount = 
            computePenalties(badDoublingCadence);
        
        assertEquals(1, penaltyCount.keySet().size());
        assertTrue(penaltyCount.get(CADENCE_DOUBLING) == 1);
    }

    @Test
    public void testCadenceDoublingNotCadenceDoubling(){
        ChordWithContext badDoublingCadence = makeChordWithContext(
            G4, E4, G3, C3, C_MAJ_ROOT, C_MAJOR, NO_CONTEXTS
        );
        Map<ChordPenaltyType, Integer> penaltyCount = 
                computePenalties(badDoublingCadence);

        assertEquals(0, penaltyCount.keySet().size());
    }

}
