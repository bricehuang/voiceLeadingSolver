package music;

/**
 * An immutable class representing a note modulo octaves.  
 */
public class BasicNote{
    
    private final Integer reducedNote;
    private final Integer reducedPitch;
    
    
    /*
     * Abstraction Function: 
     * reducedNote is the number of notes the note is above a C (0-6)  
     * reducedPitch is the number of semitones the note is above/below a C
     * natural.  This can be not in the range 0-11: for example, Cb is 
     * -1, B# is 12.  reducedPitch is such that this not has at most 2 
     * sharps/flats
     * 
     * Rep Invariant: 
     * 0 <= reducedNote < 7
     * reducedPitch is such that this note has at most 2 sharts/flats 
     * 
     * Rep Exposure:
     * only returns primitives and immutables
     */

    private void checkRep(){
        assert(reducedNote >= 0);
        assert(reducedNote < Key.PITCHES_IN_SCALE);
        int accidental = reducedPitch - Key.MAJOR_SCALE.get(reducedNote);
        assert(-2 <= accidental);
        assert(accidental <= 2);
    }

    /**
     * Constructor for BasicNotes
     * @param reducedNote reduced note
     * @param reducedPitch reduced pitch
     */
    public BasicNote(int reducedNote, int reducedPitch){
        this.reducedNote = reducedNote;
        this.reducedPitch = reducedPitch;
        checkRep();
    }
    
    
    /*********************
     *  Getter Methods   *
     *********************/
    
    /**
     * @return reduced pitch
     */
    public int getReducedNote(){
        return reducedNote;
    }

    /**
     * @return reduced pitch
     */
    public int getReducedPitch(){
        return reducedPitch;
    }

    /****************
     * Computations *
     ****************/
    
    public BasicNote transpose(Interval interval, boolean up){
        int reducedPitch, reducedNote; 
        if (up){
            reducedPitch = this.reducedPitch + interval.getSemitones();
            reducedNote = this.reducedNote + interval.getScaleDegrees();
            while (reducedNote >= Key.PITCHES_IN_SCALE){
                reducedNote -= Key.PITCHES_IN_SCALE;
                reducedPitch -= Key.SEMITONES_IN_OCTAVE;
            }
        }
        else{
            reducedPitch = this.reducedPitch - interval.getSemitones();
            reducedNote = this.reducedNote - interval.getScaleDegrees();
            while (reducedNote < 0){
                reducedNote += Key.PITCHES_IN_SCALE;
                reducedPitch += Key.SEMITONES_IN_OCTAVE;
            }
        }
        return new BasicNote(reducedNote, reducedPitch);
    }
    
    /**
     * Gets the string representation of a note as it is written in a given key 
     * @param key a key
     * @return ''
     */
    public String renderInKey(Key key){
        throw new RuntimeException("Unimplemented.");
    }

    
    /*******************
     * Object Contract *
     *******************/
    
    /**
     * Two basicNotes are equal iff they have the same reduced pitch and note.  
     * @param object another object
     * @return whether this and object are equal.   
     */
    @Override public boolean equals(Object object){
        if (!(object instanceof BasicNote)){ return false; }
        BasicNote that = (BasicNote) object;
        return (this.reducedPitch == that.reducedPitch && this.reducedNote == that.reducedNote);
    }

    @Override public int hashCode(){
        return reducedPitch + 7*reducedNote; 
    }

    /**
     * String representation of this note
     * @return the name of this note, as written in C Major
     */
    @Override public String toString(){
        String stringRep = "";
        stringRep += Key.PITCH_NAMES.get(reducedNote);
        int accidental = reducedPitch - Key.MAJOR_SCALE.get(reducedNote);
        if (accidental>0){
            for (int i=0; i<accidental; i++){
                stringRep += '+';
            }
        }
        else if (accidental < 0){
            for (int i=0; i<-accidental; i++){
                stringRep += '-';
            }
        }
        return stringRep;
    }

}
