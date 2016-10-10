package music;

/**
 * An immutable class representing a note modulo octaves.  
 */
public class BasicNote{
    
    private final Integer reducedPitch;
    private final Key key;
    private final Integer accidental;
    
    /*
     * Abstraction Function: 
     * reducedPitch is the number of semitones the note is above a C.  
     * accidental is the number of sharps (+) or flats (-) applied to the note.  
     * key is the 
     * 
     * Rep Invariant: 
     * 0<= reducedPitch < 12
     * reducedPitch - accidental is in the key
     * 
     * Rep Exposure:
     * only returns primitives and immutables
     */

    private void checkRep(){
        assert(0 <= reducedPitch);
        assert(reducedPitch < 12);
        int reducedPitchBaseNote = (reducedPitch-accidental+ Key.SEMITONES_IN_OCTAVE) 
                % Key.SEMITONES_IN_OCTAVE;
        assert(key.isReducedPitchInKey(reducedPitchBaseNote));
    }

    /**
     * Constructs a BasicNote 
     * @param c the note's letter value 
     * @param key the key in which this note is played
     * @param accidental number of semitones to shift the note up or down, relative
     * to the default note with this letter value in the key.  
     */
    public BasicNote(char c, Key key, int accidental){
        this.reducedPitch = key.getReducedPitchOfLetterNote(c, accidental);
        this.accidental = accidental;
        this.key = key;
        checkRep();
    }

    /**
     * Constructs a BasicNote
     * @param scaleDegree the scale degree in the key
     * @param key the key in which this note is played
     * @param accidental number of semitones to shift the note up or down, relative
     * to the default note with this scale degree in the key.
     */
    public BasicNote(int scaleDegree, Key key, int accidental){
        this.reducedPitch = key.getReducedPitchOfScaleDegree(scaleDegree, accidental);
        this.accidental = accidental;
        this.key = key;
        checkRep();
    }

    /**
     * Two basicNotes are equal iff they have the same reduced pitch.  
     * @param object another object
     * @return whether this and object are equal.   
     */
    @Override public boolean equals(Object object){
        if (!(object instanceof BasicNote)){ return false; }
        BasicNote that = (BasicNote) object;
        return (this.reducedPitch == that.reducedPitch && this.accidental == that.accidental
                && this.key.equals(that.key));
    }

    @Override public int hashCode(){
        return reducedPitch + 7*accidental + 23*key.hashCode(); 
    }

    /**
     * String representation of this note
     * @return the name of this note, as spelled within the key
     */
    @Override public String toString(){
        return key.getWrittenNameOfReducedPitch(reducedPitch, accidental);
    }

}
