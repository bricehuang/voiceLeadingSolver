package scorer;

import java.util.Set;

import chords.Chord;
import music.Key;
import solver.ContextTag;

/**
 * A module that scores for small movements
 */
class SmallMovement {
    
    /**
     * Scores for small movements by mutating an input score
     * @param previous previous chord 
     * @param current current chord
     * @param key key in which this transition should be analyzed
     * @param contextTags any relevant context tags
     * @param score a Score that gets mutated
     */
    static void scoreSmallMovement(Chord previous, Chord current, Key key, 
            Set<ContextTag> contextTags, Score score){
        throw new RuntimeException("Unimplemented.");
    }
}
