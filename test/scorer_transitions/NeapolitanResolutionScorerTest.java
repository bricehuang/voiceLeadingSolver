package scorer_transitions;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import chord_data.ChordWithContext;
import score_data.TransitionPenaltyType;
import test_framework.MusicTestFramework;

public class NeapolitanResolutionScorerTest extends MusicTestFramework {

    private Map<TransitionPenaltyType, Integer> computePenalties(
        ChordWithContext previous, ChordWithContext current
    ) {
        return computeTransitionPenalties(
            new NeapolitanResolutionScorer(), previous, current
        );
    }

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    @Test
    public void testNeapolitanBadDoubling(){
        ChordWithContext previous = makeChordWithContext(
            Eb5, Ab4, Eb4, C3, Ab_MAJ_6, G_MINOR, NEAPOLITAN_PREDOMINANT
        );
        ChordWithContext current = makeChordWithContext(
            D5, A4, Fs4, D3, D_MAJ_ROOT, G_MINOR, CADENTIAL_V
        );
        Map<TransitionPenaltyType, Integer> penaltyCount = 
            computePenalties(previous, current);
        assertEquals(1, penaltyCount.keySet().size());
        assertEquals((Integer) 1, penaltyCount.get(BAD_NEAPOLITAN_RES));
    }

    @Test
    public void testNeapolitanVGood(){
        ChordWithContext previous = makeChordWithContext(
            Ab4, Eb4, C4, C3, Ab_MAJ_6, G_MINOR, NEAPOLITAN_PREDOMINANT
        );
        ChordWithContext current = makeChordWithContext(
            Fs4, D4, A3, D3, D_MAJ_ROOT, G_MINOR, CADENTIAL_V
        );
        Map<TransitionPenaltyType, Integer> penaltyCount = 
            computePenalties(previous, current);
        assertEquals(2, penaltyCount.keySet().size());
        assertEquals((Integer) 1, penaltyCount.get(NEAPOLITAN_DIM_3RD_OK));
        assertEquals((Integer) 2, penaltyCount.get(NEAPOLITAN_BIG_MOVE_OK));
    }

    @Test
    public void testNeapolitanVBad(){
        ChordWithContext previous = makeChordWithContext(
            Ab4, Eb4, C4, C3, Ab_MAJ_6, G_MINOR, NEAPOLITAN_PREDOMINANT
        );
        ChordWithContext current = makeChordWithContext(
            Fs4, D4, D4, D3, D_MAJ_ROOT, G_MINOR, CADENTIAL_V
        );
        Map<TransitionPenaltyType, Integer> penaltyCount = 
            computePenalties(previous, current);
        assertEquals(1, penaltyCount.keySet().size());
        assertEquals((Integer) 1, penaltyCount.get(BAD_NEAPOLITAN_RES));
    }

    
     @Test
    public void testNeapolitanI64Good(){
        ChordWithContext previous = makeChordWithContext(
            Db5, F4, Ab3, F2, Db_MAJ_6, C_MAJOR, NEAPOLITAN_PREDOMINANT
        );
        ChordWithContext current = makeChordWithContext(
            C5, E4, G3, G2, C_MAJ_64, C_MAJOR, CADENTIAL_I64
        );
        Map<TransitionPenaltyType, Integer> penaltyCount = 
            computePenalties(previous, current);
        assertEquals(0, penaltyCount.keySet().size());
    }

    @Test
    public void testNeapolitanI64Bad(){
        ChordWithContext previous = makeChordWithContext(
            Db5, F4, Ab3, F2, Db_MAJ_6, C_MAJOR, NEAPOLITAN_PREDOMINANT
        );
        ChordWithContext current = makeChordWithContext(
            E5, G4, C4, G2, C_MAJ_64, C_MAJOR, CADENTIAL_I64
        );
        Map<TransitionPenaltyType, Integer> penaltyCount = 
            computePenalties(previous, current);
        assertEquals(1, penaltyCount.keySet().size());
        assertEquals((Integer) 1, penaltyCount.get(BAD_NEAPOLITAN_RES));
    }
    
    @Test
   public void testNeapolitani64Good(){
       ChordWithContext previous = makeChordWithContext(
           Db5, F4, Ab3, F2, Db_MAJ_6, C_MINOR, NEAPOLITAN_PREDOMINANT
       );
       ChordWithContext current = makeChordWithContext(
           C5, Eb4, G3, G2, C_MIN_64, C_MINOR, CADENTIAL_I64
       );
       Map<TransitionPenaltyType, Integer> penaltyCount = 
           computePenalties(previous, current);
       assertEquals(0, penaltyCount.keySet().size());
   }

   @Test
   public void testNeapolitani64Bad(){
       ChordWithContext previous = makeChordWithContext(
           Db5, F4, Ab3, F2, Db_MAJ_6, C_MINOR, NEAPOLITAN_PREDOMINANT
       );
       ChordWithContext current = makeChordWithContext(
           Eb5, G4, C4, G2, C_MIN_64, C_MINOR, CADENTIAL_I64
       );
       Map<TransitionPenaltyType, Integer> penaltyCount = 
           computePenalties(previous, current);
       assertEquals(1, penaltyCount.keySet().size());
       assertEquals((Integer) 1, penaltyCount.get(BAD_NEAPOLITAN_RES));
   }
}
