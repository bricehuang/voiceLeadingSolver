package music;

/**
 * An immutable class representing a chord sung by four voices.  
 */
public class Chord {
    /*
     * Voice ranges:
     * Soprano: D4-G5
     * Alto: A3-D5
     * Tenor: D3-F4
     * Bass: F2-C4
     */
    
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
     * Constructs a chord
     * @param soprano soprano note
     * @param alto alto note
     * @param tenor tenor note
     * @param bass bass note
     */
    public Chord(Note soprano, Note alto, Note tenor, Note bass, Key key){
        throw new RuntimeException("Unimplemented");
    }
    
    private void checkRep(){
        throw new RuntimeException("Unimplemented");
    }
    
    /**
     * @return the soprano note
     */
    public Note getSoprano(){
        throw new RuntimeException("Unimplemented");
    }

    /**
     * @return the alto note
     */
    public Note getAlto(){
        throw new RuntimeException("Unimplemented");
    }
    
    /**
     * @return the tenor note
     */
    public Note getTenor(){
        throw new RuntimeException("Unimplemented");
    }
    
    /**
     * @return the bass note
     */
    public Note getBass(){
        throw new RuntimeException("Unimplemented");
    }
    
    /**
     * @return the key in which this note is played
     */
    public Key getKey(){
        throw new RuntimeException("Unimplemented");
    }
    
    /**
     * Plays this chord
     */
    public void play(/*params here*/){
        throw new RuntimeException("Unimplemented.");
    }
    
    /**
     * Two chords are equal iff all corresponding notes are equal.    
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
     * @return the notes of this chord in scientific pitch
     * notation
     */
    @Override public String toString(){
        throw new RuntimeException("Unimplemented.");
    }
}
