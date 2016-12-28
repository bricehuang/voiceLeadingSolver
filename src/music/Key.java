package music;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Immutable class representing a key 
 */
public class Key {
    
    private final BasicNote tonic;
    private final Boolean isMajor;
    
    /*
     * Abstraction Function:
     * represents the key with tonic tonic.  Tonality is major if isMajor = true, else minor.  
     * 
     * Rep Invariant:
     * None. 
     * 
     * Rep Exposure:
     * only returns primitives and immutables
     */
    
    public static final Integer SEMITONES_IN_OCTAVE = 12;
    public static final Integer PITCHES_IN_SCALE = 7;
    
    static final List<Integer> MAJOR_SCALE = Collections.unmodifiableList(
            Arrays.asList(0, 2, 4, 5, 7, 9, 11));
    static final List<Integer> MINOR_SCALE = Collections.unmodifiableList(
            Arrays.asList(0, 2, 3, 5, 7, 8, 10));
    
    static final List<Character> PITCH_NAMES = Arrays.asList(
            'C','D','E','F','G','A','B');
    
    /**
     * Constructs a key with given key signature and tonality
     * @param sharps number of sharps/flats in key signature, in -6,...,6
     * @param isMajor if true, then major; else minor
     */
    public Key(BasicNote tonic, boolean isMajor){
        this.tonic = tonic;
        this.isMajor = isMajor;
    }

    
    /******************
     *  COMPUTATIONS  *
     ******************/

    /**
     * Returns a BasicNote corresponding to an (unaltered) scale degree of this note
     * @param scaleDegree a number in 1-7
     * @return ditto
     */
    public BasicNote getScaleDegree(int scaleDegree){
        if (scaleDegree<1 || scaleDegree > PITCHES_IN_SCALE){
            throw new UnsupportedOperationException("scaleDegree has to be between 1 and 7");
        }
        int notes = scaleDegree-1;
        int semitones;
        if (isMajor){
            semitones = MAJOR_SCALE.get(notes);
        }
        else{
            semitones=  MINOR_SCALE.get(notes);
        }
        return tonic.transpose(new BasicInterval(notes, semitones), true);
    }
    
    /**
     * Return the scale degree of an inputted note
     * @param note 
     * @return note's scale degree 
     */
    public int findScaleDegree(BasicNote note){
        return BasicInterval.intervalBetween(tonic, note).getScaleDegrees()+1;
    }
    
    /*******************
     * WRITING PITCHES *
     *******************/
    
    /**
     * Returns a string representation of a note as it would appear in this key.  
     * Sharps are designated +, flats -.  
     * @param note
     * @return a note as it would be written in this key
     */
    public String renderBasicNote(BasicNote note){
        BasicInterval intervalFromTonic = BasicInterval.intervalBetween(tonic, note);
        int deviationFromKeySignature;
        if (isMajor){
            deviationFromKeySignature = intervalFromTonic.getSemitones() 
                    - MAJOR_SCALE.get(intervalFromTonic.getScaleDegrees());
        }
        else{
            deviationFromKeySignature = intervalFromTonic.getSemitones() 
                    - MINOR_SCALE.get(intervalFromTonic.getScaleDegrees());
        }
        int deviationFromNatural = note.getReducedPitch() - MAJOR_SCALE.get(note.getReducedNote());
        int sharpFlatsFromKeySignature = deviationFromNatural - deviationFromKeySignature;
        
        String stringRepOfNote = String.valueOf(PITCH_NAMES.get(note.getReducedNote()));
        if (sharpFlatsFromKeySignature == 0){
            if (deviationFromKeySignature > 0){
                for (int i=0; i<deviationFromKeySignature; i++){
                    stringRepOfNote += "+";
                }
            }
            else if (deviationFromKeySignature < 0){
                for (int i=0; i<-deviationFromKeySignature; i++){
                    stringRepOfNote += "-";
                }
            }
        }
        else if (sharpFlatsFromKeySignature == 1){
            if (deviationFromKeySignature>0){
                for (int i=0; i<deviationFromKeySignature+1; i++){
                    stringRepOfNote += "+";
                }
            }
            else if (deviationFromKeySignature<0){
                stringRepOfNote += "=";
                for (int i=0; i<-deviationFromKeySignature-1; i++){
                    stringRepOfNote += "-";
                }
            }
        }
        else if (sharpFlatsFromKeySignature == -1){
            if (deviationFromKeySignature>0){
                stringRepOfNote += "=";
                for (int i=0; i<deviationFromKeySignature-1; i++){
                    stringRepOfNote += "+";
                }
            }
            else if (deviationFromKeySignature<0){
                for (int i=0; i<-deviationFromKeySignature+1; i++){
                    stringRepOfNote += "-";
                }
            }
        }
        else{
            throw new RuntimeException("Should not get here.");
        }
        return stringRepOfNote;
    }


    /*******************
     * Object Contract *
     *******************

    /**
     * Two keys are equal iff they have the same key signature
     * and tonality.  
     * @param object another object
     * @return whether this and object are equal.   
     */
    @Override public boolean equals(Object object){
        if (!(object instanceof Key)){return false;}
        Key that = (Key) object;
        return (this.tonic.equals(that.tonic) && this.isMajor == that.isMajor);
    }

    @Override public int hashCode(){
        if (isMajor){
            return tonic.hashCode();
        }
        else{
            return -tonic.hashCode();
        }
    }

    /**
     * @return the name of this key
     */
    @Override public String toString(){
        if (isMajor){
            return tonic.toString() + " Major";
        }
        else{
            return tonic.toString() + " Minor";
        }
    }
    
}
