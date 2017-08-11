package score_data;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import test_framework.MusicTestFramework;

public class TransitionScoreTest extends MusicTestFramework {

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
        
    }

    @Test
    public void testBasic(){
        TransitionScore score = new TransitionScore();
        assertEquals(0, score.totalScore());
        assertEquals("Total Penalty: 0\n", score.toString());
        assertEquals(0, score.getPenaltyCount().size());
    }
    
    @Test
    public void testAddPenalty(){
        TransitionScore score = new TransitionScore();
        score.addPenalty(PARALLEL);
        
        assertEquals(
            PARALLEL.value(), score.totalScore()
        );
        Map<TransitionPenaltyType, Integer> penaltyCount 
            = score.getPenaltyCount();
        assertEquals(1, penaltyCount.size());
        assertEquals((Integer) 1, penaltyCount.get(PARALLEL));        
    }

    @Test
    public void testUpdate(){
        TransitionScore score = new TransitionScore();
    		
        TransitionScore update1 = new TransitionScore();
        update1.addPenalty(PARALLEL);
        update1.addPenalty(DIRECT);
        update1.addPenalty(DOM_SEVEN_RES);
        score.updatePenalty(update1);
        
        TransitionScore update2 = new TransitionScore();
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
        assertEquals((Integer) 1, penaltyCount.get(PARALLEL));
        assertEquals((Integer) 1, penaltyCount.get(DIRECT));
        assertEquals((Integer) 2, penaltyCount.get(DOM_SEVEN_RES));
        assertEquals((Integer) 1, penaltyCount.get(DIM_SEVEN_RES));
    }
}
