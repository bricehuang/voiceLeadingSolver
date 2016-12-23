package scorer;

import java.util.Set;

import chords.Chord;
import music.Key;
import solver.ContextTag;

class VoiceCrossing {

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
        throw new RuntimeException("Unimplemented.");
    }


}
