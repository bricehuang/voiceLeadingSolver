package solver;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import chords.BasicChord;
import chords.Chord;
import chords.ChordProgression;
import chords.ChordType;
import chords.PrimitiveChord;
import music.BasicNote;
import music.Note;

public class SortedFiniteProgListTest {

    private static final BasicNote D = new BasicNote(1,2);
    private static final BasicNote F = new BasicNote(3,5);
    private static final BasicNote G = new BasicNote(4,7);
    private static final BasicNote B = new BasicNote(6,11);

    private static final BasicChord G_DOM_SEVEN = new BasicChord(
            B, D, F, G, new PrimitiveChord(G,ChordType.DOM7, 0));
    private static final Chord G_DOM_SEVEN_REALIZED = new Chord(
            new Note(B,4), new Note(D, 4), new Note(F, 3), 
            new Note(G, 2),  G_DOM_SEVEN);
    private static final ChordProgression TEST_G_DOM7= 
            ChordProgression.empty().append(G_DOM_SEVEN_REALIZED);

    private static final ChordProgWithScore TEST1 = new ChordProgWithScore(TEST_G_DOM7, 1);
    private static final ChordProgWithScore TEST2 = new ChordProgWithScore(TEST_G_DOM7, 2);
    private static final ChordProgWithScore TEST3 = new ChordProgWithScore(TEST_G_DOM7, 3);
    private static final ChordProgWithScore TEST4 = new ChordProgWithScore(TEST_G_DOM7, 4);

    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    //NOTE: THESE TESTS DEPEND ON PROGRESSIONS_TO_TRACK = 3

    @Test
    public void testBasic(){
        SortedFiniteProgList list = new SortedFiniteProgList();
        list.addProgression(TEST4);
        assertEquals(1,list.size());
        assertEquals(TEST4,list.getProgressions().get(0));
    }
    
    @Test
    public void testOrdering(){
        SortedFiniteProgList list = new SortedFiniteProgList();
        list.addProgression(TEST4);
        list.addProgression(TEST2);
        list.addProgression(TEST3);
        assertEquals(3,list.getProgressions().size());
        assertEquals(TEST2,list.getProgressions().get(0));
        assertEquals(TEST3,list.getProgressions().get(1));
        assertEquals(TEST4,list.getProgressions().get(2));
    }
    
    @Test
    public void testOverflow(){
        SortedFiniteProgList list = new SortedFiniteProgList();
        list.addProgression(TEST4);
        list.addProgression(TEST2);
        list.addProgression(TEST3);
        list.addProgression(TEST1);
        assertEquals(3,list.getProgressions().size());
        assertEquals(TEST1,list.getProgressions().get(0));
        assertEquals(TEST2,list.getProgressions().get(1));
        assertEquals(TEST3,list.getProgressions().get(2));
    }


}
