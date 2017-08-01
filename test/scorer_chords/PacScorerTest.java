package scorer_chords;

import java.util.Map;

import org.junit.Test;

import chord_data.ChordWithContext;
import chords.Chord;
import score_data.ChordPenaltyType;
import test_framework.MusicTestFramework;

public class PacScorerTest extends MusicTestFramework {

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
        
    }
    
    @Test
    public void testPacGood(){
        Chord cMajorVoicing = new Chord(C5, E4, G3, C3, C_MAJ_ROOT);
        ChordWithContext cMajorVoicingCadence = new ChordWithContext(
            cMajorVoicing, C_MAJOR, CADENCE
        );
                
        Map<ChordPenaltyType, Integer> penaltyCount = 
            new PacScorer().scoreChord(cMajorVoicingCadence)
            .getPenaltyCount();
        assert(penaltyCount.keySet().size() == 0);
    }
    
    @Test
    public void testPacBad(){
        Chord cMajorVoicing = new Chord(E5, G4, C4, C3, C_MAJ_ROOT);
        
        // penalty triggers when cadence tag present
        ChordWithContext cMajorVoicingCadence = new ChordWithContext(
            cMajorVoicing, C_MAJOR, CADENCE
        );
        Map<ChordPenaltyType, Integer> penaltyCountCadence 
            = new PacScorer().scoreChord(cMajorVoicingCadence)
            .getPenaltyCount();
        assert(penaltyCountCadence.keySet().size() == 1);
        assert(penaltyCountCadence.get(ChordPenaltyType.NOT_PAC) == 1);

        // penalty does not trigger when cadence tag absent
        ChordWithContext cMajorVoicingNotCadence = new ChordWithContext(
            cMajorVoicing, C_MAJOR, NO_CONTEXTS
        );
        Map<ChordPenaltyType, Integer> penaltyCountNotCadence = 
            new PacScorer().scoreChord(cMajorVoicingNotCadence)
            .getPenaltyCount();
        assert(penaltyCountNotCadence.keySet().size() == 0);

    }

}
