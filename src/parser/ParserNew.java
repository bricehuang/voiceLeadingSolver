package parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import chord_data.ContextTag;
import chord_data.PrimitiveChordWithContext;
import chords.ChordType;
import chords.PrimitiveChord;
import music.BasicInterval;
import music.BasicNote;
import music.Interval;
import music.Key;

public class ParserNew {
    /*
     * Spec for test input: 
     * 
     * Input consists of whitespace-separated tokens, each of which
     * specify a chord or a key.  
     * 
     * Chords are written in the form:
     * 
     * rootnote_chordtype_inversion_tag1_tag2_tag3_...
     * 
     * rootnote is specified by (uppercase) note name (e.g. A, Bb, C#)
     * chordtype is one of the following: 
     *  - maj (Major)
     *  - min (Minor)
     *  - dom7 (Dominant 7th)
     *  - dim7 (Diminished 7th)
     *  - maj7 (Major 7th) 
     *  - min7 (Major 7th)
     * tags specify any additional contexts.  Currently supported tags:
     *  - applieddom (Applied dominant)
     *  - cadence (Cadence)
     * 
     * 
     * Keys are of the form: 
     * 
     * KEY_rootnote_quality
     * 
     * rootnote is same as above
     * quality is one of the following:
     *  - MAJ (Major)
     *  - MIN (Minor)
     * 
     *  
     * Tokens are case sensitive.  The first token should always specify 
     * a key; long-lasting key changes (but not applied dominants) should 
     * be specified with a key token, with the pivot chord as the first 
     * chord in the new key.  Chords are analyzed in the key specified by 
     * the most recent key token.   
     */
    
    // Regexes
    private static final String NOTE_REGEX = "[A-G][#|b]?";
    private static final String CHORDTYPE_REGEX = 
        "(maj|min|dom7|dim7|maj7|min7)";
    private static final String INVERSION_REGEX = "[0-3]";
    private static final String TAG_REGEX = 
        "(applieddom|cadence)";
    private static final String QUALITY_REGEX = "(MAJ|MIN)";

    private static final String REPEATING_TAG_REGEX = 
        "[_"+TAG_REGEX+"]*";
    private static final String CHORD_REGEX = 
        NOTE_REGEX+"_"+CHORDTYPE_REGEX+"_"+INVERSION_REGEX
        +REPEATING_TAG_REGEX;

    private static final String KEY_REGEX = 
        "KEY_"+NOTE_REGEX+"_"+QUALITY_REGEX;

    private static final String MASTER_REGEX = 
        "("+CHORD_REGEX+")|("+KEY_REGEX+")";
    
    private static final String WHITESPACE_REGEX = "[ |\n|\r|\t]";
    
    // Translation maps
    private static final Map<String, BasicNote> NOTES;
    static{
        Map<String, BasicNote> notesTmp = new HashMap<>();
        notesTmp.put("C", new BasicNote(0,0));
        notesTmp.put("D", new BasicNote(1,2));
        notesTmp.put("E", new BasicNote(2,4));
        notesTmp.put("F", new BasicNote(3,5));
        notesTmp.put("G", new BasicNote(4,7));
        notesTmp.put("A", new BasicNote(5,9));
        notesTmp.put("B", new BasicNote(6,11));
        NOTES = Collections.unmodifiableMap(notesTmp);
    }
    private static final Map<String, Interval> ACCIDENTALS;
    static{
        Map<String, Interval> accidentalsTmp = new HashMap<>();
        BasicInterval halfStep = new BasicInterval(0,1);
        accidentalsTmp.put("#", new Interval(halfStep, 0, true));
        accidentalsTmp.put("b", new Interval(halfStep, 0, false));
        ACCIDENTALS = Collections.unmodifiableMap(accidentalsTmp);
    }    

    private static final Map<String, ChordType> CHORD_TYPES;
    static{
        Map<String, ChordType> chordTypesTmp = new HashMap<>();
        chordTypesTmp.put("maj", ChordType.MAJ);
        chordTypesTmp.put("min", ChordType.MIN);
        chordTypesTmp.put("dom7", ChordType.DOM7);
        chordTypesTmp.put("dim7", ChordType.DIM7);
        chordTypesTmp.put("maj7", ChordType.MAJ7);
        chordTypesTmp.put("min7", ChordType.MIN7);
        CHORD_TYPES = Collections.unmodifiableMap(chordTypesTmp);
    }

    private static final Map<String, ContextTag> CONTEXT_TAGS;
    static{
        Map<String, ContextTag> contextTagsTmp = new HashMap<>();
        contextTagsTmp.put("applieddom", ContextTag.APPLIED_DOMINANT);
        contextTagsTmp.put("cadence", ContextTag.CADENCE);
        CONTEXT_TAGS = Collections.unmodifiableMap(contextTagsTmp);
    }

    private static final Map<String, Boolean> KEY_IS_MAJOR;
    static{
        Map<String, Boolean> keyIsMajorTmp = new HashMap<>();
        keyIsMajorTmp.put("MAJ", true);
        keyIsMajorTmp.put("MIN", false);
        KEY_IS_MAJOR = Collections.unmodifiableMap(keyIsMajorTmp);
    }

    public static BasicNote parseNote(String in) {
        assert (in.matches(NOTE_REGEX));
        BasicNote baseNote = NOTES.get(in.substring(0,1));
        if (in.length() == 1) {
            return baseNote;
        } else if (in.length() == 2) {
            Interval intervalToTranspose = ACCIDENTALS.get(
                in.substring(1,2)
            );
            return baseNote.transpose(intervalToTranspose);
        } else {
            throw new RuntimeException("Should not get here");
        }
    }

    public static ChordType parseChordType(String in) { 
        assert (in.matches(CHORDTYPE_REGEX));
        return CHORD_TYPES.get(in);
    }

    public static Integer parseInversion(String in) {
        assert (in.matches(INVERSION_REGEX));
        return Integer.valueOf(in);
    }

    public static ContextTag parseContext(String in) { 
        assert (in.matches(TAG_REGEX));
        return CONTEXT_TAGS.get(in);
    }

    public static PrimitiveChordWithContext parseChord(String in, Key key) {
        assert (in.matches(CHORD_REGEX));
        String[] tokens = in.split("_");
        BasicNote rootNote = parseNote(tokens[0]);
        ChordType chordType = parseChordType(tokens[1]);
        Integer inversion = parseInversion(tokens[2]);
        PrimitiveChord chord = new PrimitiveChord(rootNote, chordType, inversion);
        Set<ContextTag> contexts = new HashSet<>();
        for (int i=3; i<tokens.length; i++) {
            contexts.add(parseContext(tokens[i]));
        }
        return new PrimitiveChordWithContext(chord, key, contexts);
    }

    public static boolean parseQualityIsMajor(String in) {
        assert (in.matches(QUALITY_REGEX));
        return KEY_IS_MAJOR.get(in);
    }
    
    public static Key parseKey(String in) {
        assert (in.matches(KEY_REGEX));
        String[] tokens = in.split("_");
        assert tokens.length == 3;
        assert tokens[0].equals("KEY");
        BasicNote tonic = parseNote(tokens[1]);
        boolean isMajor = parseQualityIsMajor(tokens[2]);
        return new Key(tonic, isMajor);
    }

    public static List<PrimitiveChordWithContext> parseBeforePostProcessing(String in) {
        String[] tokens = in.split(WHITESPACE_REGEX);

        assert tokens[0].matches(KEY_REGEX);
        Key currentKey = null; // this should be overwritten before first use
        List<PrimitiveChordWithContext> parseResult = new ArrayList<>();

        for (String token: tokens) {
            assert token.matches(MASTER_REGEX); // TODO: allow comments
            if (token.matches(CHORD_REGEX)) {
                parseResult.add(parseChord(token, currentKey));
            } else if (token.matches(KEY_REGEX)) {
                currentKey = parseKey(token);
            } else {
                throw new RuntimeException("Should not get here");
            }
        }
        return parseResult;
    }
    
    public static List<PrimitiveChordWithContext> parse(String in) {
        List<PrimitiveChordWithContext> parseResult = parseBeforePostProcessing(in);
        ParsePostProcessor.applyPostProcessors(parseResult);
        return parseResult;
    }
}
