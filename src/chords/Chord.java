package chords;

import music.BasicNote;
import music.Key;
import music.Note;

/**
 * An immutable class representing a chord sung by four voices.  
 */
public class Chord {
    
    private final Note soprano;
    private final Note alto;
    private final Note tenor;
    private final Note bass;
    private final BasicChord basicChord;
        
    /*
     * Abstraction Function:
     * represents a chord with soprano, alto, tenor, bass singing these notes
     * basicChord represents this chord modulo octaves
     * 
     * Rep Invariant:
     * soprano, alto, tenor, bass are same notes as corresponding notes in basicChord
     * voices don't cross
     * voices aren't more than an octave apart, except tenor/bass
     * notes are in voice ranges shown below
     * 
     * Rep Exposure:
     * returns only primitives and immutables 
     */
    
    /*
     * Voice ranges:
     * Soprano: D4-G5
     * Alto: A3-D5
     * Tenor: D3-G4
     * Bass: E2-C4
     */
    public static final Note SOPRANO_LOWER = new Note(new BasicNote(1,2), 4);
    public static final Note SOPRANO_UPPER = new Note(new BasicNote(4,7), 5);
    public static final Note ALTO_LOWER = new Note(new BasicNote(5,9), 3);
    public static final Note ALTO_UPPER = new Note(new BasicNote(1,2), 5);
    public static final Note TENOR_LOWER = new Note(new BasicNote(1,2), 3);
    public static final Note TENOR_UPPER = new Note(new BasicNote(4,7), 4);
    public static final Note BASS_LOWER = new Note(new BasicNote(2,4), 2);
    public static final Note BASS_UPPER = new Note(new BasicNote(0,0), 4);
    
    /**
     * Constructs a chord
     * @param soprano soprano note
     * @param alto alto note
     * @param tenor tenor note
     * @param bass bass note
     */
    public Chord(Note soprano, Note alto, Note tenor, Note bass, PrimitiveChord primitiveChord){
        this.soprano = soprano;
        this.alto = alto;
        this.tenor = tenor; 
        this.bass = bass;
        this.basicChord = new BasicChord(
                soprano.getBasicNote(), 
                alto.getBasicNote(), 
                tenor.getBasicNote(),
                bass.getBasicNote(),
                primitiveChord);
        checkRep();
    }
    
    private void checkRep(){
        assert(soprano.getBasicNote().equals(basicChord.getSoprano()));
        assert(alto.getBasicNote().equals(basicChord.getAlto()));
        assert(tenor.getBasicNote().equals(basicChord.getTenor()));
        assert(bass.getBasicNote().equals(basicChord.getBass()));
        
        assert(soprano.getNoteID() >= alto.getNoteID());
        assert(alto.getNoteID() >= tenor.getNoteID());
        assert(tenor.getNoteID() >= bass.getNoteID());
        
        assert(soprano.getNoteID() <= alto.getNoteID() + Key.PITCHES_IN_SCALE);
        assert(alto.getNoteID() <= tenor.getNoteID() + Key.PITCHES_IN_SCALE);
        
        assert(soprano.getNoteID() <= SOPRANO_UPPER.getNoteID());
        assert(soprano.getNoteID() >= SOPRANO_LOWER.getNoteID());
        assert(alto.getNoteID() <= ALTO_UPPER.getNoteID());
        assert(alto.getNoteID() >= ALTO_LOWER.getNoteID());
        assert(tenor.getNoteID() <= TENOR_UPPER.getNoteID());
        assert(tenor.getNoteID() >= TENOR_LOWER.getNoteID());
        assert(bass.getNoteID() <= BASS_UPPER.getNoteID());
        assert(bass.getNoteID() >= BASS_LOWER.getNoteID());
    }
    
    /**
     * Plays this chord
     */
    public void play(/*params here*/){
        throw new RuntimeException("Unimplemented.");
    }
    
    
    /******************
     * Getter methods *
     ******************/
    
    /**
     * @return the soprano note
     */
    public Note getSoprano(){
        return soprano;
    }

    /**
     * @return the alto note
     */
    public Note getAlto(){
        return alto;
    }
    
    /**
     * @return the tenor note
     */
    public Note getTenor(){
        return tenor;
    }
    
    /**
     * @return the bass note
     */
    public Note getBass(){
        return bass;
    }
    
    /**
     * @return the chord's basic chord
     */
    public BasicChord getBasicChord(){
        return basicChord;
    }
    
    /**
     * @return the chord's primitive chord
     */
    public PrimitiveChord getPrimitiveChord(){
        return basicChord.getPrimitiveChord();
    }

    /**
     * @return the chord's type
     */
    public ChordType getType(){
        return basicChord.getPrimitiveChord().getType();
    }

    
    /***************
     * Computation *
     ***************/

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
     * Two chords are equal iff all corresponding notes are equal.    
     * @param object another object
     * @return whether this and object are equal.   
     */
    @Override public boolean equals(Object object){
        if (!(object instanceof Chord)){return false;}
        Chord that = (Chord) object;
        return (this.soprano.equals(that.soprano) &&
                this.alto.equals(that.alto) &&
                this.tenor.equals(that.tenor) &&
                this.bass.equals(that.bass) &&
                this.basicChord.equals(that.basicChord));
    }

    @Override public int hashCode(){
        return soprano.hashCode() + alto.hashCode() +
                tenor.hashCode() + bass.hashCode() +
                basicChord.hashCode();
    }

    /**
     * @return the notes of this chord in scientific pitch
     * notation
     */
    @Override public String toString(){
        return "["+soprano.toString()+"|"+alto.toString()+
                "|"+tenor.toString()+"|"+bass.toString()+"]";
    }
}
