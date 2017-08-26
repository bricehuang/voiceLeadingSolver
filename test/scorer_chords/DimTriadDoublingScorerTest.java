package scorer_chords;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import chord_data.ChordWithContext;
import score_data.ChordPenaltyType;
import test_framework.MusicTestFramework;

public class DimTriadDoublingScorerTest extends MusicTestFramework {

    private Map<ChordPenaltyType, Integer> computePenalties(ChordWithContext chord) {
        return computeChordPenalties(new DimTriadDoublingScorer(), chord);
    }

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
        
    }

    @Test
    public void testGoodDoublingMajor(){
        ChordWithContext chord = makeChordWithContext(
            C5, A4, Fs4, A3, Fs_DIM_6, G_MAJOR, NO_CONTEXTS
        );
        Map<ChordPenaltyType, Integer> penaltyCount = 
            computePenalties(chord);

        assertEquals(1, penaltyCount.keySet().size());
        assertEquals((Integer) 1, penaltyCount.get(BAD_DOUBLING_OK_BECAUSE_DIM));
    }

    @Test
    public void testBadDoublingMajor(){
        ChordWithContext chord = makeChordWithContext(
            E5, G4, Bb3, E3, E_DIM_ROOT, F_MAJOR, NO_CONTEXTS
        );
        Map<ChordPenaltyType, Integer> penaltyCount = 
            computePenalties(chord);

        assertEquals(1, penaltyCount.keySet().size());
        assertEquals((Integer) 1, penaltyCount.get(BAD_DIM_DOUBLING));
    }

    @Test
    public void testGoodDoublingMinor(){
        ChordWithContext chord = makeChordWithContext(
            F5, Ab4, D4, F3, D_DIM_6, C_MINOR, NO_CONTEXTS
        );
        Map<ChordPenaltyType, Integer> penaltyCount = 
            computePenalties(chord);

        assertEquals(0, penaltyCount.keySet().size());
    }

    @Test
    public void testBadDoublingMinor(){
        ChordWithContext chord = makeChordWithContext(
            D5, D4, Gs3, B2, Gs_DIM_6, Fs_MINOR, NO_CONTEXTS
        );
        Map<ChordPenaltyType, Integer> penaltyCount = 
            computePenalties(chord);

        assertEquals(1, penaltyCount.keySet().size());
        assertEquals((Integer) 1, penaltyCount.get(BAD_DIM_DOUBLING));
    }

    @Test
    public void testBadDoublingNotDim(){
        ChordWithContext chord = makeChordWithContext(
            E5, E4, G3, C3, C_MAJ_ROOT, C_MAJOR, NO_CONTEXTS
        );
        Map<ChordPenaltyType, Integer> penaltyCount = 
            computePenalties(chord);

        assertEquals(0, penaltyCount.keySet().size());
    }

}
