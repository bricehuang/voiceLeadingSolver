package score_data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import score_data.ChordPenaltyType;
import score_data.ChordScore;
import test_framework.MusicTestFramework;

public class ChordScoreTest extends MusicTestFramework {

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
        
    }

    @Test
    public void testBasic(){
        ChordScore score = new ChordScore();
        assertEquals(0, score.totalScore());
        assertEquals("Total Penalty: 0\n", score.toString());
        assertEquals(0, score.getPenaltyCount().size());
    }
    
    @Test
    public void testAddPenalty(){
        ChordScore score = new ChordScore();
        score.addPenalty(DOUBLED_LEADING_TONE);
        
        assertEquals(
            DOUBLED_LEADING_TONE.value(), score.totalScore()
        );
        Map<ChordPenaltyType, Integer> penaltyCount 
            = score.getPenaltyCount();
        assertEquals(1, penaltyCount.size());
        assertTrue(penaltyCount.get(DOUBLED_LEADING_TONE) == 1);        
    }

    @Test
    public void testUpdate(){
		ChordScore score = new ChordScore();
    		
        ChordScore update1 = new ChordScore();
        update1.addPenalty(DOUBLED_LEADING_TONE);
        update1.addPenalty(DOUBLE_DOUBLING);
        update1.addPenalty(BAD_TRIPLING);
        score.updatePenalty(update1);
        
        ChordScore update2 = new ChordScore();
        update2.addPenalty(BAD_TRIPLING);
        update2.addPenalty(CADENCE_DOUBLING);
        score.updatePenalty(update2);
        
        assertEquals(
            DOUBLED_LEADING_TONE.value() 
                + DOUBLE_DOUBLING.value() 
                + BAD_TRIPLING.value()*2 
                + CADENCE_DOUBLING.value(),
            score.totalScore()
        );
        Map<ChordPenaltyType, Integer> penaltyCount 
            = score.getPenaltyCount();
        assertEquals(4, penaltyCount.size());
        assertTrue(penaltyCount.get(DOUBLED_LEADING_TONE) == 1);
        assertTrue(penaltyCount.get(DOUBLE_DOUBLING) == 1);
        assertTrue(penaltyCount.get(BAD_TRIPLING) == 2);
        assertTrue(penaltyCount.get(CADENCE_DOUBLING) == 1);
    }
}
