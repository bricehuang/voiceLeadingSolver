package chord_data;

/**
 * An immutable type representing an empty chord progression
 */
public class EmptyChordProgressionWithContext implements ChordProgressionWithContext {

    /*
     * Abstraction Function:
     * Represents an empty chord progression
     * 
     * Rep Invariant: 
     * N/A
     * 
     * Rep Exposure:
     * N/A
     */
    
    public EmptyChordProgressionWithContext(){
    }
    
    /******************************
     * ChordProgression interface *
     ******************************/
    
    @Override
    public ChordProgressionWithContext getStart() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ChordWithContext getLast() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int length() {
        return 0;
    }

    @Override
    public ChordProgressionWithContext append(ChordWithContext chord) {
        return new ConsChordProgressionWithContext(this, chord);
    }

    
    /*******************
     * Object Contract *
     *******************/

    @Override
    public boolean equals (Object other){
        if (!(other instanceof EmptyChordProgressionWithContext)){return false;}
        return true;
    }
    
    @Override
    public int hashCode(){
        return 31;
    }
    
    @Override
    public String toString(){
        return "";
    }
    
}
