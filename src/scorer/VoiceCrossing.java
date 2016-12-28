package scorer;

import java.util.List;
import java.util.Set;

import chords.Chord;
import music.Interval;
import music.Key;
import music.Note;
import solver.ContextTag;

class VoiceCrossing {
    // TODO test
    
    /**
     * Scores for voice crossing by mutating an input score
     * @param previous previous chord 
     * @param current current chord
     * @param key key in which this transition should be analyzed
     * @param contextTags any relevant context tags
     * @param score a Score that gets mutated
     */
    static void scoreVoiceCrossing(Chord previous, Chord current, Key key,
            Set<ContextTag> contextTag, Score score){
        List<Note> previousSpelled = Scorer.spellChord(previous);
        List<Note> currentSpelled = Scorer.spellChord(current);
        for (int i=0; i<3; i++){
            if (previousSpelled.get(i).getNoteID() >= currentSpelled.get(i+1).getNoteID()){
                score.addPenalty(PenaltyType.VOICE_CROSSING);
            }
            if (currentSpelled.get(i).getNoteID() >= previousSpelled.get(i+1).getNoteID()){
                score.addPenalty(PenaltyType.VOICE_CROSSING);
            }
        }
    }

}
