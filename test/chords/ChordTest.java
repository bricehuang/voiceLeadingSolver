package chords;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import test_framework.MusicTestFramework;

public class ChordTest extends MusicTestFramework {
        
    private static final Chord C_MAJ_REALIZED = new Chord(
        C5, C4, E3, C3, C_MAJ_ROOT
    );
    private static final Chord C_MIN_REALIZED = new Chord(
        C5, C4, Eb3, C3, C_MIN_ROOT
    );
    private static final Chord Fs_DIM_REALIZED = new Chord(
        A4, C4, A3, Fs3, Fs_DIM_ROOT
    );

    private static final Chord G_DOM7_REALIZED = new Chord(
        B4, D4, F3, G2, G_DOM7_ROOT
    );
    private static final Chord Fs_DIM7_REALIZED = new Chord(
        Eb5, C5, Fs4, A3, Fs_DIM7_65
    );
    private static final Chord C_MAJ7_REALIZED = new Chord(
        B4, E4, G3, C3, C_MAJ7_ROOT
    );
    private static final Chord C_MIN7_REALIZED = new Chord(
        Bb4, Eb4, G3, C3, C_MIN7_ROOT
    );
            

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }

    @Test
    public void toStringTest(){
        assertEquals("[C5|C4|E3|C3]", C_MAJ_REALIZED.toString());
        assertEquals("[C5|C4|E-3|C3]", C_MIN_REALIZED.toString());
        assertEquals("[A4|C4|A3|F+3]", Fs_DIM_REALIZED.toString());
        assertEquals("[B4|D4|F3|G2]", G_DOM7_REALIZED.toString());
        assertEquals("[E-5|C5|F+4|A3]", Fs_DIM7_REALIZED.toString());
        assertEquals("[B4|E4|G3|C3]", C_MAJ7_REALIZED.toString());
        assertEquals("[B-4|E-4|G3|C3]", C_MIN7_REALIZED.toString());
    }

    @Test
    public void renderInKeyTest(){
        assertEquals("[C5|C4|E3|C3]", C_MAJ_REALIZED.renderInKey(C_MAJOR));
        assertEquals("[C5|C4|E3|C3]", C_MIN_REALIZED.renderInKey(C_MINOR));
        assertEquals("[A4|C4|A3|F3]", Fs_DIM_REALIZED.renderInKey(E_MINOR));
        assertEquals("[B=4|D4|F3|G2]", G_DOM7_REALIZED.renderInKey(C_MINOR));
        assertEquals("[E-5|C5|F4|A3]", Fs_DIM7_REALIZED.renderInKey(G_MAJOR));
        assertEquals("[B4|E4|G3|C3]", C_MAJ7_REALIZED.renderInKey(C_MAJOR));
        assertEquals("[B4|E4|G3|C3]", C_MIN7_REALIZED.renderInKey(C_MINOR));
    }


}
