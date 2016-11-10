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
     * Sequences the first chord
     * @param chordSet ways to sing the first chord
     * @param key key in which to analyze the first chord
     * @return a BestList of ways to sing the first chord, with scores
     */
    private static BestList sequenceFirst(Set<Chord> chordSet, Key key){
        throw new RuntimeException("Unimplemented");
    }
    
    /**
     * Sequences the second through N-1th chords
     * @param previousBest BestList of ways to sing the chords up to, but 
     * excluding, this one, with scores
     * @param chordSet ways to sing this chord
     * @param key key in which to analyze this transition
     * @return a BestList of ways to sing the chords up to, and including,
     * this one, with scores
     */
    private static BestList sequenceMiddle(BestList previousBest, 
            Set<Chord> chordSet, Key key){
        throw new RuntimeException("Unimplemented");
    }
    
    /**
     * Sequences the last chord
     * @param previousBest BestList of ways to sing the chords up to, but 
     * excluding, this one, with scores
     * @param chordSet ways to sing this chord
     * @param key key in which to analyze this transition
     * @return a BestList of ways to sing all the chords
     */
    private static BestList sequenceLast(BestList previousBest, 
            Set<Chord> chordSet, Key key){
        throw new RuntimeException("Unimplemented");
    }

    /**
     * Finds the best chords from a BestList
     * @param bestList a BestList
     * @return a SortedFiniteProgList of the best chords from bestList
     */
    private static SortedFiniteProgList findBestProgs(BestList bestList){
        throw new RuntimeException("Unimplemented")
    }
    
    /**
     * Computes the best chord progression
     * @param waysToSingChords a list of ways to sing chords. The ith item
     * is a set of all the ways to sing the ith chord.    
     * @param keys a list of keys, such that the ith item is the key
     * in which the ith chord should be considered.  We say that 
     * pivot chords are considered in the old key.  
     * @return The best way to sing this chord progression
     */
    public static ChordProgression findBestChordProgression(
            List<Set<Chord>> waysToSingChords, List<Key> keys){
        throw new RuntimeException("Unimplemented");
    }
}


