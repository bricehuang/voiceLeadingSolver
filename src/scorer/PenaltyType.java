package scorer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A class representing all possible penalties
 */
public enum PenaltyType {
    MOVEMENT,
    
    BAD_DOUBLING, DOUBLE_DOUBLING, BAD_TRIPLING, OMITTED_FIFTH, 
    VOICE_OVERLAP,
    PARALLEL, DIRECT, 
    MELODIC_INTERVAL, VOICE_CROSSING,
    DOM_SEVEN_RES, DIM_SEVEN_RES;
    
    private static final Map<PenaltyType, String> STRING_REPS;
    static{
        Map<PenaltyType, String> tmpStringReps = new HashMap<>();
        tmpStringReps.put(MOVEMENT, "Movement Penalty");
        tmpStringReps.put(BAD_DOUBLING, "Bad Doubling Penalty");
        tmpStringReps.put(DOUBLE_DOUBLING, "Double Doubling Penalty");
        tmpStringReps.put(BAD_TRIPLING, "Bad Tripling Penalty");
        tmpStringReps.put(OMITTED_FIFTH, "Omitted Fifth Penalty");
        tmpStringReps.put(PARALLEL, "Parallel Interval Penalty");
        tmpStringReps.put(DIRECT, "Direct Interval Penalty");
        tmpStringReps.put(MELODIC_INTERVAL, "Melodic Interval Penalty");
        tmpStringReps.put(VOICE_CROSSING, "Voice Crossing Penalty");
        tmpStringReps.put(DOM_SEVEN_RES, "Bad Dominant Seven Resolution Penalty"); 
        tmpStringReps.put(DIM_SEVEN_RES, "Bad Diminished Seven Resolution Penalty"); 
        STRING_REPS = Collections.unmodifiableMap(tmpStringReps);
    }
    
    public static final Map<PenaltyType, Integer> PENALTIES;
    static{
        Map<PenaltyType, Integer> tmpPenalties = new HashMap<>();
        //tmpPenalties.put(MOVEMENT, TODO);
        tmpPenalties.put(BAD_DOUBLING, 100);
        tmpPenalties.put(DOUBLE_DOUBLING, 200);
        tmpPenalties.put(BAD_TRIPLING, 100);
        tmpPenalties.put(OMITTED_FIFTH, 100);
        tmpPenalties.put(VOICE_OVERLAP, 50);
        tmpPenalties.put(PARALLEL, 1000000);
        tmpPenalties.put(DIRECT, 1000000);
        //tmpPenalties.put(MELODIC_INTERVAL, TODO);
        //tmpPenalties.put(VOICE_CROSSING, TODO);
        //tmpPenalties.put(DOM_SEVEN_RES, TODO); 
        //tmpPenalties.put(DIM_SEVEN_RES, TODO); 
        PENALTIES = Collections.unmodifiableMap(tmpPenalties);
    }
    
    @Override
    public String toString(){
        return STRING_REPS.get(this);
    }
}
