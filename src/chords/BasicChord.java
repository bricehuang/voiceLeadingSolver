package chords;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import music.*;

/**
 * An immutable class representing a chord sung by four voices, modulo octaves  
 */
public class BasicChord {
    private final BasicNote soprano;
    private final BasicNote alto;
    private final BasicNote tenor;
    private final BasicNote bass;
    private final PrimitiveChord primitiveChord; 
    
    /*
     * Abstraction Function:
     * soprano, alto, tenor, bass sing BasicNotes soprano, alto, tenor, bass
     * primitiveChord stores the primitive chord class this chord belongs to
     * 
     * Rep Invariant:
     * soprano, alto, tenor, bass are notes in primitiveChord's noteset
     * major, minor chords must have root, 3rd, can be missing 5th
     * seventh chords should have all notes
     * 
     * Rep Exposure:
     * returns primitives and immutables
     */
    
    /**
     * Constructs a chord
     * @param soprano soprano note
     * @param alto alto note
     * @param tenor tenor note
     * @param bass bass note
     * @param primitiveChord the primitiveChord type of this note
     */
    public BasicChord(BasicNote soprano, BasicNote alto, BasicNote tenor, BasicNote bass,
            PrimitiveChord primitiveChord){
        this.soprano = soprano;
        this.alto = alto;
        this.tenor = tenor;
        this.bass = bass;
        this.primitiveChord = primitiveChord;
        checkRep();
    }
    
    private void checkRep(){
        List<BasicNote> noteList = primitiveChord.noteList();
        assert(bass.equals(noteList.get(primitiveChord.getInversion())));
        
        Set<BasicNote> notesInChord = new HashSet<>(Arrays.asList(
                soprano, alto, tenor, bass));
        
        // MAGIC NUMBER: 3 note chords vs 4 note chords
        if (primitiveChord.numberDistinctNotes() == 4){
            // all notes required
            assert(noteList.containsAll(notesInChord));
            assert(notesInChord.containsAll(noteList));
        }
        else{
            // 5th not strictly required
            Set<BasicNote> requiredNotes = new HashSet<>(Arrays.asList(
                    noteList.get(0), noteList.get(1)));
            assert(noteList.containsAll(notesInChord));
            assert(notesInChord.containsAll(requiredNotes));
        }
    }
    
    /******************
     * Getter methods *
     ******************/
    
    /**
     * @return the soprano note
     */
    public BasicNote getSoprano(){
        return soprano;
    }

    /**
     * @return the alto note
     */
    public BasicNote getAlto(){
        return alto;
    }
    
    /**
     * @return the tenor note
     */
    public BasicNote getTenor(){
        return tenor;
    }
    
    /**
     * @return the bass note
     */
    public BasicNote getBass(){
        return bass;
    }
        
    /**
     * @return the chord's primitive chord 
     */
    public PrimitiveChord getPrimitiveChord(){
        return primitiveChord;
    }

    /**
     * @return the chord type
     */
    public ChordType getType(){
        return primitiveChord.getType();
    }

    /****************
     * Computations *
     ****************/
    
    /**
     * @param key 
     * @return the notes of this chord, rendered in given key 
     */
    public String renderInKey(Key key){
        return "["+soprano.renderInKey(key)+"|"+alto.renderInKey(key)+
                "|"+tenor.renderInKey(key)+"|"+bass.renderInKey(key)+"]";
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
        if (!(object instanceof BasicChord)){return false;}
        BasicChord that = (BasicChord) object;
        return (this.soprano.equals(that.soprano) &&
                this.alto.equals(that.alto) &&
                this.tenor.equals(that.tenor) &&
                this.bass.equals(that.bass) &&
                this.primitiveChord.equals(that.primitiveChord));
    }

    @Override public int hashCode(){
        return soprano.hashCode() + alto.hashCode() + 
                tenor.hashCode() + bass.hashCode() + 
                primitiveChord.hashCode();
    }

    /**
     * @return the notes of this chord 
     */
    @Override public String toString(){
        return "["+soprano.toString()+"|"+alto.toString()+
                "|"+tenor.toString()+"|"+bass.toString()+"]";
    }

}
