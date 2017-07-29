package scorer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import score_data.ChordPenaltyType;
import score_data.ScoreDeprecated;
import score_data.TransitionPenaltyType;

public class ScoreTestDeprecated {

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
        
    }

    @Test
    public void testBasic(){
        ScoreDeprecated score = new ScoreDeprecated();
        assertEquals(0, score.totalScore());
        assertEquals("Total Penalty: 0\n", score.toString());
    }

    @Test
    public void testAdd(){
        ScoreDeprecated score = new ScoreDeprecated();
        score.addPenalty(TransitionPenaltyType.PARALLEL);
        
        assertEquals(1000000, score.totalScore());
        assertEquals("Parallel Interval Penalty: 1.  Score: 1000000\nTotal Penalty: 1000000\n", score.toString());
    }

    @Test
    public void testRemove(){
        ScoreDeprecated score = new ScoreDeprecated();
        score.addPenalty(TransitionPenaltyType.PARALLEL);
        score.addPenalty(ChordPenaltyType.DOUBLE_DOUBLING);
        score.addPenalty(ChordPenaltyType.BAD_TRIPLING);
        score.addPenalty(ChordPenaltyType.BAD_TRIPLING);
        score.removePenalty(TransitionPenaltyType.PARALLEL);
        
        assertEquals(400, score.totalScore());
        Set<String> validAnswers = new HashSet<>(
            Arrays.asList(
                    "Double Doubling Penalty: 1.  Score: 200\n"+
                            "Bad Tripling Penalty: 2.  Score: 200\n"+
                            "Total Penalty: 400\n",
                    "Bad Tripling Penalty: 2.  Score: 200\n"+
                            "Double Doubling Penalty: 1.  Score: 200\n"+
                            "Total Penalty: 400\n"
                    ));
        assertTrue(validAnswers.contains(score.toString()));
    }
}
