package scorer;

import java.util.List;
import java.util.Set;

import chord_data.ContextTag;
import chords.Chord;
import music.Key;
import music.Note;
import score_data.ScoreDeprecated;
import score_data.TransitionPenaltyType;

class VoiceCrossing {
    // TODO test
    
    /**
     * Scores for voice crossing by mutating an input score
     * @param previous previous chord 
     * @param current current chord
     * @param contextTagsPrevious context tags associated with Previous
     * @param contextTagsCurrent context tags associated with current
     * @param key key in which this transition should be analyzed
     * @param score a Score that gets mutated
     */
    static void scoreVoiceCrossing(Chord previous, Chord current, 
            Set<ContextTag> contextTagsPrevious, Set<ContextTag> contextTagsCurrent,
            Key key, ScoreDeprecated score){
        List<Note> previousSpelled = Scorer.spellChord(previous);
        List<Note> currentSpelled = Scorer.spellChord(current);
        for (int i=0; i<3; i++){
            if (previousSpelled.get(i).getNoteID() >= currentSpelled.get(i+1).getNoteID()){
                score.addPenalty(TransitionPenaltyType.VOICE_CROSSING);
            }
            if (currentSpelled.get(i).getNoteID() >= previousSpelled.get(i+1).getNoteID()){
                score.addPenalty(TransitionPenaltyType.VOICE_CROSSING);
            }
        }
    }

}
