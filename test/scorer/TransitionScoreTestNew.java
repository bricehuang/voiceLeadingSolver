package scorer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
    }

    @Test
    public void testUpdate(){
        TransitionScoreNew score = new TransitionScoreNew();
        Map<TransitionPenaltyType, Integer> update = new HashMap<>();
        update.put(PARALLEL, 1);
        score.updatePenalty(update);
        
        assertEquals(PARALLEL.value(), score.totalScore());
        assertEquals(
            "Parallel Interval Penalty: 1.  Score: 1000000\n"
            + "Total Penalty: 1000000\n", 
            score.toString()
        );
    }

    @Test
    public void testUpdateWithRemove(){
        TransitionScoreNew score = new TransitionScoreNew();
            
        Map<TransitionPenaltyType, Integer> update1 = new HashMap<>();
        update1.put(PARALLEL, 1);
        update1.put(DIM_SEVEN_RES, 1);
        update1.put(DOM_SEVEN_RES, 1);
        score.updatePenalty(update1);
        
        Map<TransitionPenaltyType, Integer> update2 = new HashMap<>();        
        update2.put(DOM_SEVEN_RES, 1);
        update2.put(PARALLEL, -1);
        score.updatePenalty(update2);        
        
        assertEquals(
            DIM_SEVEN_RES.value()+DOM_SEVEN_RES.value()*2, 
            score.totalScore()
        );
        String penalty1 = "Bad Dominant Seven Resolution Penalty: 2.  Score: 2000\n";
        String penalty2 = "Bad Diminished Seven Resolution Penalty: 1.  Score: 1000\n";
        String penaltyTotal = "Total Penalty: 3000\n";
        Set<String> validAnswers = new HashSet<>(
            Arrays.asList(
                penalty1+penalty2+penaltyTotal,
                penalty2+penalty1+penaltyTotal
            )
        );
        assertTrue(validAnswers.contains(score.toString()));
    }
}
