package scorer_chords;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import chord_data.ChordWithContext;
import chords.Chord;
import score_data.ChordPenaltyType;
import test_framework.MusicTestFramework;

public class VoiceOverlapScorerTest extends MusicTestFramework {

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
        
    }

    @Test
    public void testVoiceOverlap(){
        Chord voiceOverlap = new Chord(C5, C5, E4, G3, C_MAJ_64);
        ChordWithContext badDoublingAndContext = new ChordWithContext(
            voiceOverlap, C_MAJOR, NO_CONTEXTS
        ); 
        Map<ChordPenaltyType, Integer> penaltyCount = 
            new VoiceOverlapScorer().scoreChord(badDoublingAndContext)
            .getPenaltyCount();
        assertEquals(1, penaltyCount.keySet().size());
        assertTrue(penaltyCount.get(VOICE_OVERLAP)==1);
    }
    
    @Test
    public void testNoVoiceOverlap(){
        Chord noVoiceOverlap = new Chord(C5, G4, E4, G3, C_MAJ_64);
        ChordWithContext badDoublingAndContext = new ChordWithContext(
            noVoiceOverlap, C_MAJOR, NO_CONTEXTS
        ); 
        Map<ChordPenaltyType, Integer> penaltyCount = 
            new VoiceOverlapScorer().scoreChord(badDoublingAndContext)
            .getPenaltyCount();
        assertEquals(0, penaltyCount.keySet().size());
    }
}
