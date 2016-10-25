package music;

import java.util.Arrays;
import java.util.List;

/**
 * Immutable class representing a key 
 */
public class Key {
    
    private final Integer sharps;
    private final Boolean isMajor;
    
    private final BasicNote tonic;
    
    /*
     * Abstraction Function:
     * represents the key with sharps sharps.  Tonality is major if isMajor = true, else minor.  
     * 
     * Rep Invariant:
     * None. 
     * 
     * Rep Exposure:
     * only returns primitives and immutables
     */
    
    public static final Integer MAX_SHARPS_FLATS = 6;
    public static final Integer SEMITONES_IN_HALF_OCTAVE = 6;
    public static final Integer SEMITONES_IN_OCTAVE = 12;
    public static final Integer PITCHES_IN_SCALE = 7;
    
    public static final List<String> MAJOR_KEY_NAMES = Arrays.asList(
            "Gb", "Db", "Ab", "Eb", "Bb", "F", "C", "G", "D", "A", "E", "B", "F#");
    public static final List<String> MINOR_KEY_NAMES = Arrays.asList(
            "Eb", "Bb", "F", "C", "G", "D", "A", "E", "B", "F#", "C#", "G#", "D#");
    
    public static final List<Integer> MAJOR_SCALE = Arrays.asList(
            0, 2, 4, 5, 7, 9, 11);
    public static final List<Integer> MINOR_SCALE = Arrays.asList(
            0, 2, 3, 5, 7, 8, 10);
    
    public static final List<Character> PITCH_NAMES = Arrays.asList(
            'C','D','E','F','G','A','B');


    private static final List<BasicNote> MAJOR_KEY_TONICS = Arrays.asList(
            new BasicNote(4,6),  // Gb 
            new BasicNote(1,1),  // Db 
            new BasicNote(5,8),  // Ab
            new BasicNote(2,3),  // Eb
            new BasicNote(6,10), // Bb
            new BasicNote(3,5),  // F
            new BasicNote(0,0),  // C
            new BasicNote(4,7),  // G 
            new BasicNote(1,2),  // D
            new BasicNote(5,9),  // A
            new BasicNote(2,4),  // E
            new BasicNote(6,11), // B
            new BasicNote(3,6)   // F#
            );
    private static final List<BasicNote> MINOR_KEY_TONICS = Arrays.asList(
            new BasicNote(2,3),  // Eb
            new BasicNote(6,10), // Bb
            new BasicNote(3,5),  // F
            new BasicNote(0,0),  // C
            new BasicNote(4,7),  // G 
            new BasicNote(1,2),  // D
            new BasicNote(5,9),  // A
            new BasicNote(2,4),  // E
            new BasicNote(6,11), // B
            new BasicNote(3,6),  // F#
            new BasicNote(0,1),  // C# 
            new BasicNote(4,8),  // G#
            new BasicNote(1,3)   // D#
            );
    
    private static final Key C_MAJOR = new Key(0,true); 
            
    
    /**
     * Constructs a key with given key signature and tonality
     * @param sharps number of sharps/flats in key signature, in -6,...,6
     * @param isMajor if true, then major; else minor
     */
    public Key(int sharps, boolean isMajor){
        if (sharps < -6 || sharps > 6){
            throw new UnsupportedOperationException("sharps should be in -6,...,6.");
        }
        this.sharps = sharps;
        this.isMajor = isMajor;
        if (isMajor){
            this.tonic = MAJOR_KEY_TONICS.get(sharps+MAX_SHARPS_FLATS);
        }
        else{
            this.tonic = MINOR_KEY_TONICS.get(sharps+MAX_SHARPS_FLATS);
        }
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
        return tonic.transpose(new Interval(notes, semitones), true);
    }
    
    /**
     * Return the scale degree of an inputted note
     * @param note 
     * @return note's scale degree 
     */
    public int findScaleDegree(BasicNote note){
        return Interval.intervalBetween(tonic, note).getScaleDegrees()+1;
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
        Interval intervalFromTonic = Interval.intervalBetween(tonic, note);
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
        return (this.sharps == that.sharps && this.isMajor == that.isMajor);
    }

    @Override public int hashCode(){
        if (isMajor){
            return sharps;
        }
        else{
            return sharps + 7*MAX_SHARPS_FLATS;
        }
    }

    /**
     * @return the name of this key
     */
    @Override public String toString(){
        if (isMajor){
            return MAJOR_KEY_NAMES.get(sharps+MAX_SHARPS_FLATS) + " Major";
        }
        else{
            return MINOR_KEY_NAMES.get(sharps+MAX_SHARPS_FLATS) + " Minor";
        }
    }
    
}
