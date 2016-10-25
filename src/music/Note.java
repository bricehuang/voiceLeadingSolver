package music;

/**
 * An immutable class representing a note.  
 */
public class Note {

    private final BasicNote basicNote;
    private final int octave;
    private final int pitchID; 
    private final int noteID;
    
    /*
     * Abstraction Function: 
     * basicNote is this note modulo octaves
     * octave is the traditional octave number e.g.  C4-B4 has octave number 4.    
     * 
     * Rep Invariant: 
     * pitchID and basicNote's reducedPitch are same note
     * noteID and basicNote's pitch are same note
     * noteID and octave check out
     * pitchID and octave check out
     * 
     * Rep Exposure:
     * only returns primitives and immutables
     */
    
    private void checkRep(){
        assert((pitchID - basicNote.getReducedPitch())%
                Key.SEMITONES_IN_OCTAVE == 0);
        assert((noteID - basicNote.getReducedNote())%
                Key.PITCHES_IN_SCALE == 0);
        assert((pitchID - basicNote.getReducedPitch())/Key.SEMITONES_IN_OCTAVE == octave);
        assert((noteID - basicNote.getReducedNote())/Key.PITCHES_IN_SCALE == octave);
    }
    
    /**
     * Creates a note  
     * @param BasicNote the BasicNote corresponding to this
     * note's pitch
     * @param octave  The octave containing this note, as written in 
     * scientific pitch notation.       
     */
    public Note(BasicNote note, int octave){
        this.basicNote = note;
        this.octave = octave;
        this.noteID = note.getReducedNote() + octave*Key.PITCHES_IN_SCALE;
        this.pitchID = note.getReducedPitch() + octave*Key.SEMITONES_IN_OCTAVE;
        checkRep();
    }
    
    /******************
     * Getter Methods *
     ******************/
    
    /**
     * Gets the pitch (in semitones above/below C4) associated
     * with this note.  
     * @return pitch
     */
    public int getPitchID(){
        return this.pitchID;
    }
    
    /**
     * Gets the note (in notes above/below C4) associated
     * with this note.  
     * @return note
     */
    public int getNoteID(){
        return this.noteID;
    }

    /**
     * Gets the octave associated with this note.  
     * @return octave
     */
    public int getOctave(){
        return this.octave;
    }
    
    /**
     * Get the BasicNote associated with this note
     * @return basicNote
     */
    public BasicNote getBasicNote(){
        return this.basicNote;
    }
    
    /****************
     * Computations *
     ****************/
    
    public String renderInKey(Key key){
        return basicNote.renderInKey(key) + octave;
    }

    /*******************
     * Object contract *
     *******************/
    
    /**
     * Plays this note
     */
    public void play(/*params here*/){
        throw new RuntimeException("Unimplemented.");
    }
    
    /**
     * Two notes are equal iff they have the same pitch and
     * writing.  
     * @param object another object
     * @return whether this and object are equal.   
     */
    @Override public boolean equals(Object object){
        if (!(object instanceof Note)){ return false;}
        Note that = (Note) object;
        return (this.basicNote.equals(that.basicNote) &&
                this.octave == that.octave);
                
    }

    @Override public int hashCode(){
        return this.basicNote.hashCode() + 163 * octave;
    }

    /**
     * @return the name of this note in scientific pitch
     * notation
     */
    @Override public String toString(){
        return this.basicNote.toString() + octave;
    }

}
