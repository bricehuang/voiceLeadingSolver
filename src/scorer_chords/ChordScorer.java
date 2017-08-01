package scorer_chords;

import java.util.Arrays;
import java.util.List;

import chord_data.ChordWithContext;
import score_data.ChordScoreNew;

/**
 * Interface for scorers that take score a single chord.  
 */
public interface ChordScorer {
    
    public static final List<ChordScorer> ALL_CHORD_SCORERS = Arrays.asList(
        new DoublingCadenceScorer(),
        new DoublingScorer(),
        new PacScorer(),
        new VoiceOverlapScorer()
    );
    
    /**
     * Scores a chord in context
     * @param chordAndContext chord and context
     * @return a Map<ChordPenaltyType, Integer> mapping a each ChordPenalty to the 
     * number of violations that occurred.  
     */
    public ChordScoreNew scoreChord(ChordWithContext chordAndContext);
    
    /**
     * @return a ChordScore of all penalties associated with this chord
     */
    public static ChordScoreNew score(ChordWithContext chordAndContext) {
        ChordScoreNew score = new ChordScoreNew();
        for (ChordScorer chordScorer: ALL_CHORD_SCORERS) {
            score.updatePenalty(chordScorer.scoreChord(chordAndContext));
        }
        return score;
    }
    
}
