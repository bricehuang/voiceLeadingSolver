package scorer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import score_data.ChordPenaltyType;
import score_data.PenaltyType;
import score_data.ScoreNew;
import score_data.TransitionPenaltyType;

public class ScoreTestNew {

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
        
    }

    @Test
    public void testBasic(){
        ScoreNew score = new ScoreNew();
        assertEquals(0, score.totalScore());
        assertEquals("Total Penalty: 0\n", score.toString());
    }

    @Test
    public void testUpdate(){
        ScoreNew score = new ScoreNew();
        Map<PenaltyType, Integer> update = new HashMap<>();
        update.put(TransitionPenaltyType.PARALLEL, 1);
        score.updatePenalty(update);
        
        assertEquals(TransitionPenaltyType.PARALLEL.value(), score.totalScore());
        assertEquals(
        		"Parallel Interval Penalty: 1.  Score: 1000000\n"
        		+ "Total Penalty: 1000000\n", 
        		score.toString()
        	);
    }

    @Test
    public void testUpdateWithRemove(){
    		ScoreNew score = new ScoreNew();
    		
        Map<PenaltyType, Integer> update1 = new HashMap<>();
        update1.put(TransitionPenaltyType.PARALLEL, 1);
        update1.put(ChordPenaltyType.DOUBLE_DOUBLING, 1);
        update1.put(ChordPenaltyType.BAD_TRIPLING, 1);
        score.updatePenalty(update1);
        
        Map<PenaltyType, Integer> update2 = new HashMap<>();        
        update2.put(ChordPenaltyType.BAD_TRIPLING, 1);
        update2.put(TransitionPenaltyType.PARALLEL, -1);
        score.updatePenalty(update2);        
        
        assertEquals(400, score.totalScore());
        Set<String> validAnswers = new HashSet<>(
            Arrays.asList(
                "Double Doubling Penalty: 1.  Score: 200\n"+
                "Bad Tripling Penalty: 2.  Score: 200\n"+
                "Total Penalty: 400\n",
                "Bad Tripling Penalty: 2.  Score: 200\n"+
                "Double Doubling Penalty: 1.  Score: 200\n"+
                "Total Penalty: 400\n"
            )
        );
        assertTrue(validAnswers.contains(score.toString()));
    }
}
