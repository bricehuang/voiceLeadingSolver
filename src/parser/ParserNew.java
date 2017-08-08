package parser;

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
    
}
