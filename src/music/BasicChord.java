package music;

/**
 * An immutable class representing a chord sung by four voices, modulo octaves  
 */
public class BasicChord {
    // TODO methods not complete 
    
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
    public BasicChord(BasicNote soprano, BasicNote alto, BasicNote tenor, BasicNote bass){
        throw new RuntimeException("Unimplemented");
    }
    
    private void checkRep(){
        throw new RuntimeException("Unimplemented");
    }
    
    /******************
     * Getter methods *
     ******************/
    
    /**
     * @return the soprano note
     */
    public BasicNote getSoprano(){
        throw new RuntimeException("Unimplemented");
    }

    /**
     * @return the alto note
     */
    public BasicNote getAlto(){
        throw new RuntimeException("Unimplemented");
    }
    
    /**
     * @return the tenor note
     */
    public BasicNote getTenor(){
        throw new RuntimeException("Unimplemented");
    }
    
    /**
     * @return the bass note
     */
    public BasicNote getBass(){
        throw new RuntimeException("Unimplemented");
    }
    
    /*******************
     * Object Contract *
     *******************/
    
    /**
     * Two BasicChords are equal iff all corresponding notes are equal.    
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
