package scorer_chords;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import chord_data.ChordWithContext;
import score_data.ChordPenaltyType;

/**
 * Interface for scorers that take score a single chord.  
 */
public interface ChordScorer {
    
    /**
     * Scores a chord in context
     * @param chordAndContext chord and context
     * @return a Map<ChordPenaltyType, Integer> mapping a each ChordPenalty to the 
     * number of violations that occurred.  
     */
    public Map<ChordPenaltyType, Integer> scoreChord(ChordWithContext chordAndContext);
    
    /**
     * @return a list of all chord scorers
     */
    public static List<ChordScorer> allChordScorers(){
        return Arrays.asList(
            new DoublingCadenceScorer(),
            new DoublingScorer(),
            new PacScorer(),
            new VoiceOverlapScorer()
        );
    }
    
}
