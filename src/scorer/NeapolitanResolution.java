package scorer;

import java.util.Set;

import chord_data.ContextTag;
import chords.Chord;
import music.Key;

class NeapolitanResolution {
    
    /**
     * Scores for Neapolitan resolutions by mutating an input score
     * @param previous previous chord 
     * @param current current chord
     * @param contextTagsPrevious context tags associated with Previous
     * @param contextTagsCurrent context tags associated with current
     * @param key key in which this transition should be analyzed
     * @param score a Score that gets mutated
     */
    static void scoreNeapolitanResolutions(Chord previous, Chord current,
            Set<ContextTag> contextTagsPrevious, Set<ContextTag> contextTagsCurrent,
            Key key, Score score){
        // TODO
    }
}
