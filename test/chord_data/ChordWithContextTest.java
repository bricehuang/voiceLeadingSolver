package chord_data;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import chords.Chord;
import test_framework.MusicTestFramework;

public class ChordWithContextTest extends MusicTestFramework {

    private static final Chord C_MAJ_REALIZED = new Chord(
        C5, E4, G3, C3, C_MAJ_ROOT
    );
    
    private static final ChordWithContext C_MAJ_CADENCE = new ChordWithContext(
		C_MAJ_REALIZED, C_MAJOR, CADENCE
	);
    private static final ChordWithContext C_MAJ_NONCADENCE = new ChordWithContext(
        C_MAJ_REALIZED, C_MAJOR, NO_CONTEXTS
	);
    private static final ChordWithContext C_MAJ_IN_G = new ChordWithContext(
        C_MAJ_REALIZED, G_MAJOR, NO_CONTEXTS
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
