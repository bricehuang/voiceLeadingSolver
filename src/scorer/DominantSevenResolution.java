package scorer;

import java.util.Set;

import chords.Chord;
import music.Key;
import solver.ContextTag;

/**
 * A module for scoring dominant seven resolutions
 */
class DominantSevenResolution {
    
    /**
     * Scores for diminished 7th resolutions by mutating an input score
     * @param previous previous chord 
     * @param current current chord
     * @param key key in which this transition should be analyzed
     * @param contextTags any relevant context tags
     * @param score a Score that gets mutated
     */
    static void scoreDomSevenResolutions(Chord previous, Chord current, Key key,
            Set<ContextTag> contextTags, Score score){
        throw new RuntimeException("Unimplemented.");
    }

}
