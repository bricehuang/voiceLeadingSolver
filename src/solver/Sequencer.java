package solver;

import java.util.List;
import java.util.Set;

import chords.Chord;
import chords.ChordProgression;
import music.Key;

/**
 * A module that finds the best way to sing a chord progression given 
 * a list of all the ways to sing each chord.  
 */
public class Sequencer {
    
    /**
     * Computes the best chord progression
     * @param chordList a list of ways to sing chords. The ith item
     * is a set of all the ways to sing the ith chord.    
     * @param keys a list of keys, such that the ith item is the key
     * in which the ith chord should be considered.  We say that 
     * pivot chords are considered in the old key.  
     * @return The best way to sing this chord progression
     */
    public static ChordProgression findBestChordProgression(
            List<Set<Chord>> chordList, List<Key> keys){
        throw new RuntimeException("Unimplemented");
    }
}


