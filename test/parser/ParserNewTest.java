package parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import chord_data.ContextTag;
import chord_data.PrimitiveChordWithContext;
import test_framework.MusicTestFramework;

public class ParserNewTest extends MusicTestFramework {

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }

    @Test
    public void parseNoteTest(){
        assertEquals(C, ParserNew.parseNote("C"));
        assertEquals(Fs, ParserNew.parseNote("F#"));
        assertEquals(Bb, ParserNew.parseNote("Bb"));
        assertEquals(Cb, ParserNew.parseNote("Cb"));
        assertEquals(Bs, ParserNew.parseNote("B#"));
    }
    
    @Test
    public void parseChordTypeTest(){
        assertEquals(MAJ, ParserNew.parseChordType("maj"));
        assertEquals(MIN, ParserNew.parseChordType("min"));
        assertEquals(DOM7, ParserNew.parseChordType("dom7"));
        assertEquals(DIM7, ParserNew.parseChordType("dim7"));
        assertEquals(MAJ7, ParserNew.parseChordType("maj7"));
        assertEquals(MIN7, ParserNew.parseChordType("min7"));        
    }
    
    @Test
    public void parseInversionTest(){
        assertEquals((Integer) 0, ParserNew.parseInversion("0"));
        assertEquals((Integer) 1, ParserNew.parseInversion("1"));
        assertEquals((Integer) 2, ParserNew.parseInversion("2"));
        assertEquals((Integer) 3, ParserNew.parseInversion("3"));
    }

    @Test
    public void parseContextTagTest(){
        assertEquals(ContextTag.APPLIED_DOMINANT, 
            ParserNew.parseContext("applieddom"));
        assertEquals(ContextTag.CADENCE, 
            ParserNew.parseContext("cadence"));
    }
    
    @Test
    public void parseChordTest(){
        assertEquals(
            new PrimitiveChordWithContext(C_MAJ_ROOT, C_MAJOR, NO_CONTEXTS),
            ParserNew.parseChord("C_maj_0", C_MAJOR)
        );
        assertEquals(
            new PrimitiveChordWithContext(E_DOM7_65, A_MINOR, NO_CONTEXTS),
            ParserNew.parseChord("E_dom7_1", A_MINOR)
        );
        assertEquals(
            new PrimitiveChordWithContext(D_DOM7_ROOT, D_MAJOR, APPLIED_DOMINANT),
            ParserNew.parseChord("D_dom7_0_applieddom", D_MAJOR)
        );
        assertEquals(
            new PrimitiveChordWithContext(E_MAJ_ROOT, E_MAJOR, CADENCE),
            ParserNew.parseChord("E_maj_0_cadence", E_MAJOR)
        );
    }
    
    @Test
    public void parseQualityIsMajorTest() {
        assertEquals(true, ParserNew.parseQualityIsMajor("MAJ"));
        assertEquals(false, ParserNew.parseQualityIsMajor("MIN"));
    }

    @Test
    public void parseKeyTest() {
        assertEquals(C_MAJOR, ParserNew.parseKey("KEY_C_MAJ"));
        assertEquals(G_MINOR, ParserNew.parseKey("KEY_G_MIN"));
        assertEquals(Fs_MINOR, ParserNew.parseKey("KEY_F#_MIN"));
        assertEquals(Bb_MAJOR, ParserNew.parseKey("KEY_Bb_MAJ"));
    }

}
