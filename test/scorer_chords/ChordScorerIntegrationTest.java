package scorer_chords;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import chord_data.ChordWithContext;
import score_data.ChordPenaltyType;
import test_framework.MusicTestFramework;

public class ChordScorerIntegrationTest extends MusicTestFramework {
    
    private Map<ChordPenaltyType, Integer> computePenalties(ChordWithContext chord) {
        return ChordScorer.score(chord).getPenaltyCount();
    }

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
        
    }

    @Test
    public void compositeTest1(){
        // badnesses: voice overlap, bad doubling, bad cadence doubling
        ChordWithContext chord = makeChordWithContext(
            C5, E4, E4, G3, C_MAJ_64, C_MAJOR, CADENTIAL_I64
        );
        Map<ChordPenaltyType, Integer> penaltyCounts = 
            computePenalties(chord);
        
        assertEquals(3, penaltyCounts.keySet().size());
        assertEquals((Integer) 1, penaltyCounts.get(ChordPenaltyType.VOICE_OVERLAP));
        assertEquals((Integer) 1, penaltyCounts.get(ChordPenaltyType.BAD_DOUBLING));
        assertEquals((Integer) 1, penaltyCounts.get(ChordPenaltyType.CADENCE_DOUBLING));
    }

    @Test
    public void compositeTest2(){
        // badnesses: not PAC, wrong cadence doubling   
        ChordWithContext chord = makeChordWithContext(
            G4, E4, G3, C3, C_MAJ_ROOT, C_MAJOR, CADENCE
        );
        Map<ChordPenaltyType, Integer> penaltyCounts = 
            computePenalties(chord);
        
        assertEquals(2, penaltyCounts.keySet().size());
        assertEquals((Integer) 1, penaltyCounts.get(ChordPenaltyType.NOT_PAC));
        assertEquals((Integer) 1, penaltyCounts.get(ChordPenaltyType.CADENCE_DOUBLING));
    }

    @Test
    public void compositeTest3(){
        // badnesses: omitted 5th   
        ChordWithContext chord = makeChordWithContext(
            C5, E4, C4, C3, C_MAJ_ROOT, C_MAJOR, CADENCE
        );
        Map<ChordPenaltyType, Integer> penaltyCounts =
            computePenalties(chord);
        
        assertEquals(1, penaltyCounts.keySet().size());
        assertEquals((Integer) 1, penaltyCounts.get(ChordPenaltyType.OMITTED_FIFTH));
    }
}
