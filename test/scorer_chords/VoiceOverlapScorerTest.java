package scorer_chords;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import chord_data.ChordWithContext;
import score_data.ChordPenaltyType;
import test_framework.MusicTestFramework;

public class VoiceOverlapScorerTest extends MusicTestFramework {

    private Map<ChordPenaltyType, Integer> computePenalties(ChordWithContext chord) {
        return MusicTestFramework.computeChordPenalties(
            new VoiceOverlapScorer(), chord
        );
    }

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
        
    }

    @Test
    public void testVoiceOverlap(){
        ChordWithContext overlap = MusicTestFramework.makeChordWithContext(
            C5, C5, E4, G3, C_MAJ_64, C_MAJOR, NO_CONTEXTS
        ); 
        Map<ChordPenaltyType, Integer> penaltyCount = 
            computePenalties(overlap);
        
        assertEquals(1, penaltyCount.keySet().size());
        assertTrue(penaltyCount.get(VOICE_OVERLAP)==1);
    }
    
    @Test
    public void testNoVoiceOverlap(){
        ChordWithContext noOverlap = MusicTestFramework.makeChordWithContext(
            C5, G4, E4, G3, C_MAJ_64, C_MAJOR, NO_CONTEXTS
        ); 
        Map<ChordPenaltyType, Integer> penaltyCount = 
            computePenalties(noOverlap);
            
        assertEquals(0, penaltyCount.keySet().size());
    }
}
