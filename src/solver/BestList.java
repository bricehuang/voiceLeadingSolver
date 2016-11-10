package solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chords.Chord;
import chords.ChordProgression;

/**
 * Mutable class; represents the best chord progressions 
 * ending with a particular chord
 */
public class BestList{
    
    public static final int PROGRESSIONS_TO_TRACK = 3;
    
    private final Map<Chord, List<ChordProgWithScore>> content;
    
    /*
     * Abstraction Function:
     * content.get(chord) represents the best progressions (and scores) 
     * ending in chord that have been inputted so far, in increasing 
     * order of badness score.  
     * 
     * Rep invariant:
     * content.get(chord) has between 1 and PROGRESSIONS_TO_TRACK progressions
     * all progressions in content.get(chord) end with chord
     * progressions in content.get(chord) decrease in score
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
            assert(content.get(chord).size() <= PROGRESSIONS_TO_TRACK);
            assert(content.get(chord).size() > 0);
            List<ChordProgWithScore> progs = content.get(chord);
            for (ChordProgWithScore prog : progs){
                assert(prog.getChordProg().getLast().equals(chord));
            }
            for (int i=1; i<progs.size(); i++){
                assert(progs.get(i-1).getScore() <= progs.get(i).getScore());
            }
        }
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
            return Collections.unmodifiableList(content.get(chord));
        }
    }
    
    /**
     * Mutator.  Inserts a progression if its score is better than 
     * the PROGRESSIONS_TO_TRACKth progression ending with the same 
     * chord
     * @param prog a ChordProgressionWithScore
     */
    public void addProgression(ChordProgWithScore progWithScore){
        ChordProgression prog = progWithScore.getChordProg();
        Chord last = prog.getLast();
        Integer score = progWithScore.getScore();
        
        if (content.keySet().contains(last)){
            List<ChordProgWithScore> progList = content.get(last);
            int listSize = progList.size();
            if (progList.get(listSize-1).getScore() <= score){
                progList.add(progWithScore);
            }
            else{
                int position = 0;
                while (progList.get(position).getScore() <= score){
                    position++;
                }
                progList.add(position, progWithScore);

            }
            if (progList.size() > PROGRESSIONS_TO_TRACK){
                progList.remove(progList.size()-1);
            }
        }
        else{
            content.put(last, new ArrayList<>());
            content.get(last).add(progWithScore);
        }
        checkRep();
    }
    
    
}