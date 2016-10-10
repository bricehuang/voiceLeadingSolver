package music;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BasicNoteTest {

    private static final Key C_MAJOR = new Key(0,true);
    private static final Key D_FL_MAJOR = new Key(-5,true);
    private static final Key C_SH_MINOR = new Key(4,false);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    @Test
    public void basicToStringTest(){
        assertEquals("F", new BasicNote('F',C_MAJOR,0).toString());
        assertEquals("F+", new BasicNote(4,C_MAJOR,1).toString());
        assertEquals("G+", new BasicNote('G',C_MAJOR,1).toString());
        assertEquals("B-", new BasicNote(7,C_MAJOR,-1).toString());
        assertEquals("B-", new BasicNote('B',C_MAJOR,-1).toString());
        assertEquals("B+", new BasicNote('B',C_SH_MINOR,1).toString());
        assertEquals("F=", new BasicNote('F',C_SH_MINOR,-1).toString());
        assertEquals("G=", new BasicNote('G',D_FL_MAJOR,1).toString());
    }

    @Test
    public void doubleAccidentalsToStringTest(){
        assertEquals("F++", new BasicNote('F', C_MAJOR, 2).toString());
        assertEquals("G++", new BasicNote('G', C_MAJOR, 2).toString());
        assertEquals("B--", new BasicNote('B', C_MAJOR, -2).toString());
    }
    
    @Test
    public void complexAccidentalsToStringTest(){
        assertEquals("F=-", new BasicNote('F',C_SH_MINOR,-2).toString());
        assertEquals("G=+", new BasicNote('G',D_FL_MAJOR, 2).toString());
    }


}
