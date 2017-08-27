package solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import chord_data.ChordWithContext;
import chord_data.PrimitiveChordWithContext;
import chords.BasicChord;
import chords.Chord;
import chords.ChordType;
import chords.PrimitiveChord;
import music.BasicNote;
import music.Key;
import music.Note;



/**
 * A module that takes a list of primitive chords and outputs 
 * ways each chord can be sung   
 */
class ChordGenerator {
    
    /**
     * Returns whether four notes are a valid BasicChord
     * @param soprano a basicNote
     * @param alto a basicNote
     * @param tenor a basicNote
     * @param bass a basicNote
     * @param noteList the list of notes in this chord, spelled
     * from root up
     * @return whether these notes make a valid BasicChord -- they should
     * all be in noteList.  If noteList has four distinct notes all four 
     * must be present; if it has three distinct notes the root and third 
     * must be present, but the fifth may be absent.    
     */
    private static boolean isValidBasicChord(BasicNote soprano, BasicNote alto, 
            BasicNote tenor, BasicNote bass, PrimitiveChord primitive){
        List<BasicNote> noteList = primitive.noteList();
        Set<BasicNote> notesInChord = new HashSet<>(Arrays.asList(
                soprano, alto, tenor, bass));
        
        // MAGIC NUMBER: 3 note chords vs 4 note chords
        if (
            primitive.numberDistinctNotes() == 4 || 
            primitive.getType() == ChordType.DIM
        ){
            // all notes required
            if (
                (noteList.containsAll(notesInChord)) &&
                (notesInChord.containsAll(noteList)) 
            ){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            // 5th not strictly required
            Set<BasicNote> requiredNotes = new HashSet<>(Arrays.asList(
                    noteList.get(0), noteList.get(1)));
            if (
                (noteList.containsAll(notesInChord)) &&
                (notesInChord.containsAll(requiredNotes))
            ){
                return true;
            }
            else{
                return false;
            }
        }
    }
    
    /**
     * @param primitive a PrimitiveChord
     * @return a set of BasicChords corresponding to the ways
     * the notes of this PrimitiveChord can be assigned
     */
    private static Set<BasicChord> primitiveToBasicChord(
            PrimitiveChord primitive){
        Set<BasicChord> validNoteAssignments = new HashSet<>();
        BasicNote bass = primitive.noteList().get(primitive.getInversion());
        for (BasicNote soprano : primitive.noteList()){
            for (BasicNote alto : primitive.noteList()){
                for (BasicNote tenor : primitive.noteList()){
                    if (isValidBasicChord(soprano, alto, tenor, bass, primitive)){
                        validNoteAssignments.add(new BasicChord(
                                soprano, alto, tenor, bass, primitive));
                    }
                }
            }
        }
        return Collections.unmodifiableSet(validNoteAssignments);
    }
    
    /**
     * Returns whether four Notes are a valid voicing of a chord
     * @param soprano a note
     * @param alto a note
     * @param tenor a note
     * @param bass a note
     * @return true if notes are within their respective voice ranges, 
     * voices do not cross, soprano/alto, alto/tenor are within octaves
     * of each other
     */
    private static boolean isValidVoicing(Note soprano, Note alto, 
            Note tenor, Note bass){
        if(
            (soprano.getNoteID() >= alto.getNoteID()) &&
            (alto.getNoteID() >= tenor.getNoteID()) &&
            (tenor.getNoteID() >= bass.getNoteID()) &&
            
            (soprano.getNoteID() <= alto.getNoteID() + Key.PITCHES_IN_SCALE) &&
            (alto.getNoteID() <= tenor.getNoteID() + Key.PITCHES_IN_SCALE) &&
            
            (soprano.getNoteID() <= Chord.SOPRANO_UPPER.getNoteID()) &&
            (soprano.getNoteID() >= Chord.SOPRANO_LOWER.getNoteID()) &&
            (alto.getNoteID() <= Chord.ALTO_UPPER.getNoteID()) &&
            (alto.getNoteID() >= Chord.ALTO_LOWER.getNoteID()) &&
            (tenor.getNoteID() <= Chord.TENOR_UPPER.getNoteID()) &&
            (tenor.getNoteID() >= Chord.TENOR_LOWER.getNoteID()) &&
            (bass.getNoteID() <= Chord.BASS_UPPER.getNoteID()) &&
            (bass.getNoteID() >= Chord.BASS_LOWER.getNoteID())
        ){
            return true;
        }
        return false;
    }
    
    /**
     * @param basic a BasicChord
     * @return a set of Chords representing the ways this 
     * BasicChord can be sung by four voices
     */
    private static Set<Chord> basicChordToChord(BasicChord basic){
        Set<Chord> validWaysToSing = new HashSet<>();
        for (int sopranoOctave = Chord.SOPRANO_LOWER.getOctave();
                sopranoOctave <= Chord.SOPRANO_UPPER.getOctave(); 
                sopranoOctave++){
            for (int altoOctave = Chord.ALTO_LOWER.getOctave();
                    altoOctave <= Chord.ALTO_UPPER.getOctave(); 
                    altoOctave++){
                for (int tenorOctave = Chord.TENOR_LOWER.getOctave();
                        tenorOctave <= Chord.TENOR_UPPER.getOctave(); 
                        tenorOctave++){
                    for (int bassOctave = Chord.BASS_LOWER.getOctave();
                            bassOctave <= Chord.BASS_UPPER.getOctave(); 
                            bassOctave++){
                        Note soprano = new Note(basic.getSoprano(), sopranoOctave);
                        Note alto = new Note(basic.getAlto(), altoOctave);
                        Note tenor = new Note(basic.getTenor(), tenorOctave);
                        Note bass = new Note(basic.getBass(), bassOctave);
                        if (isValidVoicing(soprano, alto, tenor, bass)){
                            validWaysToSing.add(new Chord(
                                    soprano, alto, tenor, bass, 
                                    basic.getPrimitiveChord()));
                        }
                    }            
                }            
            }            
        }
        return Collections.unmodifiableSet(validWaysToSing);
    }
    
    /**
     * @param primitive a PrimitiveChord
     * @return an unmodifiable set of Chords corresponding to the 
     * ways this PrimitiveChord can be sung
     */
    private static Set<Chord> primitiveToChords(PrimitiveChord primitive){
        Set<BasicChord> noteAssignments = primitiveToBasicChord(primitive);
        Set<Chord> waysToSingThisChord = new HashSet<>();
        for (BasicChord noteAssignment : noteAssignments){
            waysToSingThisChord.addAll(basicChordToChord(noteAssignment));
        }
        return Collections.unmodifiableSet(waysToSingThisChord);
    }
    
    /**
     * @param primitiveAndContext
     * @return an unmodifiable set of ChordWithContexts corresponding to 
     * valid voicings of this chord, with contexts
     */
    private static Set<ChordWithContext> generateChordWithContextSinglePrimitive(
    		PrimitiveChordWithContext primitiveAndContext){
		Set<ChordWithContext> voicingsWithContext = new HashSet<>();
		for (Chord voicing : primitiveToChords(primitiveAndContext.getChord())){
		    voicingsWithContext.add(
				new ChordWithContext(
			        voicing, 
			        primitiveAndContext.getKey(), 
			        primitiveAndContext.getContextTags()
				)
			);
		}
		return Collections.unmodifiableSet(voicingsWithContext);
    }
    
    /**
     * Transforms each PromitiveChordWithContext into the corresponding set of 
     * all valid voicings of it, attached with the same contexts.    
     * @param inputChords a PrimitiveChordWithContext
     * @return a list, where each element is a Set<ChordWithContext> containing 
     * all valid voicings of the corresponding PrimitiveChordWithContext in the input.  
     */
    public static List<Set<ChordWithContext>> generateChordsWithContext(
            List<PrimitiveChordWithContext> inputChords){
		List<Set<ChordWithContext>> voicingsWithContext = new ArrayList<>();
		for (PrimitiveChordWithContext primitiveAndContext: inputChords) {
			voicingsWithContext.add(
				generateChordWithContextSinglePrimitive(primitiveAndContext)
			);
		}
		return Collections.unmodifiableList(voicingsWithContext);
    }

}
