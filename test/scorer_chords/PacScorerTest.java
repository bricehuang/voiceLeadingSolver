package scorer_chords;

import java.util.Map;

import org.junit.Test;

import chord_data.ChordWithContext;
import score_data.ChordPenaltyType;
import test_framework.MusicTestFramework;

public class PacScorerTest extends MusicTestFramework {

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
        
    }
    
    @Test
    public void testPacGood(){
        ChordWithContext cMajorVoicingCadence = MusicTestFramework.makeChordWithContext(
            C5, E4, G3, C3, C_MAJ_ROOT, C_MAJOR, CADENCE
        );
                
        Map<ChordPenaltyType, Integer> penaltyCount = 
            new PacScorer().scoreChord(cMajorVoicingCadence)
            .getPenaltyCount();
        assert(penaltyCount.keySet().size() == 0);
    }
    
    @Test
    public void testPacBad(){
        ChordWithContext cMajorVoicingCadence = MusicTestFramework.makeChordWithContext(
            E5, G4, C4, C3, C_MAJ_ROOT, C_MAJOR, CADENCE
        );
        Map<ChordPenaltyType, Integer> penaltyCount =
            new PacScorer().scoreChord(cMajorVoicingCadence)
            .getPenaltyCount();
        assert(penaltyCount.keySet().size() == 1);
        assert(penaltyCount.get(ChordPenaltyType.NOT_PAC) == 1);
    }
    
    @Test
    public void testPacNotePac(){
        ChordWithContext cMajorVoicingNotCadence = MusicTestFramework.makeChordWithContext(
            E5, G4, C4, C3, C_MAJ_ROOT, C_MAJOR, NO_CONTEXTS
        );
        Map<ChordPenaltyType, Integer> penaltyCount = 
            new PacScorer().scoreChord(cMajorVoicingNotCadence)
            .getPenaltyCount();
        assert(penaltyCount.keySet().size() == 0);

    }

}
