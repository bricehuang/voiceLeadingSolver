package scorer;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class ScoreTest {

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
        
    }

    @Test
    public void testBasic(){
        Score score = new Score();
        assertEquals(0, score.totalScore());
        assertEquals("Total Penalty: 0\n", score.toString());
    }

    @Test
    public void testAdd(){
        Score score = new Score();
        score.addPenalty(PenaltyType.PARALLEL);
        
        assertEquals(1000000, score.totalScore());
        assertEquals("Parallel Interval Penalty: 1.  Score: 1000000\nTotal Penalty: 1000000\n", score.toString());
    }

    @Test
    public void testRemove(){
        Score score = new Score();
        score.addPenalty(PenaltyType.PARALLEL);
        score.addPenalty(PenaltyType.DOUBLE_DOUBLING);
        score.addPenalty(PenaltyType.BAD_TRIPLING);
        score.addPenalty(PenaltyType.BAD_TRIPLING);
        score.removePenalty(PenaltyType.PARALLEL);
        
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
