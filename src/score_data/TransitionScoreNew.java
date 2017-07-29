package score_data;

import java.util.HashMap;
import java.util.Map;

/**
 * A class that represents the score of a chord
 */
public class TransitionScoreNew {

    private final Map<TransitionPenaltyType, Integer> penaltyCount = new HashMap<>();
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
    public TransitionScoreNew(){
        checkRep();
    }
    
    private void checkRep(){
        int computedTotalPenalty = 0;
        for (TransitionPenaltyType penalty : penaltyCount.keySet()){
            assert(penaltyCount.get(penalty) != 0);
            computedTotalPenalty += penaltyCount.get(penalty) * penalty.value();
        }
        assert(computedTotalPenalty == totalPenalty);
    }
    
    /**
     * Mutator.  Adds a penalty to the score
     * TODO: decide if I should deprecate negative updates, in 
     * which case the penaltyCount.get(penalty)==0 check can go
     * @param penalty a penalty type
     */
    public void updatePenalty(Map<TransitionPenaltyType, Integer> update){
            for (TransitionPenaltyType penalty: update.keySet()) {
                if (!penaltyCount.keySet().contains(penalty)) {
                    penaltyCount.put(penalty, 0);
                }
                int timesPenalty = update.get(penalty);
                penaltyCount.put(
                    penalty, penaltyCount.get(penalty)+timesPenalty
                );
                totalPenalty += penalty.value()*timesPenalty;
                if (penaltyCount.get(penalty)==0) {
                    penaltyCount.remove(penalty);
                }
                checkRep();
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
        for (TransitionPenaltyType penalty : penaltyCount.keySet()){
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
