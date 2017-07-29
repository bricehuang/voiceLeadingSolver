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
import score_data.ChordScoreNew;

public class ChordScoreTestNew {

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
        
    }

    @Test
    public void testBasic(){
        ChordScoreNew score = new ChordScoreNew();
        assertEquals(0, score.totalScore());
        assertEquals("Total Penalty: 0\n", score.toString());
    }

    @Test
    public void testUpdate(){
        ChordScoreNew score = new ChordScoreNew();
        Map<ChordPenaltyType, Integer> update = new HashMap<>();
        update.put(ChordPenaltyType.DOUBLED_LEADING_TONE, 1);
        score.updatePenalty(update);
        
        assertEquals(ChordPenaltyType.DOUBLED_LEADING_TONE.value(), score.totalScore());
        assertEquals(
        		"Doubled Leading Tone Penalty: 1.  Score: 100000\n" + 
        		"Total Penalty: 100000\n", 
        		score.toString()
        	);
    }

    @Test
    public void testUpdateWithRemove(){
    		ChordScoreNew score = new ChordScoreNew();
    		
        Map<ChordPenaltyType, Integer> update1 = new HashMap<>();
        update1.put(ChordPenaltyType.DOUBLED_LEADING_TONE, 1);
        update1.put(ChordPenaltyType.DOUBLE_DOUBLING, 1);
        update1.put(ChordPenaltyType.BAD_TRIPLING, 1);
        score.updatePenalty(update1);
        
        Map<ChordPenaltyType, Integer> update2 = new HashMap<>();        
        update2.put(ChordPenaltyType.BAD_TRIPLING, 1);
        update2.put(ChordPenaltyType.DOUBLED_LEADING_TONE, -1);
        score.updatePenalty(update2);        
        
        assertEquals(
            ChordPenaltyType.DOUBLE_DOUBLING.value()
                +ChordPenaltyType.BAD_TRIPLING.value()*2, 
            score.totalScore()
        );
        String penalty1 = "Bad Tripling Penalty: 2.  Score: 200\n";
        String penalty2 = "Double Doubling Penalty: 1.  Score: 200\n";
        String penaltyTotal = "Total Penalty: 400\n";
        Set<String> validAnswers = new HashSet<>(
            Arrays.asList(
                penalty1+penalty2+penaltyTotal,
                penalty2+penalty1+penaltyTotal
            )
        );
        assertTrue(validAnswers.contains(score.toString()));
    }
}
