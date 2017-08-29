package parser;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import chord_data.ContextTag;
import chord_data.PrimitiveChordWithContext;
import test_framework.MusicTestFramework;

public class ParserTest extends MusicTestFramework {

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }

    @Test
    public void parseNoteTest(){
        assertEquals(C, Parser.parseNote("C"));
        assertEquals(Fs, Parser.parseNote("F#"));
        assertEquals(Bb, Parser.parseNote("Bb"));
        assertEquals(Cb, Parser.parseNote("Cb"));
        assertEquals(Bs, Parser.parseNote("B#"));
    }
    
    @Test
    public void parseChordTypeTest(){
        assertEquals(MAJ, Parser.parseChordType("maj"));
        assertEquals(MIN, Parser.parseChordType("min"));
        assertEquals(DIM, Parser.parseChordType("dim"));
        assertEquals(DOM7, Parser.parseChordType("dom7"));
        assertEquals(DIM7, Parser.parseChordType("dim7"));
        assertEquals(HDIM7, Parser.parseChordType("hdim7"));
        assertEquals(MAJ7, Parser.parseChordType("maj7"));
        assertEquals(MIN7, Parser.parseChordType("min7"));        
    }
    
    @Test
    public void parseInversionTest(){
        assertEquals((Integer) 0, Parser.parseInversion("0"));
        assertEquals((Integer) 1, Parser.parseInversion("1"));
        assertEquals((Integer) 2, Parser.parseInversion("2"));
        assertEquals((Integer) 3, Parser.parseInversion("3"));
    }

    @Test
    public void parseContextTagTest(){
        assertEquals(ContextTag.APPLIED_DOMINANT, 
            Parser.parseContext("applieddom"));
        assertEquals(ContextTag.CADENCE, 
            Parser.parseContext("cadence"));
        assertEquals(ContextTag.NEAPOLITAN, 
            Parser.parseContext("neapolitan"));
    }
    
    @Test
    public void parseChordTest(){
        assertEquals(
            new PrimitiveChordWithContext(C_MAJ_ROOT, C_MAJOR, NO_CONTEXTS),
            Parser.parseChord("C_maj_0", C_MAJOR)
        );
        assertEquals(
            new PrimitiveChordWithContext(Fs_DIM_6, G_MAJOR, NO_CONTEXTS),
            Parser.parseChord("F#_dim_1", G_MAJOR)
        );
        assertEquals(
            new PrimitiveChordWithContext(E_DOM7_65, A_MINOR, NO_CONTEXTS),
            Parser.parseChord("E_dom7_1", A_MINOR)
        );
        assertEquals(
            new PrimitiveChordWithContext(D_DOM7_ROOT, D_MAJOR, APPLIED_DOMINANT),
            Parser.parseChord("D_dom7_0_applieddom", D_MAJOR)
        );
        assertEquals(
            new PrimitiveChordWithContext(D_HDIM7_65, C_MINOR, NO_CONTEXTS),
            Parser.parseChord("D_hdim7_1", C_MINOR)
        );
        assertEquals(
            new PrimitiveChordWithContext(E_MAJ_ROOT, E_MAJOR, CADENCE),
            Parser.parseChord("E_maj_0_cadence", E_MAJOR)
        );
    }

    @Test
    public void parseQualityIsMajorTest() {
        assertEquals(true, Parser.parseQualityIsMajor("MAJ"));
        assertEquals(false, Parser.parseQualityIsMajor("MIN"));
    }

    @Test
    public void parseKeyTest() {
        assertEquals(C_MAJOR, Parser.parseKey("KEY_C_MAJ"));
        assertEquals(G_MINOR, Parser.parseKey("KEY_G_MIN"));
        assertEquals(Fs_MINOR, Parser.parseKey("KEY_F#_MIN"));
        assertEquals(Bb_MAJOR, Parser.parseKey("KEY_Bb_MAJ"));
    }

    @Test
    public void parseBeforePostProcessingTest() {
        List<PrimitiveChordWithContext> parseResult = Parser.parseBeforePostProcessing(
            "KEY_C_MAJ D_min7_1\nC_maj_2\tG_maj_0\rC_maj_0_cadence"
        );
        List<PrimitiveChordWithContext> expectedParseResult = Arrays.asList(
            new PrimitiveChordWithContext(D_MIN7_65, C_MAJOR, NO_CONTEXTS), 
            new PrimitiveChordWithContext(C_MAJ_64, C_MAJOR, NO_CONTEXTS), 
            new PrimitiveChordWithContext(G_MAJ_ROOT, C_MAJOR, NO_CONTEXTS),
            new PrimitiveChordWithContext(C_MAJ_ROOT, C_MAJOR, CADENCE)
        );
        assertEquals(expectedParseResult, parseResult);
    }

    @Test
    public void parseTest() {
        List<PrimitiveChordWithContext> parseResult = Parser.parse(
            "KEY_C_MAJ D_min7_1\nC_maj_2\tG_maj_0\rC_maj_0_cadence"
        );
        List<PrimitiveChordWithContext> expectedParseResult = Arrays.asList(
            new PrimitiveChordWithContext(D_MIN7_65, C_MAJOR, CADENTIAL_PREDOMINANT), 
            new PrimitiveChordWithContext(C_MAJ_64, C_MAJOR, CADENTIAL_I64), 
            new PrimitiveChordWithContext(G_MAJ_ROOT, C_MAJOR, CADENTIAL_V),
            new PrimitiveChordWithContext(C_MAJ_ROOT, C_MAJOR, CADENCE)
        );
        assertEquals(expectedParseResult, parseResult);
    }

    @Test
    public void parseTest2() {
        // This one won't pass yet, Neapolitan not implemented.  
        List<PrimitiveChordWithContext> parseResult = Parser.parse(
            "KEY_A_MAJ Bb_maj_1_neapolitan E_maj_0 A_maj_0_cadence"
        );
        List<PrimitiveChordWithContext> expectedParseResult = Arrays.asList(
            new PrimitiveChordWithContext(Bb_MAJ_6, A_MAJOR, NEAPOLITAN_PREDOMINANT),  
            new PrimitiveChordWithContext(E_MAJ_ROOT, A_MAJOR, CADENTIAL_V),
            new PrimitiveChordWithContext(A_MAJ_ROOT, A_MAJOR, CADENCE)
        );
        assertEquals(expectedParseResult, parseResult);
    }    
}
