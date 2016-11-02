package music;

/**
 * An immutable type representing a chord 
 */
public class PrimitiveChord {
    private final BasicNote root;
    private final ChordType type;
    private final int inversion;
    
    /*
     * Abstraction Function:
     * Represents a chord with root root, chord type type, and inversion inversion
     * 
     * Rep Invariant:
     * 0<= inversion < number of distinct notes in chord
     *  
     * Rep Exposure:
     * returns primitives and immutables only
     * 
     */
    
    /**
     * Constructor for PrimitiveChords
     * @param root the root
     * @param type the type of this chord
     * @param inversion the inversion
     */
    public PrimitiveChord(BasicNote root, ChordType type, int inversion){
        this.root = root;
        this.type = type;
        this.inversion = inversion;
        checkRep();
    }
    
    private void checkRep(){
        assert(inversion >= 0);
        assert(inversion < type.numberDistinctNotes());
    }
    
    /******************
     * Getter methods *
     ******************/
    
    public BasicNote getRoot(){
        return root;
    }

    public ChordType getType(){
        return type;
    }
    
    public int getInversion(){
        return inversion;
    }

    
    /*******************
     * Object Contract *
     *******************/
    
    /**
     * Two PrimitiveChords are equal iff they have the same 
     * root, type, and inversion
     * @param object another object
     * @return whether this and object are equal.   
     */
    @Override public boolean equals(Object object){
        if (!(object instanceof PrimitiveChord)){ return false; }
        PrimitiveChord that = (PrimitiveChord) object;
        return (this.root.equals(that.root) 
                && this.type.equals(that.type)
                && this.inversion == that.inversion);
    }

    @Override public int hashCode(){
        return this.root.hashCode() + this.type.hashCode() + 241 * this.inversion;
    }

    /**
     * String representation of this note
     * @return the name of this note, as written in C Major
     */
    @Override public String toString(){
        return root.toString() + " " + type.toString() + " INVERSION " + inversion;
    }


}
