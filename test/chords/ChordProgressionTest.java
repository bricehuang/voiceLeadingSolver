package chords;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import music.BasicNote;
import music.Note;

public class ChordProgressionTest {
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    private static final BasicNote C = new BasicNote(0,0);
    private static final BasicNote D = new BasicNote(1,2);
    private static final BasicNote E = new BasicNote(2,4);
    private static final BasicNote G = new BasicNote(4,7);
    private static final BasicNote B = new BasicNote(6,11);
    
    private static final PrimitiveChord C_MAJ_64 = 
            new PrimitiveChord(C, ChordType.MAJ, 2);
    private static final Chord C_MAJ_64_REALIZED = new Chord(
            new Note(C, 5), new Note(E, 4), new Note(G, 3), 
            new Note(G, 2),  C_MAJ_64);
    
    private static final PrimitiveChord G_MAJ_ROOT = 
            new PrimitiveChord(G,ChordType.MAJ, 0);
    private static final Chord G_MAJ_REALIZED = new Chord(
            new Note(B,4), new Note(D, 4), new Note(G, 3), 
            new Note(G, 2),  G_MAJ_ROOT);
            
    private static final PrimitiveChord C_MAJ_ROOT = 
            new PrimitiveChord(C, ChordType.MAJ, 0);
    private static final Chord C_MAJ_REALIZED = new Chord(
            new Note(C, 5), new Note(E, 4), new Note(G, 3), 
            new Note(C, 3),  C_MAJ_ROOT);

    @Test
    public void emptyTest(){
        ChordProgressionDeprecated empty = ChordProgressionDeprecated.empty();
        assertEquals(0, empty.length());
        assertEquals("", empty.toString());
    }

    @Test
    public void oneChordTest(){
        ChordProgressionDeprecated empty = ChordProgressionDeprecated.empty();
        ChordProgressionDeprecated oneChord = 
                empty.append(C_MAJ_REALIZED);
        assertEquals(1, oneChord.length());
        assertEquals("\n[C5|E4|G3|C3]", oneChord.toString());
    }

    @Test
    public void progsTest(){
        ChordProgressionDeprecated empty = ChordProgressionDeprecated.empty();
        ChordProgressionDeprecated prog = empty.append(
                C_MAJ_64_REALIZED).append(G_MAJ_REALIZED).append(C_MAJ_REALIZED); 
        assertEquals(3, prog.length());
        assertEquals("\n[C5|E4|G3|G2]\n[B4|D4|G3|G2]\n[C5|E4|G3|C3]", 
                prog.toString());
    }

}
