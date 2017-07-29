package scorer;

import java.util.List;
import java.util.Set;

import chord_data.ContextTag;
import chords.Chord;
import music.Key;
import music.Note;
import score_data.ChordPenaltyType;
import score_data.Score;

/**
 * A module that scores voice overlapping
 */
class VoiceOverlap {
    
    /**
     * Scores for voice overlapping by mutating an input score 
     * @param chord current chord
     * @param contextTags any relevant context tags
     * @param key key in which this transition should be analyzed
     * @param score a Score that gets mutated
     */
    static void scoreVoiceOverlap(Chord chord, Set<ContextTag> contextTags, 
            Key key, Score score){
        List<Note> chordSpelled = Scorer.spellChord(chord);
        for (int i=0; i<3; i++){
            if (chordSpelled.get(i).equals(chordSpelled.get(i+1))){
                score.addPenalty(ChordPenaltyType.VOICE_OVERLAP);
            }
        }
        return;
    }
}
