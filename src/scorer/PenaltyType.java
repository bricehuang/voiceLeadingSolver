package scorer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A class representing all possible penalties
 */
public enum PenaltyType {
    MOVE_THIRD, MOVE_FOURTH, MOVE_FIFTH, MOVE_BIG, MOVE_BIG_BASS,
    BAD_DOUBLING, DOUBLE_DOUBLING, BAD_TRIPLING, OMITTED_FIFTH, DOUBLED_LEADING_TONE,
    VOICE_OVERLAP,
    PARALLEL, DIRECT, 
    MELODIC_INTERVAL, VOICE_CROSSING,
    DOM_SEVEN_RES, DIM_SEVEN_RES;
    
    private static final Map<PenaltyType, String> STRING_REPS;
    static{
        Map<PenaltyType, String> tmpStringReps = new HashMap<>();
        tmpStringReps.put(MOVE_THIRD, "Non-Bass 3rd Move Penalty");
        tmpStringReps.put(MOVE_FOURTH, "Non-Bass 4th Move Penalty");
        tmpStringReps.put(MOVE_FIFTH, "Non-Bass 5th Move Penalty");
        tmpStringReps.put(MOVE_BIG, "Non-Bass Move Larger than 5th Penalty");
        tmpStringReps.put(MOVE_BIG_BASS, "Bass Move Larger than Octave Penalty");
        tmpStringReps.put(BAD_DOUBLING, "Bad Doubling Penalty");
        tmpStringReps.put(DOUBLE_DOUBLING, "Double Doubling Penalty");
        tmpStringReps.put(BAD_TRIPLING, "Bad Tripling Penalty");
        tmpStringReps.put(OMITTED_FIFTH, "Omitted Fifth Penalty");
        tmpStringReps.put(DOUBLED_LEADING_TONE, "Doubled Leading Tone Penalty");
        tmpStringReps.put(VOICE_OVERLAP, "Voice Overlap Penalty");
        tmpStringReps.put(PARALLEL, "Parallel Interval Penalty");
        tmpStringReps.put(DIRECT, "Direct Interval Penalty");
        tmpStringReps.put(MELODIC_INTERVAL, "Melodic Interval Penalty");
        tmpStringReps.put(VOICE_CROSSING, "Voice Crossing Penalty");
        tmpStringReps.put(DOM_SEVEN_RES, "Bad Dominant Seven Resolution Penalty"); 
        tmpStringReps.put(DIM_SEVEN_RES, "Bad Diminished Seven Resolution Penalty"); 
        STRING_REPS = Collections.unmodifiableMap(tmpStringReps);
    }
    
    private static final Map<PenaltyType, Integer> PENALTIES;
    static{
        Map<PenaltyType, Integer> tmpPenalties = new HashMap<>();
        tmpPenalties.put(MOVE_THIRD, 5);
        tmpPenalties.put(MOVE_FOURTH, 25);
        tmpPenalties.put(MOVE_FIFTH, 50);
        tmpPenalties.put(MOVE_BIG, 10000);
        tmpPenalties.put(MOVE_BIG_BASS, 10000);
        tmpPenalties.put(BAD_DOUBLING, 100);
        tmpPenalties.put(DOUBLE_DOUBLING, 200);
        tmpPenalties.put(BAD_TRIPLING, 100);
        tmpPenalties.put(OMITTED_FIFTH, 100);
        tmpPenalties.put(DOUBLED_LEADING_TONE, 100000);
        tmpPenalties.put(VOICE_OVERLAP, 50);
        tmpPenalties.put(PARALLEL, 1000000);
        tmpPenalties.put(DIRECT, 1000000);
        tmpPenalties.put(MELODIC_INTERVAL, 1000000);
        tmpPenalties.put(VOICE_CROSSING, 10000);
        tmpPenalties.put(DOM_SEVEN_RES, 1000); 
        tmpPenalties.put(DIM_SEVEN_RES, 1000); 
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
