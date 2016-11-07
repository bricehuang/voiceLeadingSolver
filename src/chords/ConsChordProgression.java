package chords;

import javax.print.attribute.standard.MediaSize.Other;

/**
 * An immutable type representing a nonempty chord progression
 */
public class ConsChordProgression implements ChordProgression {
    private final ChordProgression start;
    private final Chord last;
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
    
    public ConsChordProgression(ChordProgression start, Chord last){
        this.start = start;
        this.last = last;
        this.length = start.length() + 1;
    }

    /******************************
     * ChordProgression interface *
     ******************************/

    @Override
    public ChordProgression getStart() {
        return start;
    }

    @Override
    public Chord getLast() {
        return last;
    }

    @Override
    public int length() {
        return length;
    }

    /*******************
     * Object Contract *
     *******************/

    @Override
    public boolean equals (Object other){
        if (!(other instanceof ConsChordProgression)){return false;}
        ConsChordProgression that = (ConsChordProgression) other;
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
