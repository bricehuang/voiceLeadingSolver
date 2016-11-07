package music;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BasicChordTest {
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }

    @Test
    public void toStringTest(){
        assertEquals("[B|D|F|G]", 
                new BasicChord(
                        new BasicNote(6,11), new BasicNote(1,2), 
                        new BasicNote(3,5), new BasicNote(4,7),  
                        new PrimitiveChord(new BasicNote(4,7), 
                                ChordType.DOM7, 0)).toString());
        assertEquals("[C|C|E|C]", 
                new BasicChord(
                        new BasicNote(0,0), new BasicNote(0,0), 
                        new BasicNote(2,4), new BasicNote(0,0),  
                        new PrimitiveChord(new BasicNote(0,0), 
                                ChordType.MAJ, 0)).toString());
    }

    @Test
    public void renderInKeyTest(){
        Key cMinor = new Key(-3, false);
        
        assertEquals("[B=|D|F|G]", 
                new BasicChord(
                        new BasicNote(6,11), new BasicNote(1,2), 
                        new BasicNote(3,5), new BasicNote(4,7),  
                        new PrimitiveChord(new BasicNote(4,7), 
                                ChordType.DOM7, 0)).renderInKey(cMinor));
        assertEquals("[C|C|E|C]", 
                new BasicChord(
                        new BasicNote(0,0), new BasicNote(0,0), 
                        new BasicNote(2,3), new BasicNote(0,0),  
                        new PrimitiveChord(new BasicNote(0,0), 
                                ChordType.MIN, 0)).renderInKey(cMinor));
    }


}
