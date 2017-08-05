package scorer_transitions;

import java.util.List;

import chord_data.ChordWithContext;
import music.Note;
import score_data.TransitionPenaltyType;
import score_data.TransitionScoreNew;
import solver.SolverUtils;

public class VoiceCrossingScorer implements TransitionScorer {
    
    private boolean isHigherOrEqualTo(Note note1, Note note2) {
        if (note1.getNoteID() > note2.getNoteID()) {
            return true;
        } else if (note1.getNoteID() < note2.getNoteID()) {
            return false;
        } else {
            return (note1.getPitchID() >= note2.getPitchID());
        }
    }

    @Override
    public TransitionScoreNew scoreTransition(ChordWithContext previous, ChordWithContext current) {
        TransitionScoreNew score = new TransitionScoreNew();
        List<Note> previousNotes = SolverUtils.spellNotes(previous.getChord());
        List<Note> currentNotes = SolverUtils.spellNotes(current.getChord());
        for (int i=0; i<3; i++){
            if (isHigherOrEqualTo(previousNotes.get(i+1), currentNotes.get(i))){
                score.addPenalty(TransitionPenaltyType.VOICE_CROSSING);
            }
            if (isHigherOrEqualTo(currentNotes.get(i+1), previousNotes.get(i))){
                score.addPenalty(TransitionPenaltyType.VOICE_CROSSING);
            }            
        }
        return score;
    }

}
