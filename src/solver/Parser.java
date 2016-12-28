package solver;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import chords.ChordType;
import chords.PrimitiveChord;
import music.BasicInterval;
import music.BasicNote;

/**
 * A module that takes a string input and returns a list of primitive chords
 */
class Parser {
    private static final String CHORD_REGEX = "[A-G][+|-]?"
            + "(((MajT|MinT)[0-2])|((DomS|DimS|MajS|MinS)[0-3]))";
    
    private static final Map<Character, BasicNote> NOTES;
    static{
        Map<Character, BasicNote> tmpNotes = new HashMap<>();
        tmpNotes.put('C',new BasicNote(0,0));
        tmpNotes.put('D',new BasicNote(1,2));
        tmpNotes.put('E',new BasicNote(2,4));
        tmpNotes.put('F',new BasicNote(3,5));
        tmpNotes.put('G',new BasicNote(4,7));
        tmpNotes.put('A',new BasicNote(5,9));
        tmpNotes.put('B',new BasicNote(6,11));
        NOTES = Collections.unmodifiableMap(tmpNotes);
    }
    private static final BasicInterval SEMITONE = new BasicInterval(0,1);
    
    private static final Map<String, ChordType> CHORD_TYPES;
    static{
        Map<String, ChordType> tmpChordTypes = new HashMap<>();
        tmpChordTypes.put("MajT", ChordType.MAJ);
        tmpChordTypes.put("MinT", ChordType.MIN);
        tmpChordTypes.put("DomS", ChordType.DOM7);
        tmpChordTypes.put("DimS", ChordType.DIM7);
        tmpChordTypes.put("MajS", ChordType.MAJ7);
        tmpChordTypes.put("MinS", ChordType.MIN7);
        CHORD_TYPES = Collections.unmodifiableMap(tmpChordTypes);
    }
    
    private static BasicNote parseNote(String strNote){
        BasicNote baseNote = NOTES.get(strNote.charAt(0));
        if (strNote.length() == 2){
            if (strNote.charAt(1) == '+'){
                baseNote = baseNote.transpose(SEMITONE, true);
            }
            else if (strNote.charAt(1) == '-'){
                baseNote = baseNote.transpose(SEMITONE, false);
            }
            else{
                throw new RuntimeException("Should not get here.");
            }
        }
        return baseNote;
    }
    
    private static PrimitiveChord parseChord(String strChord){
        assert(strChord.matches(CHORD_REGEX));
        String root;
        String spec;
        if (strChord.substring(1,2).matches("[+|-]")){
            root = strChord.substring(0, 1);
            spec = strChord.substring(2);
        }
        else{
            root = strChord.substring(0, 1);
            spec = strChord.substring(1);
        }
        assert(spec.length()==4);
        String specType = spec.substring(0, 3);
        String specInversion = spec.substring(4);
        BasicNote rootType = parseNote(root);
        ChordType chordType = CHORD_TYPES.get(specType);
        Integer inversion = Integer.valueOf(specInversion);
        return new PrimitiveChord(rootType, chordType, inversion);
    }
    
    // TODO
            
}