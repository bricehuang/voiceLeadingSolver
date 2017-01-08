package solver;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum ContextTag {
    APPLIED_DOMINANT, 
    NEAPOLITAN, 
    CADENTIAL_PREDOMINANT, // ii, ii65, iv, etc
    CADENTIAL_I64,
    CADENTIAL_V, // v or v7 right before i
    CADENCE; // last note 
    
    private static Map<ContextTag, String> STRING_REPS;
    static{
        Map <ContextTag, String> tmpStringRep = new HashMap<>(); 
        tmpStringRep.put(APPLIED_DOMINANT, "Applied Dominant");
        tmpStringRep.put(NEAPOLITAN, "Neapolitan");
        tmpStringRep.put(CADENTIAL_PREDOMINANT, "Cadential Predominant");
        tmpStringRep.put(CADENTIAL_I64, "Cadential I64");
        tmpStringRep.put(CADENTIAL_V, "Cadential V");
        tmpStringRep.put(CADENCE, "Cadence");
        STRING_REPS = Collections.unmodifiableMap(tmpStringRep);
    }
    
    /*******************
     * Object Contract *
     *******************/
    
    @Override
    public String toString(){
        return STRING_REPS.get(this);
    }
}
