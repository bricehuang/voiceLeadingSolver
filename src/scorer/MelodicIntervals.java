package scorer;

import chords.Chord;
import music.Key;

/**
 * A module that scores for good melodic intervals
 */
class MelodicIntervals {
    // TODO make Interval have major, minor, perfect, aug, dim type?
    /**
     * Scores for bad melodic intervals
     * @param previous previous chord 
     * @param current current chord
     * @param key key in which this transition should be analyzed
     * @return score
     */
    static Integer scoreMelodicIntervals(Chord previous, Chord current, Key key){
        throw new RuntimeException("Unimplemented.");
    }

}
