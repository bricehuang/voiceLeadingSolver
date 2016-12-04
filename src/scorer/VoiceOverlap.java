package scorer;

import java.util.List;

import chords.Chord;
import music.Key;
import music.Note;

/**
 * A module that scores voice overlapping
 */
class VoiceOverlap {
    
    private static final int VOICE_OVERLAP_PENALTY = 25;
    
    /**
     * Scores the badness of voice overlapping
     * @param chord current chord
     * @param key key in which this chord should be analyzed
     * @return score
     */
    static Integer scoreVoiceOverlap(Chord chord, Key key, boolean debug){
        int score = 0;
        List<Note> chordSpelled = Scorer.spellChord(chord);
        for (int i=0; i<3; i++){
            if (chordSpelled.get(i).equals(chordSpelled.get(i+1))){
                score += VOICE_OVERLAP_PENALTY;
                if (debug){
                    System.err.println("Voice Overlap Penalty: "+VOICE_OVERLAP_PENALTY); 
                }
            }
        }
        return score;
    }
}
