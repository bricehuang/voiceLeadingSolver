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
    
    private static final boolean debug = true;
    
    private static final BasicNote C = new BasicNote(0,0);
    private static final BasicNote D = new BasicNote(1,2);
    private static final BasicNote E = new BasicNote(2,4);
    private static final BasicNote F = new BasicNote(3,5);
    private static final BasicNote G = new BasicNote(4,7);
    private static final BasicNote A = new BasicNote(5,9);
    private static final BasicNote B = new BasicNote(6,11);

    private static final Key C_MAJOR = new Key(0,debug);
    private static final Scorer C_MAJOR_SCORER = new Scorer(C_MAJOR, true);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
        
    }
    
    /************************
     * Tests for scoreChord *
     ************************/
    
    @Test
    public void testGoodDoublingSD1(){
        Chord cMajorDoubleRoot = new Chord(
                new Note(C, 5), new Note(E, 4), new Note(G, 3), new Note(C, 3), 
                new BasicChord(C, E, G, C, new PrimitiveChord(C, ChordType.MAJ, 0))
                );
        C_MAJOR_SCORER.scoreChord(cMajorDoubleRoot);
    }

    @Test
    public void testBadDoublingSD3(){
        Chord cMajorDoubleThird = new Chord(
                new Note(E, 5), new Note(E, 4), new Note(G, 3), new Note(C, 3), 
                new BasicChord(E, E, G, C, new PrimitiveChord(C, ChordType.MAJ, 0))
                );
        C_MAJOR_SCORER.scoreChord(cMajorDoubleThird);
    }

    @Test
    public void testBadDoubleDouble(){
        Chord cMajorDoubleRootThirdIncomplete = new Chord(
                new Note(E, 5), new Note(E, 4), new Note(C, 4), new Note(C, 3), 
                new BasicChord(E, E, C, C, new PrimitiveChord(C, ChordType.MAJ, 0))
                );
        C_MAJOR_SCORER.scoreChord(cMajorDoubleRootThirdIncomplete);
    }
    
    @Test
    public void testTripledSD1(){
        Chord cMajorTripleRootIncomplete= new Chord(
                new Note(C, 5), new Note(C, 4), new Note(E, 3), new Note(C, 3),  
                new BasicChord(C, C, E, C, new PrimitiveChord(C, ChordType.MAJ, 0))
                );
        C_MAJOR_SCORER.scoreChord(cMajorTripleRootIncomplete);
    }

    @Test
    public void testBadDoublingSD2(){
        Chord dMinorDoubleRoot = new Chord(
                new Note(D, 5), new Note(F, 4), new Note(A, 3), new Note(D, 3), 
                new BasicChord(D, F, A, D, new PrimitiveChord(D, ChordType.MIN, 0))
                );
        C_MAJOR_SCORER.scoreChord(dMinorDoubleRoot);
    }

    @Test
    public void testGoodDoublingSD4(){
        Chord dMinorDoubleThird = new Chord(
                new Note(A, 4), new Note(F, 4), new Note(D, 4), new Note(F, 3), 
                new BasicChord(A, F, D, F, new PrimitiveChord(D, ChordType.MIN, 1))
                );
        C_MAJOR_SCORER.scoreChord(dMinorDoubleThird);
    }
    
    @Test
    public void testBadDoublingSD3VoiceOverlap(){
        Chord cMajorVoiceOverlapDoubleThird = new Chord(
                new Note(C, 5), new Note(E, 4), new Note(E, 4), new Note(G, 3), 
                new BasicChord(C, E, E, G, new PrimitiveChord(C, ChordType.MAJ, 2))
                );
        C_MAJOR_SCORER.scoreChord(cMajorVoiceOverlapDoubleThird);
    }
    
    /*****************************
     * Tests for scoreTransition *
     *****************************/

    @Test
    public void testParallels(){
        Chord dMinorDoubleThird = new Chord(
                new Note(F, 5), new Note(A, 4), new Note(D, 4), new Note(F, 3), 
                new BasicChord(F, A, D, F, new PrimitiveChord(D, ChordType.MIN, 1))
                );
        Chord cMajorDoubleRoot = new Chord(
                new Note(E, 5), new Note(G, 4), new Note(C, 4), new Note(C, 3), 
                new BasicChord(E, G, C, C, new PrimitiveChord(C, ChordType.MAJ, 0))
                );
        C_MAJOR_SCORER.scoreTransition(dMinorDoubleThird, cMajorDoubleRoot);
    }
    
    @Test
    public void testNotParallel(){
        Chord cMajor64 = new Chord(
                new Note(C, 5), new Note(G, 4), new Note(E, 4), new Note(G, 3), 
                new BasicChord(C, G, E, G, new PrimitiveChord(C, ChordType.MAJ, 2))
                );
        Chord gDominantSeven = new Chord(
                new Note(B, 4), new Note(F, 4), new Note(D, 4), new Note(G, 3), 
                new BasicChord(B, F, D, G, new PrimitiveChord(G, ChordType.DOM7, 0))
                );
        C_MAJOR_SCORER.scoreTransition(cMajor64, gDominantSeven);
    }

}
