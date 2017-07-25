package chords;

/**
 * An immutable type representing an empty chord progression
 */
public class EmptyChordProgressionDeprecated implements ChordProgressionDeprecated {

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
    
    public EmptyChordProgressionDeprecated(){
    }
    
    /******************************
     * ChordProgression interface *
     ******************************/
    
    @Override
    public ChordProgressionDeprecated getStart() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Chord getLast() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int length() {
        return 0;
    }

    @Override
    public ChordProgressionDeprecated append(Chord chord) {
        return new ConsChordProgressionDeprecated(this, chord);
    }

    
    /*******************
     * Object Contract *
     *******************/

    @Override
    public boolean equals (Object other){
        if (!(other instanceof EmptyChordProgressionDeprecated)){return false;}
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
