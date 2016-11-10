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
        BestList best = new BestList();
        for (Chord chord : chordSet){
            ChordProgression prog = ChordProgression.empty().append(chord);
            Integer score = Scorer.scoreChord(chord, key);
            best.addProgression(new ChordProgWithScore(prog, score));
        }
        return best;
    }
    
    /**
     * Sequences the second through last chords
     * @param previousBest BestList of ways to sing the chords up to, but 
     * excluding, this one, with scores
     * @param chordSet ways to sing this chord
     * @param key key in which to analyze this transition
     * @return a BestList of ways to sing the chords up to, and including,
     * this one, with scores
     */
    private static BestList sequenceRest(BestList previousBest, 
            Set<Chord> chordSet, Key key, boolean isLast){
        BestList best = new BestList();
        for (Chord currentChord : chordSet){
            Integer currentChordScore = Scorer.scoreChord(currentChord, key);
            
            for (Chord previousChord : previousBest.getEndingChords()){
                int transitionScore = Scorer.scoreTransition(previousChord, currentChord, key);
                if (isLast){
                    transitionScore += Scorer.scoreTransition(previousChord, currentChord, key); 
                }
                
                for (ChordProgWithScore previous : best.getProgressions(previousChord)){
                    int totalScore = previous.getScore() + currentChordScore + transitionScore;
                    
                    ChordProgression previousProg = previous.getChordProg();
                    ChordProgression concatenated = previousProg.append(currentChord);
                    ChordProgWithScore concatenatedWithScore = 
                            new ChordProgWithScore(concatenated, totalScore);
                    
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
    private static SortedFiniteProgList findBestProgs(BestList bestList){
        SortedFiniteProgList bestProgressions = new SortedFiniteProgList();
        for (Chord lastChord : bestList.getEndingChords()){
            for (ChordProgWithScore progression : bestList.getProgressions(lastChord)){
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
    public static SortedFiniteProgList findBestChordProgressions(
            List<Set<Chord>> waysToSingChords, List<Key> keys){
        assert(waysToSingChords.size() == keys.size());
        assert(waysToSingChords.size() > 0);
        
        BestList currentBest = sequenceFirst(waysToSingChords.get(0), keys.get(0));
        for (int i=1; i<waysToSingChords.size(); i++){
            currentBest = sequenceRest(currentBest, waysToSingChords.get(i), 
                    keys.get(i), (i== waysToSingChords.size()-1));
        }
        return findBestProgs(currentBest);
    }
}


