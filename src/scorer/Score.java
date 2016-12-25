package scorer;

import java.util.HashMap;
import java.util.Map;

/**
 * A class that represents the score of a transition
 */
public class Score {

    private final Map<PenaltyType, Integer> penaltyCount = new HashMap<>();
    private final boolean debug;
    
    /*
     * Abstraction function:
     * penaltyCount counts the number of each penalty
     * 
     * Rep invariant:
     * counts are nonzero.  
     * 
     * Rep exposure:
     * returns only immutables
     */
    
    /**
     * Constructs a scorer. Debug flag is default off.  
     * If debug flag is on, prints a summary of penalties when total penalty
     * is requested. 
     */
    public Score(){
        this.debug = false;
        checkRep();
    }
    
    /**
     * Constructs a scorer with user inputted debug flag
     * @param debug if true, 
     */
    public Score(boolean debug){
        this.debug = debug;
        checkRep();
    }
    
    private void checkRep(){
        for (PenaltyType penalty : penaltyCount.keySet()){
            assert(penaltyCount.get(penalty) != 0);
        }
    }
    
    /**
     * Mutator.  Adds a penalty to the score
     * @param penalty a penalty type
     */
    public void addPenalty(PenaltyType penalty){
        if (!penaltyCount.keySet().contains(penalty)){
            penaltyCount.put(penalty, 0);
        }
        penaltyCount.put(penalty, penaltyCount.get(penalty)+1);
        if (penaltyCount.get(penalty)==0){
            penaltyCount.remove(penalty);
        }
        checkRep();
    }
    
    /**
     * Mutator.  Removes a penalty from the score
     * @param penalty a penalty type
     */
    public void removePenalty(PenaltyType penalty){
        if (!penaltyCount.keySet().contains(penalty)){
            penaltyCount.put(penalty, 0);
        }
        penaltyCount.put(penalty, penaltyCount.get(penalty)-1);
        if (penaltyCount.get(penalty)==0){
            penaltyCount.remove(penalty);
        }
        checkRep();
    }
    
    /**
     * Computes the total penalty of this transition
     * @return total penalty of this transition
     */
    public int totalScore(){
        int totalScore = 0;
        for (PenaltyType penalty: penaltyCount.keySet()){
            totalScore += penaltyCount.get(penalty) 
                    * penalty.value();
        }
        if(debug){
            System.err.println(this.toString());
        }
        return totalScore;
    }
    
    /*******************
     * Object Contract *
     *******************/
    
    @Override
    public String toString(){
        String stringRep = "";
        for (PenaltyType penalty : penaltyCount.keySet()){
            String penaltyDescription = penalty.toString() + ": " 
                    + penaltyCount.get(penalty)
                    + ".  Score: "
                    + penaltyCount.get(penalty) * penalty.value()
                    + "\n";
            stringRep += penaltyDescription;
        }
        stringRep += "Total Penalty: "+ totalScore() + "\n";
        return stringRep;
    }
}
