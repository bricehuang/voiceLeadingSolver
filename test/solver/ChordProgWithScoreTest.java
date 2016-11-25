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

public class ChordProgWithScoreTest {
    
    private static final BasicNote C = new BasicNote(0,0);
    private static final BasicNote D = new BasicNote(1,2);
    private static final BasicNote E = new BasicNote(2,4);
    private static final BasicNote G = new BasicNote(4,7);
    private static final BasicNote B = new BasicNote(6,11);
    
    private static final PrimitiveChord C_MAJ_64 = 
            new PrimitiveChord(C, ChordType.MAJ, 2);
    private static final Chord C_MAJ_64_REALIZED = new Chord(
            new Note(C, 5), new Note(E, 4), new Note(G, 3), 
            new Note(G, 2),  C_MAJ_64);
    
    private static final PrimitiveChord G_MAJ = 
            new PrimitiveChord(G,ChordType.MAJ, 0);
    private static final Chord G_MAJ_REALIZED = new Chord(
            new Note(B,4), new Note(D, 4), new Note(G, 3), 
            new Note(G, 2),  G_MAJ);
            
    private static final PrimitiveChord C_MAJ = 
            new PrimitiveChord(C, ChordType.MAJ, 0);
    private static final Chord C_MAJ_REALIZED = new Chord(
            new Note(C, 5), new Note(E, 4), new Note(G, 3), 
            new Note(C, 3),  C_MAJ);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    @Test
    public void toStringTest(){
        ChordProgression empty = ChordProgression.empty();
        ChordProgression prog = empty.append(
                C_MAJ_64_REALIZED).append(G_MAJ_REALIZED).append(C_MAJ_REALIZED); 
        ChordProgWithScore progWithScore = new ChordProgWithScore(prog, 42);
        assertEquals("\n[C5|E4|G3|G2]\n[B4|D4|G3|G2]\n[C5|E4|G3|C3]\nScore: 42", 
                progWithScore.toString());
    }
}
