package chords;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import test_framework.MusicTestFramework;

public class ChordTypeTest extends MusicTestFramework{
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    @Test
    public void numberDistinctNotesTest(){
        assertEquals(3, MAJ.numberDistinctNotes());
        assertEquals(3, MIN.numberDistinctNotes());
        assertEquals(3, DIM.numberDistinctNotes());
        assertEquals(4, DOM7.numberDistinctNotes());
        assertEquals(4, MAJ7.numberDistinctNotes());
        assertEquals(4, MIN7.numberDistinctNotes());
        assertEquals(4, DIM7.numberDistinctNotes());
    }

    @Test
    public void toStringTest(){
        assertEquals("MAJOR", MAJ.toString());
        assertEquals("MINOR", MIN.toString());
        assertEquals("DIMINISHED", DIM.toString());
        assertEquals("DOMINANT 7TH", DOM7.toString());
        assertEquals("MAJOR 7TH", MAJ7.toString());
        assertEquals("MINOR 7TH", MIN7.toString());
        assertEquals("DIMINISHED 7TH", DIM7.toString());
    }
}
