package parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import parser.ParserNew;

import test_framework.MusicTestFramework;

public class ParserNewTest extends MusicTestFramework {
    
    @Rule
    public final ExpectedException exception = ExpectedException.none();    

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
    
}
