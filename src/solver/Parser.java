package solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chords.ChordType;
import chords.PrimitiveChord;
import music.BasicInterval;
import music.BasicNote;
import music.Key;

/**
 * A module that takes a string input and returns a list of primitive chords
 */
class Parser {
    private static final String CHORD_REGEX = "[A-G][+|-]?"
            + "(((MajT|MinT)[0-2])|((DomS|DimS|MajS|MinS)[0-3]))";
    private static final String TAG_REGEX = "App|Cad";
    private static final String KEY_REGEX = "KEY:[A-G][+|-]?(Maj|Min)";
    
    private static final String CHORD_OPTIONAL_TAG_REGEX = CHORD_REGEX 
            + "(" + TAG_REGEX + ")?";
    private static final String MASTER_REGEX = 
            "(" + CHORD_OPTIONAL_TAG_REGEX + ")|(" + KEY_REGEX + ")";  
    
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
    
    private static final Map<String, ContextTag> CONTEXT_TAGS;
    static{
        Map<String, ContextTag> tmpContextTags = new HashMap<>();
        tmpContextTags.put("App", ContextTag.APPLIED_DOMINANT);
        tmpContextTags.put("Cad", ContextTag.CADENCE);
        CONTEXT_TAGS = Collections.unmodifiableMap(tmpContextTags);
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
            root = strChord.substring(0, 2);
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

    private static Key parseKey(String strKey){
        assert(strKey.matches(KEY_REGEX));
        String tonic;
        String tonality;
        if (strKey.substring(5,6).matches("[+|-]")){
            tonic = strKey.substring(4,6);
            tonality = strKey.substring(6);
        }
        else{
            tonic = strKey.substring(4,5);
            tonality = strKey.substring(5);    
        }
        assert(tonality.length() == 3);
        BasicNote tonicNote = parseNote(tonic);
        if (tonality.equals("MAJ")){
            return new Key(tonicNote, true);
        }
        else if (tonality.equals("MIN")){
                return new Key(tonicNote, false);
        }
        else{
            throw new RuntimeException("Should not get here");
        }
    }
    
    private static ParseResult parseTokens(List<String> tokens){
        List<PrimitiveChord> primitiveChords = new ArrayList<>();
        List<Key> keys = new ArrayList<>();
        List<ContextTag> contextTags = new ArrayList<>();
        
        assert(tokens.get(0).matches(KEY_REGEX)); 
        Key currentKey = null; // this is ok because first token will set key
        
        for (String token : tokens){
            if (token.matches(KEY_REGEX)){
                currentKey = parseKey(token);
            }
            else{
                // TODO
            }
        }
        throw new RuntimeException("Unimplemented");
    }
    
    public static ParseResult parse(String input){
        String[] inputTokenized = input.split(" ");
        List<String> tokens = new ArrayList<>();
        for (String token : inputTokenized){
            if (!input.matches(MASTER_REGEX)){
                continue;
            }
            tokens.add(token);
        }
        assert(tokens.get(0).matches(KEY_REGEX));
        return parseTokens(tokens);
    }

}

class ParseResult{
    private final List<PrimitiveChord> primitiveChords;
    private final List<Key> keys;
    private final List<ContextTag> contextTags;
    
    public ParseResult(List<PrimitiveChord> primitiveChords, 
            List<Key> keys,
            List<ContextTag> contextTags){
        this.primitiveChords = primitiveChords;
        this.keys = keys;
        this.contextTags = contextTags;
    }
    
    public List<PrimitiveChord> getPrimitiveChords(){
        return Collections.unmodifiableList(primitiveChords);
    }
    
    public List<Key> getKeys(){
        return Collections.unmodifiableList(keys);
    }
    
    public List<ContextTag> getContextTags(){
        return Collections.unmodifiableList(contextTags);
    }
}
