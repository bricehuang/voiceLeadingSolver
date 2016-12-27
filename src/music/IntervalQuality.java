package music;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public enum IntervalQuality {
    // TODO test
    MAJ, MIN, PFT,
    AUG, DIM,
    ERR;
    
    private static final Map<IntervalQuality, String> STRING_REPS;
    static{
        Map<IntervalQuality, String> stringRepTmp = new HashMap<>();
        stringRepTmp.put(MAJ, "Major");
        stringRepTmp.put(MIN, "Minor");
        stringRepTmp.put(PFT, "Perfect");
        stringRepTmp.put(AUG, "Augmented");
        stringRepTmp.put(DIM, "Diminished");
        stringRepTmp.put(ERR, "ERROR");
        STRING_REPS = Collections.unmodifiableMap(stringRepTmp);
    }

    private static final Set<Integer> PERFECT_INTERVALS = 
            Collections.unmodifiableSet(new HashSet<>(Arrays.asList(0,3,4)));
    private static final List<Integer> PERFECT_AND_MAJOR_SEMITONES = 
            Collections.unmodifiableList(Arrays.asList(0,2,4,5,7,9,11));

    /****************
     * Computations *
     ****************/
    
    static IntervalQuality qualityOf(int scaleDegrees, int semitones) {
        if (PERFECT_INTERVALS.contains(scaleDegrees)){
            int diffFromPerfect = semitones- PERFECT_AND_MAJOR_SEMITONES.get(scaleDegrees);
            if (diffFromPerfect == -1){
                return DIM;
            }
            else if (diffFromPerfect == 0){
                return PFT;
            }
            else if (diffFromPerfect == 1){
                return AUG;
            }
            else{
                return ERR;
            }
        }
        else{
            int diffFromMajor = semitones - PERFECT_AND_MAJOR_SEMITONES.get(scaleDegrees);
            if (diffFromMajor == -2){
                return DIM;
            }
            if (diffFromMajor == -1){
                return MIN;
            }
            else if (diffFromMajor == 0){
                return MAJ;
            }
            else if (diffFromMajor == 1){
                return AUG;
            }
            else{
                return ERR;
            }
        }
    }
    
    /*******************
     * Object contract *
     *******************/
    
    @Override
    public String toString(){
        return STRING_REPS.get(this);
    }
}
