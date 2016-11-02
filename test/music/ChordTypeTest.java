package music;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ChordTypeTest {
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    @Test
    public void numberDistinctNotesTest(){
        assertEquals(3, ChordType.MAJ.numberDistinctNotes());
        assertEquals(3, ChordType.MIN.numberDistinctNotes());
        assertEquals(4, ChordType.DOM7.numberDistinctNotes());
        assertEquals(4, ChordType.MAJ7.numberDistinctNotes());
        assertEquals(4, ChordType.MIN7.numberDistinctNotes());
        assertEquals(4, ChordType.DIM7.numberDistinctNotes());
    }

    @Test
    public void toStringTest(){
        assertEquals("MAJOR", ChordType.MAJ.toString());
        assertEquals("MINOR", ChordType.MIN.toString());
        assertEquals("DOMINANT 7TH", ChordType.DOM7.toString());
        assertEquals("MAJOR 7TH", ChordType.MAJ7.toString());
        assertEquals("MINOR 7TH", ChordType.MIN7.toString());
        assertEquals("DIMINISHED 7TH", ChordType.DIM7.toString());
    }
}
