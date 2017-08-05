package solver;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import chords.Chord;
import music.Interval;
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
    
    @Test
    public void testComputeVoiceMovementsByPosition() {
        Chord previous = new Chord(B4, F4, D4, G3, G_DOM7_ROOT);
        Chord current = new Chord(C5, E4, C4, C3, C_MAJ_ROOT);
        List<Interval> orderedMovements = 
            SolverUtils.computeVoiceMovementsByPosition(
                previous, current
            );
        List<Interval> expectedOrderedMovements = 
            Arrays.asList(
                DN_PFT_5TH, UP_MIN_2ND, DN_MAJ_2ND, DN_MIN_2ND  
            );
        assertEquals(expectedOrderedMovements, orderedMovements);
    }
    

}
