package chord_data;

/**
 * An interface representing an immutable sequence of chords with contexts
 */
public interface ChordProgressionWithContext {
    /**
    * @return the chordProgression of all chords in this one except
    * the last one
    */
   public ChordProgressionWithContext getStart();

   /**
    * @return the last chord of this ChordProgression
    */
   public ChordWithContext getLast();
   
   /**
    * @return the length of this ChordProgression
    */
   public int length();
   
   /**
    * @param chord a ChordWithContext
    * @return a ChordProgression representing chord appended to this
    */
   public ChordProgressionWithContext append(ChordWithContext chord);
   
   /**
    * @return a ChordProgression representing an empty progression
    */
   public static ChordProgressionWithContext empty(){
       return new EmptyChordProgressionWithContext();
   }
}
