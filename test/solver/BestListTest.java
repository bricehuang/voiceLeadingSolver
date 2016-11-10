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

public class BestListTest {
    
    private static final BasicNote C = new BasicNote(0,0);
    private static final BasicNote D = new BasicNote(1,2);
    private static final BasicNote E = new BasicNote(2,4);
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
    
    private static final ChordProgression TEST_G_DOM7= 
            ChordProgression.empty().append(G_DOM_SEVEN_REALIZED);
    private static final ChordProgression TEST_PERFECT_CADENCE= 
            ChordProgression.empty().append(G_DOM_SEVEN_REALIZED).append(C_MAJ_REALIZED);

    private static final ChordProgWithScore TEST1 = new ChordProgWithScore(TEST_G_DOM7, 1);
    private static final ChordProgWithScore TEST2 = new ChordProgWithScore(TEST_G_DOM7, 2);
    private static final ChordProgWithScore TEST3 = new ChordProgWithScore(TEST_G_DOM7, 3);
    private static final ChordProgWithScore TEST4 = new ChordProgWithScore(TEST_G_DOM7, 4);
    
    private static final ChordProgWithScore TEST2_PC = new ChordProgWithScore(TEST_PERFECT_CADENCE, 2);
    private static final ChordProgWithScore TEST4_PC = new ChordProgWithScore(TEST_PERFECT_CADENCE, 4);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    //NOTE: THESE TESTS DEPEND ON SortedFiniteProgList.PROGRESSIONS_TO_TRACK = 3

    @Test
    public void testBasic(){
        BestList bestList = new BestList();
        bestList.addProgression(TEST4);
        assertEquals(1,bestList.getProgressions(G_DOM_SEVEN_REALIZED).size());
        assertEquals(TEST4,bestList.getProgressions(G_DOM_SEVEN_REALIZED).get(0));
    }
    
    @Test
    public void testOrdering(){
        BestList bestList = new BestList();
        bestList.addProgression(TEST4);
        bestList.addProgression(TEST2);
        bestList.addProgression(TEST3);
        assertEquals(3,bestList.getProgressions(G_DOM_SEVEN_REALIZED).size());
        assertEquals(TEST2,bestList.getProgressions(G_DOM_SEVEN_REALIZED).get(0));
        assertEquals(TEST3,bestList.getProgressions(G_DOM_SEVEN_REALIZED).get(1));
        assertEquals(TEST4,bestList.getProgressions(G_DOM_SEVEN_REALIZED).get(2));
    }
    
    @Test
    public void testOverflow(){
        BestList bestList = new BestList();
        bestList.addProgression(TEST4);
        bestList.addProgression(TEST2);
        bestList.addProgression(TEST3);
        bestList.addProgression(TEST1);
        assertEquals(3,bestList.getProgressions(G_DOM_SEVEN_REALIZED).size());
        assertEquals(TEST1,bestList.getProgressions(G_DOM_SEVEN_REALIZED).get(0));
        assertEquals(TEST2,bestList.getProgressions(G_DOM_SEVEN_REALIZED).get(1));
        assertEquals(TEST3,bestList.getProgressions(G_DOM_SEVEN_REALIZED).get(2));
    }

    @Test
    public void testIndependence(){
        BestList bestList = new BestList();
        bestList.addProgression(TEST4);
        bestList.addProgression(TEST2);
        bestList.addProgression(TEST3);
        bestList.addProgression(TEST1);
        bestList.addProgression(TEST2_PC);
        bestList.addProgression(TEST4_PC);
        assertEquals(3,bestList.getProgressions(G_DOM_SEVEN_REALIZED).size());
        assertEquals(2,bestList.getProgressions(C_MAJ_REALIZED).size());
        assertEquals(TEST1,bestList.getProgressions(G_DOM_SEVEN_REALIZED).get(0));
        assertEquals(TEST2,bestList.getProgressions(G_DOM_SEVEN_REALIZED).get(1));
        assertEquals(TEST3,bestList.getProgressions(G_DOM_SEVEN_REALIZED).get(2));
        assertEquals(TEST2_PC,bestList.getProgressions(C_MAJ_REALIZED).get(0));
        assertEquals(TEST4_PC,bestList.getProgressions(C_MAJ_REALIZED).get(1));
    }

}
