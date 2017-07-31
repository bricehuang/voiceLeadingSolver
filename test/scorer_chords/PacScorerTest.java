package scorer_chords;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import chord_data.ChordWithContext;
import chord_data.ContextTag;
import chords.Chord;
import chords.ChordType;
import chords.PrimitiveChord;
import music.BasicNote;
import music.Key;
import music.Note;
import score_data.ChordPenaltyType;

public class PacScorerTest {

    private static final BasicNote C = new BasicNote(0,0);
    private static final BasicNote E = new BasicNote(2,4);
    private static final BasicNote G = new BasicNote(4,7);
    
    private static final Key C_MAJOR = new Key(C, true);
    
    private static final PrimitiveChord C_MAJ_ROOT = 
        new PrimitiveChord (C, ChordType.MAJ, 0);
    
    private static final Set<ContextTag> CADENCE = 
        Collections.unmodifiableSet(new HashSet<>(Arrays.asList(ContextTag.CADENCE)));
    private static final Set<ContextTag> NO_CONTEXT = 
        Collections.unmodifiableSet(new HashSet<>());
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
        
    }
    
    @Test
    public void testPacGood(){
        Chord cMajorVoicing = new Chord(
            new Note(C, 5),
            new Note(E, 4), 
            new Note(G, 3), 
            new Note(C, 3), 
            C_MAJ_ROOT
        );
        ChordWithContext cMajorVoicingCadence = new ChordWithContext(
            cMajorVoicing,
            C_MAJOR, 
            CADENCE
        );
                
        Map<ChordPenaltyType, Integer> score = new PacScorer().scoreChord(
            cMajorVoicingCadence
        );
        assert(score.keySet().size() == 0);
    }
    
    @Test
    public void testPacBad(){
        Chord cMajorVoicing = new Chord(
            new Note(E, 5),
            new Note(G, 4), 
            new Note(C, 4), 
            new Note(C, 3), 
            C_MAJ_ROOT
        );
        
        // penalty triggers when cadence tag present
        ChordWithContext cMajorVoicingCadence = new ChordWithContext(
            cMajorVoicing,
            C_MAJOR, 
            CADENCE
        );
        Map<ChordPenaltyType, Integer> scoreCadence = new PacScorer().scoreChord(
            cMajorVoicingCadence
        );
        assert(scoreCadence.keySet().size() == 1);
        assert(scoreCadence.get(ChordPenaltyType.NOT_PAC) == 1);

        // penalty does not trigger when cadence tag absent
        ChordWithContext cMajorVoicingNotCadence = new ChordWithContext(
            cMajorVoicing,
            C_MAJOR, 
            NO_CONTEXT
        );
        Map<ChordPenaltyType, Integer> scoreNotCadence = new PacScorer().scoreChord(
            cMajorVoicingNotCadence
        );
        assert(scoreNotCadence.keySet().size() == 0);

    }

}
