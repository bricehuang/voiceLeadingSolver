package chords;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import test_framework.MusicTestFramework;

public class BasicChordTest extends MusicTestFramework {
    
    private static final BasicChord C_MAJ = new BasicChord(
        C, C, E, C, C_MAJ_ROOT
    );
    private static final BasicChord C_MIN = new BasicChord(
        C, C, Eb, C, C_MIN_ROOT
    );
    private static final BasicChord Fs_DIM = new BasicChord(
        A, C, A, Fs, Fs_DIM_ROOT
    );
    
    private static final BasicChord G_DOM7 = new BasicChord(
        B, D, F, G, G_DOM7_ROOT
    );
    private static final BasicChord Fs_DIM7 = new BasicChord(
        Eb, C, Fs, A, Fs_DIM7_65
    );
    private static final BasicChord C_MAJ7 = new BasicChord(
        B, G, E, C, C_MAJ7_ROOT
    );
    private static final BasicChord C_MIN7 = new BasicChord(
        Bb, G, Eb, C, C_MIN7_ROOT
    );
    private static final BasicChord D_HDIM7 = new BasicChord(
        C, Ab, D, F, D_HDIM7_65
    );
            
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }

    @Test
    public void toStringTest(){
        assertEquals("[C|C|E|C]", C_MAJ.toString());
        assertEquals("[A|C|A|F+]", Fs_DIM.toString());
        assertEquals("[C|C|E-|C]", C_MIN.toString());
        assertEquals("[B|D|F|G]", G_DOM7.toString());
        assertEquals("[E-|C|F+|A]", Fs_DIM7.toString());
        assertEquals("[B|G|E|C]", C_MAJ7.toString());
        assertEquals("[B-|G|E-|C]", C_MIN7.toString());
        assertEquals("[C|A-|D|F]", D_HDIM7.toString());
    }

    @Test
    public void renderInKeyTest(){
        assertEquals("[C|C|E|C]", C_MAJ.renderInKey(C_MAJOR));
        assertEquals("[C|C|E|C]", C_MIN.renderInKey(C_MINOR));
        assertEquals("[A|C|A|F]", Fs_DIM.renderInKey(E_MINOR));
        assertEquals("[B=|D|F|G]", G_DOM7.renderInKey(C_MINOR));
        assertEquals("[E-|C|F|A]", Fs_DIM7.renderInKey(G_MAJOR));
        assertEquals("[B|G|E|C]", C_MAJ7.renderInKey(C_MAJOR));
        assertEquals("[B|G|E|C]", C_MIN7.renderInKey(C_MINOR));
        assertEquals("[C|A|D|F]", D_HDIM7.renderInKey(C_MINOR));
    }

}
