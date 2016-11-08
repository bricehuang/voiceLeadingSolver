package chords;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import music.BasicNote;


public class PrimitiveChordTest {
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }

    @Test
    public void noteSetTest(){
        List<BasicNote> cMajorTriad = Arrays.asList(
                new BasicNote(0,0), new BasicNote(2,4), new BasicNote(4,7)
                );
        assertEquals(cMajorTriad, new PrimitiveChord(
                new BasicNote(0,0), ChordType.MAJ, 0).noteList());

        List<BasicNote> eMinorTriad = Arrays.asList(
                new BasicNote(2,4), new BasicNote(4,7), new BasicNote(6,11)
                );
        assertEquals(eMinorTriad, new PrimitiveChord(
                new BasicNote(2,4), ChordType.MIN, 0).noteList());

        List<BasicNote> gDomSeven = Arrays.asList(
                new BasicNote(4,7), new BasicNote(6,11), 
                new BasicNote(1,2), new BasicNote(3,5)
                );
        assertEquals(gDomSeven, new PrimitiveChord(
                new BasicNote(4,7), ChordType.DOM7, 0).noteList());

        List<BasicNote> dbMajSeven = Arrays.asList(
                new BasicNote(1,1), new BasicNote(3,5), 
                new BasicNote(5,8), new BasicNote(0,0)
                );
        assertEquals(dbMajSeven, new PrimitiveChord(
                new BasicNote(1,1), ChordType.MAJ7, 0).noteList());

        List<BasicNote> aMinSeven = Arrays.asList(
                new BasicNote(5,9), new BasicNote(0,0), 
                new BasicNote(2,4), new BasicNote(4,7)
                );
        assertEquals(aMinSeven, new PrimitiveChord(
                new BasicNote(5,9), ChordType.MIN7, 0).noteList());

        List<BasicNote> cshDimSeven = Arrays.asList(
                new BasicNote(0,1), new BasicNote(2,4), 
                new BasicNote(4,7), new BasicNote(6,10)
                );
        assertEquals(cshDimSeven, new PrimitiveChord(
                new BasicNote(0,1), ChordType.DIM7, 0).noteList());
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
