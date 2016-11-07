package chords;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import music.*;

/**
 * A class representing all supported chord types
 */
public enum ChordType {
    MAJ, MIN, 
    DOM7, MAJ7, MIN7, DIM7;
        
    public static final Map<ChordType, List<BasicInterval>> CHORD_OFFSETS = new HashMap<>();
    static{
        CHORD_OFFSETS.put(MAJ, Arrays.asList(
                new BasicInterval(0,0), 
                new BasicInterval(2,4), 
                new BasicInterval(4,7)
                ));
        CHORD_OFFSETS.put(MIN, Arrays.asList(
                new BasicInterval(0,0), 
                new BasicInterval(2,3), 
                new BasicInterval(4,7)
                ));
        CHORD_OFFSETS.put(DOM7, Arrays.asList(
                new BasicInterval(0,0), 
                new BasicInterval(2,4), 
                new BasicInterval(4,7),
                new BasicInterval(6,10)
                ));
        CHORD_OFFSETS.put(MAJ7, Arrays.asList(
                new BasicInterval(0,0), 
                new BasicInterval(2,4), 
                new BasicInterval(4,7),
                new BasicInterval(6,11)
                ));
        CHORD_OFFSETS.put(MIN7, Arrays.asList(
                new BasicInterval(0,0), 
                new BasicInterval(2,3), 
                new BasicInterval(4,7),
                new BasicInterval(6,10)
                ));
        CHORD_OFFSETS.put(DIM7, Arrays.asList(
                new BasicInterval(0,0), 
                new BasicInterval(2,3), 
                new BasicInterval(4,6),
                new BasicInterval(6,9)
                ));
    }
    
    public int numberDistinctNotes(){
        return CHORD_OFFSETS.get(this).size();
    }
    
    /*******************
     * Object Contract *
     *******************/
       
    @Override
    public String toString(){
        if (this.equals(MAJ)){
            return "MAJOR";
        }
        else if (this.equals(MIN)){
            return "MINOR";
        }
        else if (this.equals(DOM7)){
            return "DOMINANT 7TH";
        }
        else if (this.equals(MAJ7)){
            return "MAJOR 7TH";
        }
        else if (this.equals(MIN7)){
            return "MINOR 7TH";
        }
        else if (this.equals(DIM7)){
            return "DIMINISHED 7TH";
        }
        else{
            throw new RuntimeException("Should not get here");
        }
    }
}
