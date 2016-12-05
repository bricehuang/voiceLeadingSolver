package solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import chords.Chord;
import chords.ChordProgression;

/**
 * Mutable class; represents the best chord progressions 
 * ending with a particular chord
 */
class BestList{
    
    private final Map<Chord, SortedFiniteProgList> content;
    
    /*
     * Abstraction Function:
     * content.get(chord) represents the best progressions (and scores) 
     * ending in chord that have been inputted so far, in increasing 
     * order of badness score.  
     * 
     * Rep invariant:
     * content.get(chord) has at least 1 progression (Enforced by SortedFiniteProgList 
     * at most SortedFiniteProgList.PROGRESSIONS_TO_TRACK progressions)
     * all progressions in content.get(chord) end with chord
     * (Enforced by SortedFiniteList: progressions in content.get(chord) 
     * decrease in score)
     * 
     * Rep exposure:
     * Returns immutables and unmodifiable views of lists only
     */
    
    /**
     * Constructs a BestList
     */
    public BestList(){
        content = new HashMap<>();
        checkRep();
    }
    
    private void checkRep(){
        for (Chord chord : content.keySet()){
            assert(content.get(chord).size() > 0);
            List<ChordProgWithScore> progs = content.get(chord).getProgressions();
            for (ChordProgWithScore prog : progs){
                assert(prog.getChordProg().getLast().equals(chord));
            }
        }
    }
    
    
    /**
     * Returns the set of ending chords
     * @return ditto
     */
    public Set<Chord> getEndingChords(){
        Set<Chord> endingChords = new HashSet<>();
        for (Chord chord : content.keySet()){
            endingChords.add(chord);
        }
        return Collections.unmodifiableSet(endingChords);
    }
    
    /**
     * Returns the best chord progressions ending with a particular chord
     * @param chord a chord
     * @return an unmodifiable view of a list of the best chord 
     * progressions ending with this chord, in increasing order of 
     * badness score 
     */
    public List<ChordProgWithScore> getProgressions(Chord chord){
        if (!content.keySet().contains(chord)){
            return new ArrayList<>();
        }
        else{
            return content.get(chord).getProgressions();
        }
    }
    
    /**
     * Mutator.  Inserts a progression if its score is better than 
     * the SortedFiniteProgList.PROGRESSIONS_TO_TRACKth progression 
     * ending with the same chord
     * @param prog a ChordProgressionWithScore
     */
    public void addProgression(ChordProgWithScore progWithScore){
        ChordProgression prog = progWithScore.getChordProg();
        Chord last = prog.getLast();
        
        if (!content.keySet().contains(last)){
            content.put(last, new SortedFiniteProgList());
        }
        content.get(last).addProgression(progWithScore);
        checkRep();
    }
    
    
}