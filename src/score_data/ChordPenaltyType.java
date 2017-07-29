package score_data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A class representing all possible penalties on a chord
 */
public enum ChordPenaltyType implements PenaltyTypeNew {
    BAD_DOUBLING, DOUBLE_DOUBLING, BAD_TRIPLING, OMITTED_FIFTH, DOUBLED_LEADING_TONE, 
    VOICE_OVERLAP, 
    CADENCE_DOUBLING, 
    NOT_PAC; 
    
    private static final Map<ChordPenaltyType, String> STRING_REPS;
    static{
        Map<ChordPenaltyType, String> tmpStringReps = new HashMap<>();
        tmpStringReps.put(BAD_DOUBLING, "Bad Doubling Penalty");
        tmpStringReps.put(DOUBLE_DOUBLING, "Double Doubling Penalty");
        tmpStringReps.put(BAD_TRIPLING, "Bad Tripling Penalty");
        tmpStringReps.put(OMITTED_FIFTH, "Omitted Fifth Penalty");
        tmpStringReps.put(DOUBLED_LEADING_TONE, "Doubled Leading Tone Penalty");
        tmpStringReps.put(VOICE_OVERLAP, "Voice Overlap Penalty");
        tmpStringReps.put(CADENCE_DOUBLING, "Bad Doubling in Cadence Penalty"); 
        tmpStringReps.put(NOT_PAC, "Not Perfect Authentic Cadence Penalty");
        STRING_REPS = Collections.unmodifiableMap(tmpStringReps);
    }
    
    private static final Map<ChordPenaltyType, Integer> PENALTIES;
    static{
        Map<ChordPenaltyType, Integer> tmpPenalties = new HashMap<>();
        tmpPenalties.put(BAD_DOUBLING, 100);
        tmpPenalties.put(DOUBLE_DOUBLING, 200);
        tmpPenalties.put(BAD_TRIPLING, 100);
        tmpPenalties.put(OMITTED_FIFTH, 100);
        tmpPenalties.put(DOUBLED_LEADING_TONE, 100000);
        tmpPenalties.put(VOICE_OVERLAP, 50);
        tmpPenalties.put(CADENCE_DOUBLING, 1000); 
        tmpPenalties.put(NOT_PAC, 10); 
        PENALTIES = Collections.unmodifiableMap(tmpPenalties);
    }
    
    @Override
    public int value(){
        return PENALTIES.get(this);
    }
    
    @Override
    public String toString(){
        return STRING_REPS.get(this);
    }
}
