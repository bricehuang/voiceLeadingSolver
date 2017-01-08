package solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    private static final String TAG_REGEX = "App|Cad|Nea";
    private static final String KEY_REGEX = "KEY:[A-G][+|-]?(Maj|Min)";
    
    private static final String CHORD_TAGS_REGEX = CHORD_REGEX 
            + "(" + TAG_REGEX + ")*";
    private static final String MASTER_REGEX = 
            "(" + CHORD_TAGS_REGEX + ")|(" + KEY_REGEX + ")";  
    
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
    
    // all tags should be 3 characters long
    private static final Map<String, ContextTag> CONTEXT_TAGS;
    static{
        Map<String, ContextTag> tmpContextTags = new HashMap<>();
        tmpContextTags.put("App", ContextTag.APPLIED_DOMINANT);
        tmpContextTags.put("Cad", ContextTag.CADENCE);
        tmpContextTags.put("Nea", ContextTag.NEAPOLITAN);
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
        assert(spec.length()==5);
        String specType = spec.substring(0, 4);
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
        if (tonality.equals("Maj")){
            return new Key(tonicNote, true);
        }
        else if (tonality.equals("Min")){
            return new Key(tonicNote, false);
        }
        else{
            throw new RuntimeException("Should not get here");
        }
    }
    
    private static ParseResult parseTokensPrelim(List<String> tokens){
        List<PrimitiveChord> primitiveChords = new ArrayList<>();
        List<Key> keys = new ArrayList<>();
        List<Set<ContextTag>> contextTags = new ArrayList<>();
        
        assert(tokens.get(0).matches(KEY_REGEX)); 
        Key currentKey = null; // this is ok because first token will set key
        
        for (String token : tokens){
            if (token.matches(KEY_REGEX)){
                currentKey = parseKey(token);
            }
            else{
                Set<ContextTag> tags = new HashSet<>();
                String tmpToken = new String(token);
                while(!tmpToken.matches(CHORD_REGEX)){
                    String tag = tmpToken.substring(tmpToken.length()-3);
                    assert(tag.matches(TAG_REGEX));
                    tags.add(CONTEXT_TAGS.get(tag));
                    tmpToken = tmpToken.substring(0, tmpToken.length()-3);
                }
                PrimitiveChord primitiveChord = parseChord(tmpToken);
                primitiveChords.add(primitiveChord);
                keys.add(currentKey);
                contextTags.add(tags);
            }
        }
        return new ParseResult(primitiveChords, keys, contextTags);
    }
    
    /**
     * Note: contextTags is contextTags before postprocessing
     */
    private static boolean isPerfectCadence(PrimitiveChord chord, Key key, Set<ContextTag> contextTags){
        if (contextTags.contains(ContextTag.CADENCE)){
            if (chord.getRoot().equals(key.getTonic()) && chord.getInversion() == 0){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
    
    private static boolean isCadentialDominant(PrimitiveChord chord, Key key){
        Set<ChordType> dominantChordTypes = new HashSet<>(Arrays.asList(
                ChordType.MAJ, ChordType.DOM7));
        if (dominantChordTypes.contains(chord.getType())){
            if (chord.getRoot().equals(key.getScaleDegree(5)) 
                    && chord.getInversion() == 0){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;            
        }
    }
    
    private static boolean isCadentialI64(PrimitiveChord chord, Key key){
        if (chord.getRoot().equals(key.getTonic()) 
                && chord.getInversion() == 2){
            return true;
        }
        else{
            return false;
        }
    }

    private static boolean isCadentialPredominant(PrimitiveChord chord, Key key, 
            Set<ContextTag> contextTags){
        // TODO: applied dominant, aug6, diminished??
        if (chord.getRoot().equals(key.getScaleDegree(2))){
            Set<ChordType> predominant2ChordTypes = new HashSet<>(
                    Arrays.asList(ChordType.MIN, ChordType.MIN7)); 
            if (predominant2ChordTypes.contains(chord.getType())){
                return true;
            }
            else{
                return false;
            }
        }
        else if (chord.getRoot().equals(key.getScaleDegree(4))){
            Set<ChordType> predominant4ChordTypes = new HashSet<>(
                    Arrays.asList(ChordType.MAJ, ChordType.MIN));
            if (predominant4ChordTypes.contains(chord.getType())){
                return true;
            }
            else{
                return false;
            }
        }
        else if (chord.getRoot().equals(key.getScaleDegree(2).transpose(SEMITONE, false))){
            if (contextTags.contains(ContextTag.NEAPOLITAN) 
                    && chord.getType().equals(ChordType.MAJ) 
                    && chord.getInversion() == 1){
                return true;
            }
            return false;
        }
        else{
            return false;
        }
    }
    
    private static ParseResult parseTokensPostprocess(ParseResult prelim){
        List<PrimitiveChord> primitiveChords = prelim.getPrimitiveChords();
        List<Key> keys = prelim.getKeys();
        List<Set<ContextTag>> contextTags = prelim.getContextTags();
        
        for (int i=0; i<primitiveChords.size(); i++){
            if (isPerfectCadence(primitiveChords.get(i), keys.get(i), contextTags.get(i))){
                Key key = keys.get(i);
                if (i>=1 && keys.get(i-1).equals(key) 
                        && isCadentialDominant(primitiveChords.get(i-1), keys.get(i-1))){
                    contextTags.get(i-1).add(ContextTag.CADENTIAL_V);
                    if (i>=2 && keys.get(i-2).equals(key)){
                        if (isCadentialPredominant(primitiveChords.get(i-2), 
                                keys.get(i-2), contextTags.get(i-2))){
                            contextTags.get(i-2).add(ContextTag.CADENTIAL_PREDOMINANT);
                        }
                        else if (isCadentialI64(primitiveChords.get(i-2), keys.get(i-2))){
                            contextTags.get(i-2).add(ContextTag.CADENTIAL_I64);
                            if (i>=3 && keys.get(i-3).equals(key) 
                                    && isCadentialPredominant(primitiveChords.get(i-3), 
                                            keys.get(i-3), contextTags.get(i-3))){
                                contextTags.get(i-3).add(ContextTag.CADENTIAL_PREDOMINANT);
                            }
                        }
                    }
                }
            }
        }
        return new ParseResult(primitiveChords, keys, contextTags);
    }
    
    private static ParseResult parseTokens(List<String> tokens){
        ParseResult prelim = parseTokensPrelim(tokens);
        return parseTokensPostprocess(prelim);
    }
    
    public static ParseResult parse(String input){
        String[] inputTokenized = input.split("[ |\n|\r|\t]");
        List<String> tokens = new ArrayList<>();
        for (String token : inputTokenized){
            if (token.matches(MASTER_REGEX)){
                tokens.add(token);
            }
            else{
                if (!token.equals("")){
                    System.err.println("I don't recognize token " 
                            + token + ".  Will ignore.");
                }
            }
        }
        assert(tokens.get(0).matches(KEY_REGEX));
        return parseTokens(tokens);
    }

}

class ParseResult{
    private final List<PrimitiveChord> primitiveChords;
    private final List<Key> keys;
    private final List<Set<ContextTag>> contextTags;
    
    public ParseResult(List<PrimitiveChord> primitiveChords, 
            List<Key> keys,
            List<Set<ContextTag>> contextTags){
        this.primitiveChords = primitiveChords;
        this.keys = keys;
        this.contextTags = contextTags;
        checkRep();
    }
    
    private void checkRep(){
        assert(primitiveChords.size() == keys.size());
        assert(primitiveChords.size() == contextTags.size());
    }
    
    public List<PrimitiveChord> getPrimitiveChords(){
        return Collections.unmodifiableList(primitiveChords);
    }
    
    public List<Key> getKeys(){
        return Collections.unmodifiableList(keys);
    }
    
    public List<Set<ContextTag>> getContextTags(){
        return Collections.unmodifiableList(contextTags);
    }
}
