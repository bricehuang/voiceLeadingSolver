package scorer_chords;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import chord_data.ChordWithContext;
import score_data.ChordPenaltyType;
import test_framework.MusicTestFramework;

public class PacScorerTest extends MusicTestFramework {

    private Map<ChordPenaltyType, Integer> computePenalties(ChordWithContext chord) {
        return computeChordPenalties(new PacScorer(), chord);
    }

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
        
    }
    
    @Test
    public void testPacGood(){
        ChordWithContext goodPAC = makeChordWithContext(
            C5, E4, G3, C3, C_MAJ_ROOT, C_MAJOR, CADENCE
        );                
        Map<ChordPenaltyType, Integer> penaltyCount = 
            computePenalties(goodPAC);
        
        assertEquals(0, penaltyCount.keySet().size());
    }
    
    @Test
    public void testPacBad(){
        ChordWithContext badPAC = makeChordWithContext(
            E5, G4, C4, C3, C_MAJ_ROOT, C_MAJOR, CADENCE
        );
        Map<ChordPenaltyType, Integer> penaltyCount = 
            computePenalties(badPAC);
        
        assertEquals(1, penaltyCount.keySet().size());
        assertEquals((Integer) 1, penaltyCount.get(ChordPenaltyType.NOT_PAC));
    }
    
    @Test
    public void testPacNotePac(){
        ChordWithContext badPAC = makeChordWithContext(
            E5, G4, C4, C3, C_MAJ_ROOT, C_MAJOR, NO_CONTEXTS
        );
        Map<ChordPenaltyType, Integer> penaltyCount = 
            computePenalties(badPAC);
        
        assertEquals(0, penaltyCount.keySet().size());
    }

}
