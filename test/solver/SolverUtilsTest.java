package solver;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import chords.Chord;
import chords.ChordType;
import chords.PrimitiveChord;
import music.BasicNote;
import music.Note;

public class SolverUtilsTest {

    private static final BasicNote C = new BasicNote(0,0);
    private static final BasicNote E = new BasicNote(2,4);
    private static final BasicNote G = new BasicNote(4,7);
    private static final BasicNote Bb = new BasicNote(6,10);
    
    private static final Note C5 = new Note(C, 5);
    private static final Note Bb4 = new Note(Bb, 4);
    private static final Note E4 = new Note(E, 4);
    private static final Note G3 = new Note(G, 3);
    private static final Note C3 = new Note(C, 3);
    
    private static final PrimitiveChord C_MAJ_ROOT = 
        new PrimitiveChord(C, ChordType.MAJ, 0);
    private static final PrimitiveChord C_DOM7_ROOT =
        new PrimitiveChord(C, ChordType.DOM7, 0);
    
    private static final Chord C_MAJ_ROOT_REALIZED = new Chord(
        C5, E4, G3, C3, C_MAJ_ROOT
    );
    private static final Chord C_DOM7_ROOT_REALIZED = new Chord(
        Bb4, E4, G3, C3, C_DOM7_ROOT
    );

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    @Test
    public void testSpellNotes() {
        assertEquals(
            Arrays.asList(C5, E4, G3, C3), 
            SolverUtils.spellNotes(C_MAJ_ROOT_REALIZED)
        );
        assertEquals(
            Arrays.asList(Bb4, E4, G3, C3), 
            SolverUtils.spellNotes(C_DOM7_ROOT_REALIZED)
        );
    }
    
    @Test
    public void testSpellBasicNotes() {
        assertEquals(
            Arrays.asList(C, E, G, C), 
            SolverUtils.spellBasicNotes(C_MAJ_ROOT_REALIZED)
        );
        assertEquals(
            Arrays.asList(Bb, E, G, C), 
            SolverUtils.spellBasicNotes(C_DOM7_ROOT_REALIZED)
        );
    }
    

}
