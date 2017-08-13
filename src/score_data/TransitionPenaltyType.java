package score_data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A class representing all possible penalties on transitions
 */
public enum TransitionPenaltyType {
    MOVE_THIRD, MOVE_FOURTH, MOVE_FIFTH, MOVE_BIG, MOVE_BIG_BASS, 
    PARALLEL, DIRECT,  
    MELODIC_INTERVAL, VOICE_CROSSING, 
    DOM_SEVEN_RES, DIM_SEVEN_RES, 
    MOVE_BIG_CADENCE, CADENTIAL_II7_SUSPEND, 
    BAD_NEAPOLITAN_RES, NEAPOLITAN_AUG_2ND_OK; 
    
    private static final Map<TransitionPenaltyType, String> STRING_REPS;
    static{
        Map<TransitionPenaltyType, String> tmpStringReps = new HashMap<>();
        tmpStringReps.put(MOVE_THIRD, "Non-Bass 3rd Move Penalty");
        tmpStringReps.put(MOVE_FOURTH, "Non-Bass 4th Move Penalty");
        tmpStringReps.put(MOVE_FIFTH, "Non-Bass 5th Move Penalty");
        tmpStringReps.put(MOVE_BIG, "Non-Bass Move Larger than 5th Penalty");
        tmpStringReps.put(MOVE_BIG_BASS, "Bass Move Larger than Octave Penalty");
        tmpStringReps.put(PARALLEL, "Parallel Interval Penalty");
        tmpStringReps.put(DIRECT, "Direct Interval Penalty");
        tmpStringReps.put(MELODIC_INTERVAL, "Melodic Interval Penalty");
        tmpStringReps.put(VOICE_CROSSING, "Voice Crossing Penalty");
        tmpStringReps.put(DOM_SEVEN_RES, "Bad Dominant Seven Resolution Penalty"); 
        tmpStringReps.put(DIM_SEVEN_RES, "Bad Diminished Seven Resolution Penalty"); 
        tmpStringReps.put(MOVE_BIG_CADENCE, "Non-Step Move in Cadence Penalty");
        tmpStringReps.put(CADENTIAL_II7_SUSPEND, "Must Suspend 7th of ii7 Penalty");
        tmpStringReps.put(BAD_NEAPOLITAN_RES, "Bad Neapolitan Resolution");
        tmpStringReps.put(NEAPOLITAN_AUG_2ND_OK, "Good Neapolitan Resolution - aug 2nd OK");
        STRING_REPS = Collections.unmodifiableMap(tmpStringReps);
    }
    
    private static final Map<TransitionPenaltyType, Integer> PENALTIES;
    static{
        Map<TransitionPenaltyType, Integer> tmpPenalties = new HashMap<>();
        tmpPenalties.put(MOVE_THIRD, 5);
        tmpPenalties.put(MOVE_FOURTH, 25);
        tmpPenalties.put(MOVE_FIFTH, 50);
        tmpPenalties.put(MOVE_BIG, 10000);
        tmpPenalties.put(MOVE_BIG_BASS, 10000);
        tmpPenalties.put(PARALLEL, 1000000);
        tmpPenalties.put(DIRECT, 1000000);
        tmpPenalties.put(MELODIC_INTERVAL, 1000000);
        tmpPenalties.put(VOICE_CROSSING, 10000);
        tmpPenalties.put(DOM_SEVEN_RES, 1000); 
        tmpPenalties.put(DIM_SEVEN_RES, 1000); 
        tmpPenalties.put(MOVE_BIG_CADENCE, 1000);
        tmpPenalties.put(CADENTIAL_II7_SUSPEND, 1000);
        tmpPenalties.put(BAD_NEAPOLITAN_RES, 1000);
        tmpPenalties.put(NEAPOLITAN_AUG_2ND_OK, -tmpPenalties.get(MELODIC_INTERVAL));
        PENALTIES = Collections.unmodifiableMap(tmpPenalties);
    }
    
    public int value(){
        return PENALTIES.get(this);
    }
    
    @Override
    public String toString(){
        return STRING_REPS.get(this);
    }
}
