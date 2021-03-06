package chords;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import music.BasicInterval;

/**
 * A class representing all supported chord types
 */
public enum ChordType {
    MAJ, MIN, DIM,
    DOM7, MAJ7, MIN7, DIM7, HDIM7;
        
    private static final Map<ChordType, List<BasicInterval>> CHORD_OFFSETS;
    static{
        Map<ChordType, List<BasicInterval>> tmpChordOffsets = new HashMap<>();
        tmpChordOffsets.put(MAJ, Arrays.asList(
            new BasicInterval(0,0), 
            new BasicInterval(2,4), 
            new BasicInterval(4,7)
        ));
        tmpChordOffsets.put(MIN, Arrays.asList(
            new BasicInterval(0,0), 
            new BasicInterval(2,3), 
            new BasicInterval(4,7)
        ));
        tmpChordOffsets.put(DIM, Arrays.asList(
            new BasicInterval(0,0), 
            new BasicInterval(2,3), 
            new BasicInterval(4,6)
        ));
        tmpChordOffsets.put(DOM7, Arrays.asList(
            new BasicInterval(0,0), 
            new BasicInterval(2,4), 
            new BasicInterval(4,7),
            new BasicInterval(6,10)
        ));
        tmpChordOffsets.put(MAJ7, Arrays.asList(
            new BasicInterval(0,0), 
            new BasicInterval(2,4), 
            new BasicInterval(4,7),
            new BasicInterval(6,11)
        ));
        tmpChordOffsets.put(MIN7, Arrays.asList(
            new BasicInterval(0,0), 
            new BasicInterval(2,3), 
            new BasicInterval(4,7),
            new BasicInterval(6,10)
        ));
        tmpChordOffsets.put(DIM7, Arrays.asList(
            new BasicInterval(0,0), 
            new BasicInterval(2,3), 
            new BasicInterval(4,6),
            new BasicInterval(6,9)
        ));
        tmpChordOffsets.put(HDIM7, Arrays.asList(
            new BasicInterval(0,0), 
            new BasicInterval(2,3), 
            new BasicInterval(4,6),
            new BasicInterval(6,10)
        ));
        CHORD_OFFSETS = Collections.unmodifiableMap(tmpChordOffsets);
    }
    
    private static final Map<ChordType, String> STRING_REPS;
    static{
        Map<ChordType, String> tmpStringReps = new HashMap<>();
        tmpStringReps.put(MAJ, "MAJOR");
        tmpStringReps.put(MIN, "MINOR");
        tmpStringReps.put(DIM, "DIMINISHED");
        tmpStringReps.put(DOM7, "DOMINANT 7TH");
        tmpStringReps.put(MAJ7, "MAJOR 7TH");
        tmpStringReps.put(MIN7, "MINOR 7TH");
        tmpStringReps.put(DIM7, "DIMINISHED 7TH");
        tmpStringReps.put(HDIM7, "HALF DIMINISHED 7TH");
        STRING_REPS = Collections.unmodifiableMap(tmpStringReps);
    }
    
    public List<BasicInterval> getChordOffset(){
        return Collections.unmodifiableList(CHORD_OFFSETS.get(this));
    }
    
    public int numberDistinctNotes(){
        return CHORD_OFFSETS.get(this).size();
    }
    
    /*******************
     * Object Contract *
     *******************/
       
    @Override
    public String toString(){
        return STRING_REPS.get(this);
    }
}
