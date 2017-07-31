package chords;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import test_framework.MusicTestFramework;

public class ChordProgressionTest extends MusicTestFramework {
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
        
    private static final Chord C_MAJ_64_REALIZED = new Chord(
        C5, E4, G3, G2, C_MAJ_64
    );
    private static final Chord G_MAJ_REALIZED = new Chord(
        B4, D4, G3, G2, G_MAJ_ROOT
    );
    private static final Chord C_MAJ_REALIZED = new Chord(
        C5, E4, G3, C3, C_MAJ_ROOT
    ); 
    
    @Test
    public void emptyTest(){
        ChordProgressionDeprecated empty = ChordProgressionDeprecated.empty();
        assertEquals(0, empty.length());
        assertEquals("", empty.toString());
    }

    @Test
    public void oneChordTest(){
        ChordProgressionDeprecated empty = ChordProgressionDeprecated.empty();
        ChordProgressionDeprecated oneChord = empty.append(C_MAJ_REALIZED);
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
