package chords;

/**
 * An interface representing an immutable sequence of chords
 */
public interface ChordProgression {
    
     /**
     * @return the chordProgression of all chords in this one except
     * the last one
     */
    public ChordProgression getStart();

    /**
     * @return the last chord of this ChordProgression
     */
    public Chord getLast();
    
    /**
     * @return the length of this ChordProgression
     */
    public int length();
    
    /**
     * @return a ChordProgression representing an empty progression
     */
    public static ChordProgression empty(){
        return new EmptyChordProgression();
    }

    /**
     * @param start a ChordProgression
     * @param last a Chord
     * @return a ChordProgression representing last appended to start
     */
    public static ChordProgression append(ChordProgression start, 
            Chord last){
        return new ConsChordProgression(start, last);
    }
}
