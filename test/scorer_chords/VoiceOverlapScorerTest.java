package scorer_chords;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import chord_data.ChordWithContext;
import score_data.ChordPenaltyType;
import test_framework.MusicTestFramework;

public class VoiceOverlapScorerTest extends MusicTestFramework {

    private Map<ChordPenaltyType, Integer> computePenalties(ChordWithContext chord) {
        return computeChordPenalties(new VoiceOverlapScorer(), chord);
    }

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
        
    }

    @Test
    public void testVoiceOverlap(){
        ChordWithContext overlap = makeChordWithContext(
            C5, C5, E4, G3, C_MAJ_64, C_MAJOR, NO_CONTEXTS
        ); 
        Map<ChordPenaltyType, Integer> penaltyCount = 
            computePenalties(overlap);
        
        assertEquals(1, penaltyCount.keySet().size());
        assertEquals((Integer) 1, penaltyCount.get(VOICE_OVERLAP));
    }
    
    @Test
    public void testNoVoiceOverlap(){
        ChordWithContext noOverlap = makeChordWithContext(
            C5, G4, E4, G3, C_MAJ_64, C_MAJOR, NO_CONTEXTS
        ); 
        Map<ChordPenaltyType, Integer> penaltyCount = 
            computePenalties(noOverlap);
            
        assertEquals(0, penaltyCount.keySet().size());
    }
}
