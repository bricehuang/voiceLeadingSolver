package solver;

import java.util.List;
import java.util.Set;

import chords.Chord;
import chords.PrimitiveChord;
import music.Key;

/**
 * A module that solves voice leading, post-parsing
 */
class Solver {
    
    static List<ChordProgWithScore> solve(List<PrimitiveChord> primitiveChords, 
            List<Key> keys, List<Set<ContextTag>> contextTagsList){
        List<Set<Chord>> waysToSingChords = ChordGenerator.generateChords(primitiveChords);
        SortedFiniteProgList bestProgressions = Sequencer.findBestChordProgressions(waysToSingChords, keys, contextTagsList);
        return bestProgressions.getProgressions();
    }
}
