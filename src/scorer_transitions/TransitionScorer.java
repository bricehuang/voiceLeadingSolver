package scorer_transitions;

import java.util.Arrays;
import java.util.List;

import chord_data.ChordWithContext;
import score_data.TransitionScoreNew;

/**
 * Interface for scorers that take score a transition between two chords.  
 */
public interface TransitionScorer {
    
    public static final List<TransitionScorer> ALL_TRANSITION_SCORERS = Arrays.asList(
        new CadenceMovementScorer(),
        new Dim7ResolutionScorer(),
        new DirectsScorer(),
        new Dom7ResolutionScorer(),
        new II7SuspensionScorer(),
        new MelodicIntervalScorer(),
        new MovementScorer(),
//        new NeapolitanResolutionScorer(),
        new ParallelsScorer(),
        new VoiceCrossingScorer()
    );

    
    /**
     * Scores a transition in context
     * @param chordAndContext chord and context
     * @return a Map<ChordPenaltyType, Integer> mapping a each ChordPenalty to the 
     * number of violations that occurred.  
     */
    public TransitionScoreNew scoreTransition(
        ChordWithContext previous, ChordWithContext current
    );
    
    /**
     * @return a list of all chord scorers
     */
    public static TransitionScoreNew score(ChordWithContext previous, ChordWithContext current){
        TransitionScoreNew score = new TransitionScoreNew();
        for (TransitionScorer transitionScorer: ALL_TRANSITION_SCORERS) {
            score.updatePenalty(transitionScorer.scoreTransition(previous, current));
        }
        return score;        
    }
    
}
