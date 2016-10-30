package music;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BasicNoteTest {

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    @Test
    public void toStringTest(){
        // naturals
        assertEquals("C", new BasicNote(0,0).toString());
        assertEquals("F", new BasicNote(3,5).toString());

        // sharps
        assertEquals("F+", new BasicNote(3,6).toString());
        assertEquals("B+", new BasicNote(6,12).toString());
        
        // flats
        assertEquals("B-", new BasicNote(6,10).toString());
        assertEquals("C-", new BasicNote(0,-1).toString());
        
        // double sharps
        assertEquals("G++", new BasicNote(4,9).toString());
        assertEquals("B++", new BasicNote(6,13).toString());

        // double flats
        assertEquals("G--", new BasicNote(4,5).toString());
        assertEquals("D--", new BasicNote(1,0).toString());
        assertEquals("C--", new BasicNote(0,-2).toString());
    }
    
    
    @Test 
    public void transposeTest(){
        assertEquals(new BasicNote(4,7), new BasicNote(3,5).transpose(
                new BasicInterval(1,2), true));
        assertEquals(new BasicNote(0,0), new BasicNote(3,5).transpose(
                new BasicInterval(4,7), true));
        assertEquals(new BasicNote(3,5), new BasicNote(0,0).transpose(
                new BasicInterval(4,7), false));    }

    // renderInKey tested in KeyTest

}
