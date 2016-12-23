package scorer;

import java.util.List;
import java.util.Set;

import chords.Chord;
import music.Key;
import music.Note;
import solver.ContextTag;

/**
 * A module that scores voice overlapping
 */
class VoiceOverlap {
    // TODO THIS IS BROKEN
    
    private static final int VOICE_OVERLAP_PENALTY = 25;
    
    /**
     * Scores for voice overlapping by mutating an input score 
     * @param chord current chord
     * @param key key in which this transition should be analyzed
     * @param contextTags any relevant context tags
     * @param score a Score that gets mutated
     */
    static void scoreVoiceOverlap(Chord chord, Key key, 
            Set<ContextTag> contextTags, Score score){
        int tmpScore = 0;
        List<Note> chordSpelled = Scorer.spellChord(chord);
        for (int i=0; i<3; i++){
            if (chordSpelled.get(i).equals(chordSpelled.get(i+1))){
                tmpScore += VOICE_OVERLAP_PENALTY;
            }
        }
        return;
    }
}
