package music;

/**
 * An immutable class representing an interval modulo octaves
 */
public class BasicInterval {

    private final int semitones;
    private final int scaleDegrees;
    
    /*
     * Abstraction Function:
     * Represents an interval with semitones semitones
     * and scaleDegrees scale degrees
     * (scaleDegrees is defined such that a unison is 0, so 
     * a 2nd is 1, and so on)
     * 
     * Rep Invariant:
     * TODO: All intervals should be dim, min, maj, or aug
     * 
     * Rep Exposure:
     * Only returns primitives and immutables
     */
        
    /**
     * Constructor for intervals
     * @param scaleDegrees number of scale degrees
     * @param semitones number of semitones
     */
    public BasicInterval(int scaleDegrees, int semitones){
        this.scaleDegrees = scaleDegrees;
        this.semitones = semitones;
        checkRep();
    }
    
    private void checkRep(){
        assert(0<= scaleDegrees);
        assert(scaleDegrees < Key.PITCHES_IN_SCALE);
        // TODO more robust checkrep
    }
    
    /****************
     * Computations *
     ****************/
    
    /**
     * Calculates the interval between two BasicNotes
     * @param first a BasicNote
     * @param second a BasicNote
     * @return the interval between these two notes, where second is assumed to be 
     * in the octave above first (including first and not including first + octave) 
     */
    public static BasicInterval intervalBetween(BasicNote first, BasicNote second){
        if (second.getReducedNote() >= first.getReducedNote()){
            return new BasicInterval(
                    second.getReducedNote() - first.getReducedNote(),
                    second.getReducedPitch() - first.getReducedPitch()
                    );
        }
        else{
            return new BasicInterval(
                    second.getReducedNote() - first.getReducedNote() + Key.PITCHES_IN_SCALE,
                    second.getReducedPitch() - first.getReducedPitch() + Key.SEMITONES_IN_OCTAVE
                    );
        }
    }
    
    /***********
     * Getters *
     ***********/
    
    /**
     * Simple getter method
     * @return the number of semitones in this interval
     */
    public int getSemitones(){
        return semitones;
    }

    /**
     * Simple getter method
     * @return the number of scale degrees in this interval
     */
    public int getScaleDegrees(){
        return scaleDegrees;
    }

    /*******************
     * Object Contract *
     *******************/

    
    @Override
    public boolean equals(Object other){
        if (!(other instanceof BasicInterval)){ return false;}
        BasicInterval that = (BasicInterval) other;
        return (this.scaleDegrees == that.scaleDegrees &&
                this.semitones == that.semitones);
    }
    
    @Override
    public int hashCode(){
        return scaleDegrees*71 + semitones * 43;
    }
    
    @Override
    public String toString(){
        return "("+scaleDegrees+","+semitones+")";
    }
    
}
