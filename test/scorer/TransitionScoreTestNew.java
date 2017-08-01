package scorer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import score_data.TransitionPenaltyType;
import score_data.TransitionScoreNew;
import test_framework.MusicTestFramework;

public class TransitionScoreTestNew extends MusicTestFramework {

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
        
    }

    @Test
    public void testBasic(){
        TransitionScoreNew score = new TransitionScoreNew();
        assertEquals(0, score.totalScore());
        assertEquals("Total Penalty: 0\n", score.toString());
        assertEquals(0, score.getPenaltyCount().size());
    }
    
    @Test
    public void testAddPenalty(){
        TransitionScoreNew score = new TransitionScoreNew();
        score.addPenalty(PARALLEL);
        
        assertEquals(
            PARALLEL.value(), score.totalScore()
        );
        Map<TransitionPenaltyType, Integer> penaltyCount 
            = score.getPenaltyCount();
        assertEquals(1, penaltyCount.size());
        assertTrue(penaltyCount.get(PARALLEL) == 1);        
    }

    @Test
    public void testUpdate(){
        TransitionScoreNew score = new TransitionScoreNew();
    		
        TransitionScoreNew update1 = new TransitionScoreNew();
        update1.addPenalty(PARALLEL);
        update1.addPenalty(DIRECT);
        update1.addPenalty(DOM_SEVEN_RES);
        score.updatePenalty(update1);
        
        TransitionScoreNew update2 = new TransitionScoreNew();
        update2.addPenalty(DOM_SEVEN_RES);
        update2.addPenalty(DIM_SEVEN_RES);
        score.updatePenalty(update2);
        
        assertEquals(
            PARALLEL.value() 
                + DIRECT.value() 
                + DOM_SEVEN_RES.value()*2 
                + DIM_SEVEN_RES.value(),
            score.totalScore()
        );
        Map<TransitionPenaltyType, Integer> penaltyCount 
            = score.getPenaltyCount();
        assertEquals(4, penaltyCount.size());
        assertTrue(penaltyCount.get(PARALLEL) == 1);
        assertTrue(penaltyCount.get(DIRECT) == 1);
        assertTrue(penaltyCount.get(DOM_SEVEN_RES) == 2);
        assertTrue(penaltyCount.get(DIM_SEVEN_RES) == 1);
    }
}
