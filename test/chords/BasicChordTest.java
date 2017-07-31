package chords;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import test_framework.MusicTestFramework;

public class BasicChordTest extends MusicTestFramework {
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    private static final BasicChord G_DOM_SEVEN = new BasicChord(
        B, D, F, G, G_DOM7_ROOT
    );
    private static final BasicChord C_MAJ = new BasicChord(
        C, C, E, C, C_MAJ_ROOT
    );
    private static final BasicChord C_MIN = new BasicChord(
        C, C, Eb, C, C_MIN_ROOT
    );
    
    @Test
    public void toStringTest(){
        assertEquals("[B|D|F|G]", G_DOM_SEVEN.toString());
        assertEquals("[C|C|E|C]", C_MAJ.toString());
    }

    @Test
    public void renderInKeyTest(){
        assertEquals("[B=|D|F|G]", G_DOM_SEVEN.renderInKey(C_MINOR));
        assertEquals("[C|C|E|C]", C_MIN.renderInKey(C_MINOR));
    }


}
