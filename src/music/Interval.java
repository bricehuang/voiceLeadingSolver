package music;

import java.util.Arrays;
import java.util.List;

/**
 * An immutable class representing an interval
 */
public class Interval {

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
     * All intervals should be dim, min, maj, or aug
     * 
     * Rep Exposure:
     * Only returns primitives and immutables
     */
    
    //TODO fix this
//    private static final List<Integer> lowerBoundSemitones = 
//            Arrays.asList(0,1,2,4,6,7,9);
//    private static final List<Integer> upperBoundSemitones = 
//            Arrays.asList(1,3,5,6,8,10,12);
    
    /**
     * Constructor for intervals
     * @param semitones number of semitones
     * @param scaleDegrees number of scale degrees
     */
    public Interval(int scaleDegrees, int semitones){
        this.scaleDegrees = scaleDegrees;
        this.semitones = semitones;
        checkRep();
    }
    
    private void checkRep(){
//        assert(this.semitones >= 0);
//        assert(this.scaleDegrees >= 0);
//        int reducedSemitones = this.semitones;
//        int reducedScaleDegrees =  this.scaleDegrees;
//        while (reducedScaleDegrees >= Key.PITCHES_IN_SCALE){
//            reducedSemitones -= Key.SEMITONES_IN_OCTAVE;
//            reducedScaleDegrees -= Key.PITCHES_IN_SCALE;
//        }
//        assert(reducedSemitones >= lowerBoundSemitones.get(reducedScaleDegrees));
//        assert(reducedSemitones <= upperBoundSemitones.get(reducedScaleDegrees));
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
    public static Interval intervalBetween(BasicNote first, BasicNote second){
        if (second.getReducedNote() >= first.getReducedNote()){
            return new Interval(
                    second.getReducedNote() - first.getReducedNote(),
                    second.getReducedPitch() - first.getReducedPitch()
                    );
        }
        else{
            return new Interval(
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
        if (!(other instanceof Interval)){ return false;}
        Interval that = (Interval) other;
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
