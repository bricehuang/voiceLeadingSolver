package solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import chord_data.ChordProgressionWithContext;
import chord_data.ChordWithContext;

/**
 * Mutable class; represents the best chord progressions 
 * ending with a particular chord
 */
class BestProgressionsByLast{
    
    private final Map<ChordWithContext, BestProgressionList> content;
    
    /*
     * Abstraction Function:
     * content.get(chord) represents the best progressions (and scores) 
     * ending in chord that have been inputted so far, in increasing 
     * order of badness score.  
     * 
     * Rep invariant:
     * content.get(chord) has at least 1 progression  
     * all progressions in content.get(chord) end with chord
     * (Enforced by BestProgressionList) content.get(chord has at most 
     *     BestProgressionList.PROGRESSIONS_TO_TRACK progressions 
     * (Enforced by BestProgressionList) progressions in content.get(chord) 
     * increase in score
     * 
     * Rep exposure:
     * Returns immutables and unmodifiable views of lists only
     */
    
    /**
     * Constructs a BestProgressionsByLast
     */
    public BestProgressionsByLast(){
        content = new HashMap<>();
        checkRep();
    }
    
    private void checkRep(){
        for (ChordWithContext chordAndContext : content.keySet()){
            assert(content.get(chordAndContext).size() > 0);
            List<ScoredProgression> progressions = 
            		content.get(chordAndContext).getProgressions();
            for (ScoredProgression progression : progressions){
                assert(progression.getChordProg().getLast().equals(chordAndContext));
            }
        }
    }
    
    
    /**
     * Returns the set of ending chords
     * @return ditto
     */
    public Set<ChordWithContext> getEndingChords(){
        Set<ChordWithContext> endingChords = new HashSet<>();
        for (ChordWithContext chordAndContext: content.keySet()){
            endingChords.add(chordAndContext);
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
    public List<ScoredProgression> getProgressions(ChordWithContext chordAndContext){
        if (!content.keySet().contains(chordAndContext)){
            return Collections.unmodifiableList(new ArrayList<>());
        }
        else{
            return content.get(chordAndContext).getProgressions();
        }
    }
    
    /**
     * Mutator.  Inserts a progression if its score is better than 
     * the BestProgressionList.PROGRESSIONS_TO_TRACKth progression 
     * ending with the same chord
     * @param prog a ScoredProgression
     */
    public void addProgression(ScoredProgression scoredProgression){
        ChordProgressionWithContext progression = scoredProgression.getChordProg();
        ChordWithContext last = progression.getLast();
        if (!content.keySet().contains(last)){
            content.put(last, new BestProgressionList());
        }
        content.get(last).addProgression(scoredProgression);
        checkRep();
    }
    
    
}