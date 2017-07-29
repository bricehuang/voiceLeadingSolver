package score_data;

import java.util.HashMap;
import java.util.Map;

/**
 * A class that represents the score of a transition
 */
public class Score {

    private final Map<PenaltyTypeDeprecated, Integer> penaltyCount = new HashMap<>();
    private int totalPenalty;
    
    /*
     * Abstraction function:
     * penaltyCount counts the number of each penalty
     * 
     * Rep invariant:
     * counts are nonzero.  
     * 
     * Rep exposure:
     * returns only immutables, primitives
     */
    
    /**
     * Constructs a scorer. Debug flag is default off.  
     * If debug flag is on, prints a summary of penalties when total penalty
     * is requested. 
     */
    public Score(){
        checkRep();
    }
    
    private void checkRep(){
        int computedTotalPenalty = 0;
        for (PenaltyTypeDeprecated penalty : penaltyCount.keySet()){
            assert(penaltyCount.get(penalty) != 0);
            computedTotalPenalty += penaltyCount.get(penalty) * penalty.value();
        }
        assert(computedTotalPenalty == totalPenalty);
    }
    
    /**
     * Mutator.  Adds a penalty to the score
     * @param penalty a penalty type
     */
    public void addPenalty(PenaltyTypeDeprecated penalty){
        if (!penaltyCount.keySet().contains(penalty)){
            penaltyCount.put(penalty, 0);
        }
        penaltyCount.put(penalty, penaltyCount.get(penalty)+1);
        totalPenalty += penalty.value();
        if (penaltyCount.get(penalty)==0){
            penaltyCount.remove(penalty);
        }
        checkRep();
    }
    
    /**
     * Mutator.  Removes a penalty from the score
     * @param penalty a penalty type
     */
    public void removePenalty(PenaltyTypeDeprecated penalty){
        if (!penaltyCount.keySet().contains(penalty)){
            penaltyCount.put(penalty, 0);
        }
        penaltyCount.put(penalty, penaltyCount.get(penalty)-1);
        totalPenalty -= penalty.value();
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
        return totalPenalty;
    }
    
    /*******************
     * Object Contract *
     *******************/
    
    @Override
    public String toString(){
        String stringRep = "";
        for (PenaltyTypeDeprecated penalty : penaltyCount.keySet()){
            String penaltyDescription = penalty.toString() + ": " 
                    + penaltyCount.get(penalty)
                    + ".  Score: "
                    + penaltyCount.get(penalty) * penalty.value()
                    + "\n";
            stringRep += penaltyDescription;
        }
        stringRep += "Total Penalty: "+ totalPenalty + "\n";
        return stringRep;
    }
}
