package chords;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import music.BasicNote;
import music.Key;
import music.Note;


public class ChordTest {
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
    private static final Chord G_DOM_SEVEN_REALIZED = new Chord(
            new Note(B,4), new Note(D, 4), new Note(F, 3), 
            new Note(G, 2),  G_DOM_SEVEN);
            
    private static final BasicChord C_MAJ = new BasicChord(
            C, C, E, C, new PrimitiveChord(C, ChordType.MAJ, 0));
    private static final Chord C_MAJ_REALIZED = new Chord(
            new Note(C, 5), new Note(C, 4), new Note(E, 3), 
            new Note(C, 3),  C_MAJ);

    private static final BasicChord C_MIN = new BasicChord(
            C, C, Eb, C, new PrimitiveChord(C, ChordType.MIN, 0));
    private static final Chord C_MIN_REALIZED = new Chord(
            new Note(C, 5), new Note(C, 4), new Note(Eb, 3), 
            new Note(C, 3),  C_MIN);
    private static final Key C_MINOR = new Key(-3, false);

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
