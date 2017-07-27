package chord_data;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import chords.Chord;
import chords.ChordType;
import chords.PrimitiveChord;
import music.BasicNote;
import music.Key;
import music.Note;

public class ChordWithContextTest {

    private static final BasicNote C = new BasicNote(0,0);
    private static final BasicNote E = new BasicNote(2,4);
    private static final BasicNote G = new BasicNote(4,7);
    
    private static final Key C_MAJOR = new Key(C, true);
    private static final Key G_MAJOR = new Key(G, true);

    private static final PrimitiveChord C_MAJ = 
        new PrimitiveChord(C, ChordType.MAJ, 0);
    private static final Chord C_MAJ_REALIZED = new Chord(
        new Note(C, 5), 
        new Note(E, 4), 
        new Note(G, 3), 
        new Note(C, 3),  
        C_MAJ
    );
    
    private static final ChordWithContext C_MAJ_CADENCE = new ChordWithContext(
    		C_MAJ_REALIZED,
    		C_MAJOR, 
    		new HashSet<>(Arrays.asList(ContextTag.CADENCE))
    	);
    private static final ChordWithContext C_MAJ_NONCADENCE = new ChordWithContext(
    		C_MAJ_REALIZED,
    		C_MAJOR, 
    		new HashSet<>(Arrays.asList())
    	);
    private static final ChordWithContext C_MAJ_IN_G = new ChordWithContext(
    		C_MAJ_REALIZED,
    		G_MAJOR, 
    		new HashSet<>(Arrays.asList())
    	);

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    @Test
    public void testEquals() {
    		assertTrue(C_MAJ_CADENCE.equals(C_MAJ_CADENCE));
    		assertFalse(C_MAJ_CADENCE.equals(C_MAJ_NONCADENCE));
    		assertFalse(C_MAJ_NONCADENCE.equals(C_MAJ_IN_G));
    }
}
