package solver;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import chord_data.ChordProgressionWithContext;
import chord_data.ChordWithContext;
import chords.Chord;
import test_framework.MusicTestFramework;

public class ScoredProgressionTest extends MusicTestFramework {
    
    private static final Chord C_MAJ_64_REALIZED = new Chord(
        C5, E4, G3, G2, C_MAJ_64
    );    		

    private static final Chord G_MAJ_REALIZED = new Chord(
        B4, D4, G3, G2, G_MAJ_ROOT
    );

    private static final Chord C_MAJ_REALIZED = new Chord(
        C5, E4, G3, C3, C_MAJ_ROOT
    );
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    @Test
    public void toStringTest(){
        ChordProgressionWithContext empty = ChordProgressionWithContext.empty();
        ChordProgressionWithContext prog = empty.append(
    		new ChordWithContext(C_MAJ_64_REALIZED, C_MAJOR, CADENTIAL_I64)
    	).append(
    		new ChordWithContext(G_MAJ_REALIZED, C_MAJOR, CADENTIAL_V)
    	).append(
    		new ChordWithContext(C_MAJ_REALIZED, C_MAJOR, CADENCE)
    	); 
        ScoredProgression progression = new ScoredProgression(prog, 42);
        assertEquals(
    		"\n[C5|E4|G3|G2]||C Major|| [Cadential I64]" + 
    		"\n[B4|D4|G3|G2]||C Major|| [Cadential V]" + 
    		"\n[C5|E4|G3|C3]||C Major|| [Cadence]" + 
    		"\nScore: 42", 
            progression.toString()
        );
    }
}
