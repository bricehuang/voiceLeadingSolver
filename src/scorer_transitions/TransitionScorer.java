package scorer_transitions;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import chord_data.ChordWithContext;
import score_data.TransitionPenaltyType;

/**
 * Interface for scorers that take score a transition between two chords.  
 */
public interface TransitionScorer {
    
    /**
     * Scores a transition in context
     * @param chordAndContext chord and context
     * @return a Map<ChordPenaltyType, Integer> mapping a each ChordPenalty to the 
     * number of violations that occurred.  
     */
    public Map<TransitionPenaltyType, Integer> scoreTransition(
            ChordWithContext previous, ChordWithContext current
    );
    
    /**
     * @return a list of all chord scorers
     */
    public static List<TransitionScorer> allTransitionScorers(){
        return Arrays.asList(
            new CadenceMovementScorer(),
            new Dim7ResolutionScorer(),
            new DirectsScorer(),
            new Dom7ResolutionScorer(),
            new II7SuspensionScorer(),
            new MelodicIntervalScorer(),
            new MovementScorer(),
            new NeapolitanResolutionScorer(),
            new ParallelsScorer(),
            new VoiceCrossingScorer()
        );
    }
    
}
