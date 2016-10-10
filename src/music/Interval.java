package music;

/**
 * An immutable class representing an interval
 */
public class Interval {

    /*
     * Abstraction Function:
     * TODO
     * 
     * Rep Invariant:
     * TODO
     * 
     * Rep Exposure:
     * TODO
     */
    
    /**
     * @return the number of semitones in this interval
     */
    public int getSemitones(){
        throw new RuntimeException("Unimplemented");
    }

    /**
     * @return the number of scale degrees in this interval
     */
    public int getScaleDegrees(){
        throw new RuntimeException("Unimplemented");
    }
    
    /**
     * Finds the interval between two notes
     * @param note1 first note
     * @param note2 second note
     * @return interval between note1 and note2
     */
    public static Interval findIntervalBetweenNotes(
            Note note1, Note note2){
        throw new RuntimeException("Unimplemented");
    }
    
    @Override
    public boolean equals(Object other){
        throw new RuntimeException("Unimplemented");
    }
    
    @Override
    public int hashCode(){
        throw new RuntimeException("Unimplemented");
    }
    
    @Override
    public String toString(){
        throw new RuntimeException("Unimplemented");
    }
    
}
