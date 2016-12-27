package music;

/**
 * An immutable class representing melodic intervals
 */
public class Interval {
    private final BasicInterval basicInterval;
    private final int octaves;
    private final boolean increasing;
    
    /*
     *  Abstraction function:
     *  represents an increasing/decreasing melodic interval whose value modulo octaves is
     *  basic Interval, and which contains octaves additional octaves
     *  
     *  Rep Invariant:
     *  octaves > 0, or
     *  octaves = 0 and basicInterval.scaleDegrees > 0, or
     *  octaves = 0, basicInterval.scaleDegrees = 0, and basicInterval.semitones > 0, or
     *  octaves = 0, basicInterval.scaleDegrees = 0, basicInterval.semitones = 0, and increasing
     *  
     *  
     *  Rep exposure:
     *  only returns primitives and immutables
     */
    
    /**
     * Constructor for Intervals
     * @param basicInterval 
     * @param octaves 
     * @param increasing
     */
    public Interval (BasicInterval basicInterval, int octaves, boolean increasing){
        this.basicInterval = basicInterval;
        this.octaves = octaves;
        this.increasing = increasing;
        checkRep();
    }
    
    private void checkRep(){
        if (octaves < 0){
            assert false;
        }
        else if (octaves == 0){
            if (basicInterval.getScaleDegrees() == 0){
                if (basicInterval.getSemitones() < 0){
                    assert false;
                }
                else if (basicInterval.getSemitones() == 0){
                    assert(increasing);
                }
            }
        }
    }
    
    /****************
     * Computations *
     ****************/
    
    /**
     * Returns the interval between two notes
     * @param first a note
     * @param second a note
     * @return the melodic interval between first and second, measured from first
     */
    public static Interval melodicIntervalBetween(Note first, Note second){
        BasicInterval basicInterval;
        int octaves;
        boolean increasing;
        
        if (second.getNoteID() > first.getNoteID()){
            increasing = true;
        }
        else if (second.getNoteID() < first.getNoteID()){
            increasing = false;
        }
        else{
            if (second.getPitchID() >= first.getPitchID()){
                increasing = true;
            }
            else{
                increasing = false;
            }
        }
        if (increasing){
            basicInterval = BasicInterval.intervalBetween(first.getBasicNote(), second.getBasicNote());
            octaves = (second.getNoteID() - first.getNoteID())/Key.PITCHES_IN_SCALE; 
        }
        else{
            basicInterval = BasicInterval.intervalBetween(second.getBasicNote(), first.getBasicNote());
            octaves = (first.getNoteID() - second.getNoteID())/Key.PITCHES_IN_SCALE;
        }
        return new Interval(basicInterval, octaves, increasing);
    }
    
    /***********
     * Getters *
     ***********/
    
    /**
     * Simple getter method
     * @return the interval's underlying BasicInterval
     */
    public BasicInterval getBasicInterval(){
        return basicInterval;
    }

    /**
     * Simple getter method
     * @return the number of octaves in this interval
     */
    public int getOctaves(){
        return octaves;
    }

    /**
     * Simple getter method
     * @return true iff the interval is increasing
     */
    public boolean getIncreasing(){
        return increasing;
    }
    
    /**
     * Get number of semitones in this interval
     * @return ditto
     */
    public int getSemitones(){
        int absoluteSemitones = basicInterval.getSemitones() + octaves * Key.SEMITONES_IN_OCTAVE;
        return (increasing ? absoluteSemitones : -absoluteSemitones);
    }

    /**
     * Get number of scale degrees in this interval
     * @return ditto
     */
    public int getScaleDegrees(){
        int absoluteScaleDegrees = basicInterval.getScaleDegrees() + octaves * Key.PITCHES_IN_SCALE;
        return (increasing ? absoluteScaleDegrees : -absoluteScaleDegrees);
    }
    
    /**
     * Simple getter method
     * @return the quality of ths interval
     */
    public IntervalQuality getQuality(){
        return basicInterval.getQuality();
    }

    /*******************
     * Object Contract *
     *******************/

    
    @Override
    public boolean equals(Object other){
        if (!(other instanceof Interval)){ return false;}
        Interval that = (Interval) other;
        return (this.basicInterval.equals(that.basicInterval) &&
                this.octaves == that.octaves &&
                this.increasing == that.increasing);
    }
    
    @Override
    public int hashCode(){
        return basicInterval.hashCode()*47 + octaves * 23 + (increasing ? 9001 : 129);
    }
    
    @Override
    public String toString(){
        return (increasing ? "UP" : "DOWN") + " " + basicInterval.toString() + " + " + octaves + " OCTAVES";
    }


}
