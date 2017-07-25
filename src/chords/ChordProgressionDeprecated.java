package chords;

/**
 * An interface representing an immutable sequence of chords
 */
public interface ChordProgressionDeprecated {
    
     /**
     * @return the chordProgression of all chords in this one except
     * the last one
     */
    public ChordProgressionDeprecated getStart();

    /**
     * @return the last chord of this ChordProgression
     */
    public Chord getLast();
    
    /**
     * @return the length of this ChordProgression
     */
    public int length();
    
    /**
     * @param chord a Chord
     * @return a ChordProgression representing chord appended to this
     */
    public ChordProgressionDeprecated append(Chord chord);
    
    /**
     * @return a ChordProgression representing an empty progression
     */
    public static ChordProgressionDeprecated empty(){
        return new EmptyChordProgressionDeprecated();
    }
}
