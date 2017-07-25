package chord_data;

import java.util.Collections;
import java.util.Set;

import chords.Chord;

/**
 * An immutable class representing a chord sung by four voices, with any 
 * contexts attached.    
 */
public class ChordWithContext {
	private final Chord chord;
	private final Set<ContextTag> contextTags;
	
	public ChordWithContext(Chord chord, Set<ContextTag> contextTags) {
		this.chord = chord;
		this.contextTags = Collections.unmodifiableSet(contextTags);
	}
	
    /*******************
     *     Getters     *
     *******************/	
	
	/**
	 * @return the chord 
	 */
	public Chord getChord() {
		return chord;
	}

	/**
	 * @return the context tags associated with this chord
	 */
	public Set<ContextTag> getContextTags() {
		return contextTags;
	}
	
    /*******************
     * Object Contract *
     *******************/

    /**
     * Two chordsWithContexts are equal iff all corresponding notes are equal.    
     * @param object another object
     * @return whether this and object are equal.   
     */
    @Override public boolean equals(Object object){
        if (!(object instanceof Chord)){return false;}
        ChordWithContext that = (ChordWithContext) object;
        return this.chord.equals(that.chord) 
        		&& this.contextTags.size()==that.contextTags.size()
        		&& this.contextTags.containsAll(that.contextTags);
    }

    @Override public int hashCode(){
    		return chord.hashCode();
    }

    /**
     * @return the notes of this chord in scientific pitch
     * notation
     */
    @Override public String toString(){
        return chord.toString() + " " + contextTags.toString();
    }

}
