package parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

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

}
