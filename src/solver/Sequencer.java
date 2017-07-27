package solver;

import java.util.List;
import java.util.Set;

import chord_data.ContextTag;
import chords.Chord;
import chords.ChordProgressionDeprecated;
import music.Key;
import scorer.Score;
import scorer.Scorer;

/**
 * A module that finds the best way to sing a chord progression given 
 * a list of all the ways to sing each chord.  
 */
class Sequencer {
    
    /**
     * Sequences the first chord
     * @param chordSet ways to sing the first chord
     * @param scorer scorer in the desired key
     * @return a BestList of ways to sing the first chord, with scores
     */
    private static BestList sequenceFirst(Set<Chord> chordSet, Key key, Set<ContextTag> contextTags){
        BestList best = new BestList();
        for (Chord chord : chordSet){
            ChordProgressionDeprecated prog = ChordProgressionDeprecated.empty().append(chord);
            
            Score chordScore = Scorer.scoreChord(chord, contextTags, key);
            best.addProgression(new ChordProgWithScoreDeprecated(prog, chordScore.totalScore()));
        }
        return best;
    }
    
    /**
     * Sequences the second through last chords
     * @param previousBest BestList of ways to sing the chords up to, but 
     * excluding, this one, with scores
     * @param chordSet ways to sing this chord
     * @param scorer scorer in the desired key
     * @return a BestList of ways to sing the chords up to, and including,
     * this one, with scores
     */
    private static BestList sequenceRest(BestList previousBest, Set<Chord> chordSet, 
            Set<ContextTag> contextTagsPrevious, Set<ContextTag> contextTagsCurrent,
            Key key){
        BestList best = new BestList();
        for (Chord currentChord : chordSet){
            Score currentChordScore = Scorer.scoreChord(currentChord, contextTagsCurrent, key);
            
            for (Chord previousChord : previousBest.getEndingChords()){
                Score transitionScore = Scorer.scoreTransition(previousChord, currentChord, 
                        contextTagsPrevious, contextTagsCurrent, key);
                
                for (ChordProgWithScoreDeprecated previous : previousBest.getProgressions(previousChord)){
                    int totalScore = previous.getScore() + currentChordScore.totalScore() + transitionScore.totalScore();
                    
                    ChordProgressionDeprecated previousProg = previous.getChordProg();
                    ChordProgressionDeprecated concatenated = previousProg.append(currentChord);
                    ChordProgWithScoreDeprecated concatenatedWithScore = 
                            new ChordProgWithScoreDeprecated(concatenated, totalScore);
                    
                    best.addProgression(concatenatedWithScore);
                }
            }
        }
        return best;
    }
    
    /**
     * Finds the best chords from a BestList
     * @param bestList a BestList
     * @return a SortedFiniteProgList of the best chords from bestList
     */
    private static SortedFiniteProgListDeprecated findBestProgs(BestList bestList){
        SortedFiniteProgListDeprecated bestProgressions = new SortedFiniteProgListDeprecated();
        for (Chord lastChord : bestList.getEndingChords()){
            for (ChordProgWithScoreDeprecated progression : bestList.getProgressions(lastChord)){
                bestProgressions.addProgression(progression);
            }
        }
        return bestProgressions;
    }
    
    /**
     * Computes the best chord progressions
     * @param waysToSingChords a list of ways to sing chords. The ith item
     * is a set of all the ways to sing the ith chord.    
     * @param keys a list of keys, such that the ith item is the key
     * in which the ith chord should be considered.  We say that 
     * pivot chords are considered in the old key.  
     * @return The best way to sing this chord progression
     */
    public static SortedFiniteProgListDeprecated findBestChordProgressions(
            List<Set<Chord>> waysToSingChords, List<Key> keys, List<Set<ContextTag>> contextTagsList){
        assert(waysToSingChords.size() == keys.size());
        assert(waysToSingChords.size() == contextTagsList.size());
        assert(waysToSingChords.size() > 0);
        
        BestList currentBest = sequenceFirst(waysToSingChords.get(0), keys.get(0), contextTagsList.get(0));

        for (int i=1; i<waysToSingChords.size(); i++){
            currentBest = sequenceRest(currentBest, waysToSingChords.get(i), 
                    contextTagsList.get(i-1), contextTagsList.get(i), keys.get(i));
        }

        return findBestProgs(currentBest);
    }
}


