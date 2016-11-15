package solver;

import org.junit.Test;

import chords.BasicChord;
import chords.Chord;
import chords.ChordType;
import chords.PrimitiveChord;
import music.BasicNote;
import music.Key;
import music.Note;

public class ScorerTest {
    
    private static final BasicNote C = new BasicNote(0,0);
    private static final BasicNote D = new BasicNote(1,2);
    private static final BasicNote E = new BasicNote(2,4);
    private static final BasicNote F = new BasicNote(3,5);
    private static final BasicNote G = new BasicNote(4,7);
    private static final BasicNote A = new BasicNote(5,9);

    private static final Key C_MAJOR = new Key(0,true);
    private static final Scorer C_MAJOR_SCORER = new Scorer(C_MAJOR, true);
    
    private static final Chord C_MAJOR_DOUBLE_ROOT = new Chord(
            new Note(C, 5), new Note(E, 4), new Note(G, 3), new Note(C, 3), 
            new BasicChord(C, E, G, C, new PrimitiveChord(C, ChordType.MAJ, 0))
            );
    
    private static final Chord C_MAJOR_DOUBLE_THIRD= new Chord(
            new Note(E, 5), new Note(E, 4), new Note(G, 3), new Note(C, 3), 
            new BasicChord(E, E, G, C, new PrimitiveChord(C, ChordType.MAJ, 0))
            );
     
    private static final Chord C_MAJOR_DOUBLE_ROOT_THIRD= new Chord(
            new Note(E, 5), new Note(E, 4), new Note(C, 4), new Note(C, 3), 
            new BasicChord(E, E, C, C, new PrimitiveChord(C, ChordType.MAJ, 0))
            );

    private static final Chord C_MAJOR_TRIPLE_ROOT_INCOMPLETE = new Chord(
            new Note(C, 5), new Note(C, 4), new Note(E, 3), new Note(C, 3),  
            new BasicChord(C, C, E, C, new PrimitiveChord(C, ChordType.MAJ, 0))
            );

    private static final Chord D_MINOR_DOUBLE_ROOT = new Chord(
            new Note(D, 5), new Note(F, 4), new Note(A, 3), new Note(D, 3), 
            new BasicChord(D, F, A, D, new PrimitiveChord(D, ChordType.MIN, 0))
            );

    private static final Chord D_MINOR_DOUBLE_THIRD = new Chord(
            new Note(A, 4), new Note(F, 4), new Note(D, 4), new Note(F, 3), 
            new BasicChord(A, F, D, F, new PrimitiveChord(D, ChordType.MIN, 1))
            );

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
        
    }
    
    @Test
    public void testGoodDoublingSD1(){
        C_MAJOR_SCORER.scoreChord(C_MAJOR_DOUBLE_ROOT);
    }

    @Test
    public void testBadDoublingSD3(){
        C_MAJOR_SCORER.scoreChord(C_MAJOR_DOUBLE_THIRD);
    }

    @Test
    public void testBadDoubleDouble(){
        C_MAJOR_SCORER.scoreChord(C_MAJOR_DOUBLE_ROOT_THIRD);
    }

    
    @Test
    public void testTripledSD1(){
        C_MAJOR_SCORER.scoreChord(C_MAJOR_TRIPLE_ROOT_INCOMPLETE);
    }

    @Test
    public void testBadDoublingSD2(){
        C_MAJOR_SCORER.scoreChord(D_MINOR_DOUBLE_ROOT);
    }

    @Test
    public void testGoodDoublingSD4(){
        C_MAJOR_SCORER.scoreChord(D_MINOR_DOUBLE_THIRD);
    }
}
