package chord_data;

import java.util.Collections;
import java.util.Set;

import chords.PrimitiveChord;
import music.Key;

public class PrimitiveChordWithContext {
	private final PrimitiveChord primitiveChord;
	private final Key key;
	private final Set<ContextTag> contextTags;
	
	public PrimitiveChordWithContext(PrimitiveChord primitiveChord, Key key, Set<ContextTag> contextTags) {
		this.primitiveChord = primitiveChord;
		this.key = key;
		this.contextTags = Collections.unmodifiableSet(contextTags);
	}
	
    /*******************
     *     Getters     *
     *******************/	
	
	/**
	 * @return the primitiveChord 
	 */
	public PrimitiveChord getChord() {
		return primitiveChord;
	}
	
	/**
	 * @return the surrounding key
	 */
	public Key getKey() {
		return key;
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
        if (!(object instanceof PrimitiveChord)){return false;}
        PrimitiveChordWithContext that = (PrimitiveChordWithContext) object;
        return this.primitiveChord.equals(that.primitiveChord) 
    		&& this.key.equals(that.key)
    		&& this.contextTags.size()==that.contextTags.size()
    		&& this.contextTags.containsAll(that.contextTags);
    }

    @Override public int hashCode(){
		return primitiveChord.hashCode() + key.hashCode();
    }

    /**
     * @return a string representation of the chord, keys, and context tags
     */
    @Override public String toString(){
        return primitiveChord.toString() 
    		+ "||" + key.toString() 
    		+ "|| " + contextTags.toString();
    }

}
