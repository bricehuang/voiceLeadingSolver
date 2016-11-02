package music;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PrimitiveChordTest {
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }

    @Test
    public void toStringTest(){
        assertEquals("C MAJOR INVERSION 0", 
                new PrimitiveChord(new BasicNote(0,0), 
                        ChordType.MAJ, 0).toString());
        assertEquals("G MINOR INVERSION 2", 
                new PrimitiveChord(new BasicNote(4,7), 
                        ChordType.MIN, 2).toString());
        assertEquals("E- DOMINANT 7TH INVERSION 3", 
                new PrimitiveChord(new BasicNote(2,3), 
                        ChordType.DOM7, 3).toString());
    }
}
