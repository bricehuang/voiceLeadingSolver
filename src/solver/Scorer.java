package solver;

import chords.Chord;
import music.Key;

/**
 * A module that scores individual chords and transitions between chords  
 */
public class Scorer {
    
    /**
     * Scores the badness of a transition 
     * @param previous previous chord 
     * @param current current chord
     * @param key key in which this transition should be analyzed
     * @return a score representing this transition's badness
     */
    public static Integer scoreTransition(Chord previous, Chord current, Key key){
        throw new RuntimeException("Unimplemented.");
    }
}
