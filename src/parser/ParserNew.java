package parser;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import chord_data.ContextTag;
import chords.ChordType;
import music.BasicNote;

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
     *  - appdominant (Applied dominant)
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
        "(appdominant|cadence)";
    private static final String QUALITY_REGEX = "(MAJ|MIN)";
    
    private static final String REPEATING_TAG_REGEX = 
        "[_"+TAG_REGEX+"]*";
    private static final String CHORD_REGEX = 
        NOTE_REGEX+"_"+CHORDTYPE_REGEX+"_"+INVERSION_REGEX
        +REPEATING_TAG_REGEX;

    private static final String KEY_REGEX = 
        "KEY_"+NOTE_REGEX+"_"+QUALITY_REGEX;
    
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
    
    
}
