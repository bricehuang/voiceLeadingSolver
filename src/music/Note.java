package music;

/**
 * An immutable class representing a note.  
 */
public class Note {

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
    
    private void checkRep(){
        throw new RuntimeException("Unimplemented.");
    }
    
    /**
     * Creates a note  
     * @param BasicNote the BasicNote corresponding to this
     * note's pitch
     * @param octave  The octave containing this note, as written in 
     * scientific pitch notation.       
     */
    public Note(BasicNote note, int octave){
        throw new RuntimeException("Unimplemented.");
    }
    
    /**
     * Gets the pitch (in semitones above/below C4) associated
     * with this note.  
     * @return pitch
     */
    public int getPitch(){
        throw new RuntimeException("Unimplemented");
    }
    
    /**
     * Computes the interval between two notes
     * @param otherNote another note
     * @return the interval (in half-steps) from this to otherNote
     */
    public Interval interval(Note otherNote){
        throw new RuntimeException("Unimplemented.");
    }
    
    /**
     * Plays this note
     */
    public void play(/*params here*/){
        throw new RuntimeException("Unimplemented.");
    }
    
    /**
     * Two notes are equal iff they have the same pitch.  
     * @param object another object
     * @return whether this and object are equal.   
     */
    @Override public boolean equals(Object object){
        throw new RuntimeException("Unimplemented.");
    }

    @Override public int hashCode(){
        throw new RuntimeException("Unimplemented.");
    }

    /**
     * @return the name of this note in scientific pitch
     * notation
     */
    @Override public String toString(){
        throw new RuntimeException("Unimplemented.");
    }

}
