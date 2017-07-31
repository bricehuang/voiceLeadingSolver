package solver;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import chords.Chord;
import test_framework.MusicTestFramework;

public class SolverUtilsTest extends MusicTestFramework {
    
    private static final Chord C_MAJ_ROOT_REALIZED = new Chord(
        C5, E4, G3, C3, C_MAJ_ROOT
    );
    private static final Chord C_DOM7_ROOT_REALIZED = new Chord(
        Bb4, E4, G3, C3, C_DOM7_ROOT
    );

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    @Test
    public void testSpellNotes() {
        assertEquals(
            Arrays.asList(C5, E4, G3, C3), 
            SolverUtils.spellNotes(C_MAJ_ROOT_REALIZED)
        );
        assertEquals(
            Arrays.asList(Bb4, E4, G3, C3), 
            SolverUtils.spellNotes(C_DOM7_ROOT_REALIZED)
        );
    }
    
    @Test
    public void testSpellBasicNotes() {
        assertEquals(
            Arrays.asList(C, E, G, C), 
            SolverUtils.spellBasicNotes(C_MAJ_ROOT_REALIZED)
        );
        assertEquals(
            Arrays.asList(Bb, E, G, C), 
            SolverUtils.spellBasicNotes(C_DOM7_ROOT_REALIZED)
        );
    }
    

}
