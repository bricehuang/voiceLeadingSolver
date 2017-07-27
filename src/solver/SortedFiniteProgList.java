package solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a list of chords, sorted by score
 **/
class SortedFiniteProgList {
    
    public static final int PROGRESSIONS_TO_TRACK = 3;
    private final List<ChordProgWithScoreDeprecated> content;

    /*
     * Abstraction Function: 
     * content represents the best progressions (and scores) that have 
     * been inputted so far, in increasing order of badness score
     * 
     * Rep invariant:
     * has at most PROGRESSIONS_TO_TRACK progressions
     * progressions in content.get(chord) increase in score
     * 
     * 
     * Rep exposure:
     * Returns primitives and unmodifiable views of lists only
     */
    
    /**
     * Constructs a SortedFiniteProgList
     */
    public SortedFiniteProgList(){
        content = new ArrayList<>();
        checkRep();
    }
    
    private void checkRep(){
        assert(content.size() <= PROGRESSIONS_TO_TRACK);
        for (int i=1; i<content.size(); i++){
            assert(content.get(i-1).getScore() <= content.get(i).getScore());
        }
    }
    
    /**
     * Return size of this list
     * @return ditto
     */
    public int size(){
        return content.size();
    }
    
    /**
     * Returns the contents of this list
     * @return an unmodifiable view of the contents of this list
     */
    public List<ChordProgWithScoreDeprecated> getProgressions(){
        List<ChordProgWithScoreDeprecated> copyContent = new ArrayList<>();
        for (ChordProgWithScoreDeprecated prog : content){
            copyContent.add(prog);
        }
        return Collections.unmodifiableList(copyContent);
    }
    
    /**
     * Mutator.  Inserts a progression if its score is better than 
     * the PROGRESSIONS_TO_TRACKth progression ending with the same 
     * chord
     * @param prog a ChordProgressionWithScore
     */
    public void addProgression(ChordProgWithScoreDeprecated progWithScore){
        Integer score = progWithScore.getScore();
        
        int listSize = content.size();
        if (listSize == 0){
            content.add(progWithScore);
        }
        else if (content.get(listSize-1).getScore() <= score){
            content.add(progWithScore);
        }
        else{
            int position = 0;
            while (content.get(position).getScore() <= score){
                position++;
            }
            content.add(position, progWithScore);

        }
        if (content.size() > PROGRESSIONS_TO_TRACK){
            content.remove(content.size()-1);
        }
        checkRep();
    }
 

}
