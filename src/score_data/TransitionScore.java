package score_data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A class that represents the score of a chord
 */
public class TransitionScore {

    private final Map<TransitionPenaltyType, Integer> penaltyCount = new HashMap<>();
    private int totalPenalty;
    
    /*
     * Abstraction function:
     * penaltyCount counts the number of each penalty
     * 
     * Rep invariant:
     * counts are positive.  
     * 
     * Rep exposure:
     * returns only immutables, primitives
     */
    
    /**
     * Constructs a scorer. Debug flag is default off.  
     * If debug flag is on, prints a summary of penalties when total penalty
     * is requested. 
     */
    public TransitionScore(){
        checkRep();
    }
    
    private void checkRep(){
        int computedTotalPenalty = 0;
        for (TransitionPenaltyType penalty : penaltyCount.keySet()){
            assert(penaltyCount.get(penalty) > 0);
            computedTotalPenalty += penaltyCount.get(penalty) * penalty.value();
        }
        assert(computedTotalPenalty == totalPenalty);
    }
    
    /**
     * Adds a single penalty to the score.  
     * @param penalty
     */
    public void addPenalty(TransitionPenaltyType penalty){
        if (!penaltyCount.keySet().contains(penalty)) {
            penaltyCount.put(penalty, 0);
        }
        penaltyCount.put(penalty, penaltyCount.get(penalty)+1);
        totalPenalty += penalty.value();
        checkRep();
    }
    
    /**
     * Mutator.  Adds the contents of a TransitionScoreNew to the score
     * @param penalty a penalty type
     */
    public void updatePenalty(TransitionScore updateScore){
        Map<TransitionPenaltyType, Integer> update = updateScore.getPenaltyCount(); 
		for (TransitionPenaltyType penalty: update.keySet()) {
			if (!penaltyCount.keySet().contains(penalty)) {
				penaltyCount.put(penalty, 0);
			}
			int timesPenalty = update.get(penalty);
			penaltyCount.put(
				penalty, penaltyCount.get(penalty)+timesPenalty
			);
		}
		totalPenalty += updateScore.totalPenalty;
        checkRep();
    }
        
    /**
     * Gets the total penalty of this transition
     * @return total penalty of this transition
     */
    public int totalScore(){
        return totalPenalty;
    }

    /**
     * Gets the penalty counts of this transition
     * @return penalty counts of this transition
     */
    public Map<TransitionPenaltyType, Integer> getPenaltyCount(){
        return Collections.unmodifiableMap(penaltyCount);
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