package solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import chord_data.ContextTag;
import chords.Chord;
import chords.ChordProgressionDeprecated;
import chords.PrimitiveChord;
import music.Key;

/**
 * A module that solves voice leading, post-parsing
 */
class SolverDeprecated {
    
    static List<ChordProgressionDeprecated> solve(List<PrimitiveChord> primitiveChords, 
            List<Key> keys, List<Set<ContextTag>> contextTagsList){
        List<Set<Chord>> waysToSingChords = ChordGenerator.generateChordsDeprecated(primitiveChords);
        SortedFiniteProgListDeprecated bestProgressionsAndScores = SequencerDeprecated.findBestChordProgressions(waysToSingChords, keys, contextTagsList);
        List<ChordProgressionDeprecated> bestProgressions = new ArrayList<>();
        for (ChordProgWithScoreDeprecated progressionAndScore : bestProgressionsAndScores.getProgressions()){
            bestProgressions.add(progressionAndScore.getChordProg());
        }
        return Collections.unmodifiableList(bestProgressions);
    }
}
