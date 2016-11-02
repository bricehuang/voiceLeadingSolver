package music;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ChordTypeTest {
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
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
