package scorer_transitions;

import chord_data.ChordWithContext;
import chord_data.ContextTag;
import chords.Chord;
import chords.ChordType;
import chords.PrimitiveChord;
import music.BasicInterval;
import music.Interval;
import music.Key;
import score_data.TransitionPenaltyType;
import score_data.TransitionScore;
import solver.SolverUtils;

public class II7SuspensionScorer implements TransitionScorer {
    
    private static final ChordType MAJ = ChordType.MAJ;
    private static final ChordType MIN = ChordType.MIN;
    private static final ChordType MIN7 = ChordType.MIN7;
            
    private static final BasicInterval P1 = new BasicInterval(0, 0);
    
    private static final Interval UNIS = new Interval(P1, 0, true);

    @Override
    public TransitionScore scoreTransition(ChordWithContext previous, ChordWithContext current) {
        TransitionScore score = new TransitionScore();
        
        // Return early if this doesn't apply
        if (! previous.getContextTags().contains(ContextTag.CADENTIAL_PREDOMINANT) || 
            ! current.getContextTags().contains(ContextTag.CADENTIAL_I64)) {
            return score;
        } 

        Chord previousChord = previous.getChord();
        Chord currentChord = current.getChord();
        if (previousChord.getType() != MIN7) {
            return score;
        }
        
        // Sanity checks
        assert previous.getKey().equals(current.getKey());
        Key key = current.getKey();
        assert (
            previousChord.getPrimitiveChord().getRoot()
            .equals(key.getScaleDegree(2))
        );
        ChordType expectedCurrentType = (key.getIsMajor()) ? MAJ : MIN;
        PrimitiveChord expectedCurrentPrimitiveChord = new PrimitiveChord(
            key.getScaleDegree(1), expectedCurrentType, 2
        );
        assert (
            currentChord.getPrimitiveChord()
            .equals(expectedCurrentPrimitiveChord)
        );
        
        // Penalty logic
        Interval tonicMovement = SolverUtils.computeVoiceMovementsByPosition(
            previousChord, currentChord
        ).get(3); // 3 because 7th of ii7 chord is tonic
        if (! tonicMovement.equals(UNIS)) {
            score.addPenalty(TransitionPenaltyType.CADENTIAL_II7_SUSPEND);
        }
        return score;
    }

}
