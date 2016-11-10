package chords;

/**
 * An immutable type representing an empty chord progression
 */
public class EmptyChordProgression implements ChordProgression {

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
    
    public EmptyChordProgression(){
    }
    
    /******************************
     * ChordProgression interface *
     ******************************/
    
    @Override
    public ChordProgression getStart() {
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
    public ChordProgression append(Chord chord) {
        return new ConsChordProgression(this, chord);
    }

    
    /*******************
     * Object Contract *
     *******************/

    @Override
    public boolean equals (Object other){
        if (!(other instanceof EmptyChordProgression)){return false;}
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
