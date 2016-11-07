package chords;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import music.*;

public class BasicChordTest {
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }

    private static final BasicNote C = new BasicNote(0,0);
    private static final BasicNote D = new BasicNote(1,2);
    private static final BasicNote E = new BasicNote(2,4);
    private static final BasicNote Eb = new BasicNote(2,3);
    private static final BasicNote F = new BasicNote(3,5);
    private static final BasicNote G = new BasicNote(4,7);
    private static final BasicNote B = new BasicNote(6,11);
    
    private static final BasicChord G_DOM_SEVEN = new BasicChord(
            B, D, F, G, new PrimitiveChord(G,ChordType.DOM7, 0));
    private static final BasicChord C_MAJ = new BasicChord(
            C, C, E, C, new PrimitiveChord(C, ChordType.MAJ, 0));
    private static final BasicChord C_MIN = new BasicChord(
            C, C, Eb, C, new PrimitiveChord(C, ChordType.MIN, 0));
    private static final Key C_MINOR = new Key(-3, false);
    
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
