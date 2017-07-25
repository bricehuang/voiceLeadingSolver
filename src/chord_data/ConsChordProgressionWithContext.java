package chord_data;

/**
 * An immutable type representing a nonempty chord progression
 */
public class ConsChordProgressionWithContext implements ChordProgressionWithContext {
    private final ChordProgressionWithContext start;
    private final ChordWithContext last;
    private final int length;
    
    /*
     * Abstraction Function:
     * Represents a nonempty chord progression with last chord last
     * and remaining chords start
     * 
     * Rep Invariant: 
     * N/A
     * 
     * Rep Exposure:
     * Only returns immutables
     */
    
    public ConsChordProgressionWithContext(ChordProgressionWithContext start, ChordWithContext last){
        this.start = start;
        this.last = last;
        this.length = start.length() + 1;
    }

    /******************************
     * ChordProgression interface *
     ******************************/

    @Override
    public ChordProgressionWithContext getStart() {
        return start;
    }

    @Override
    public ChordWithContext getLast() {
        return last;
    }

    @Override
    public int length() {
        return length;
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
        if (!(other instanceof ConsChordProgressionWithContext)){return false;}
        ConsChordProgressionWithContext that = (ConsChordProgressionWithContext) other;
        return (this.start.equals(that.start) && this.last.equals(that.last));
    }
    
    @Override
    public int hashCode(){
        return start.hashCode() + last.hashCode();
    }
    
    @Override
    public String toString(){
        return start.toString() + "\n" + last.toString();
    }


    

}
