package chords;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import test_framework.MusicTestFramework;

public class ChordTest extends MusicTestFramework {
        
    private static final Chord G_DOM_SEVEN_REALIZED = new Chord(
        B4, D4, F3, G2, G_DOM7_ROOT
    );
            
    private static final Chord C_MAJ_REALIZED = new Chord(
        C5, C4, E3, C3, C_MAJ_ROOT
    );

    private static final Chord C_MIN_REALIZED = new Chord(
        C5, C4, Eb3, C3, C_MIN_ROOT
    );

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }

    @Test
    public void toStringTest(){
        assertEquals("[B4|D4|F3|G2]", G_DOM_SEVEN_REALIZED.toString());
        assertEquals("[C5|C4|E3|C3]", C_MAJ_REALIZED.toString());
    }

    @Test
    public void renderInKeyTest(){
        assertEquals("[B=4|D4|F3|G2]", G_DOM_SEVEN_REALIZED.renderInKey(C_MINOR));
        assertEquals("[C5|C4|E3|C3]", C_MIN_REALIZED.renderInKey(C_MINOR));
    }


}
