package music;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BasicNoteTest {

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    private static final Key C_MAJOR = new Key(0,true);
    private static final Key D_MAJOR = new Key(2,true);
    private static final Key G_MINOR = new Key(-2,false);
    
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
    public void renderInKeyTest(){
        // no accidental, originally natural
        assertEquals("C", new BasicNote(0,0).renderInKey(C_MAJOR));
        assertEquals("G", new BasicNote(4,7).renderInKey(D_MAJOR));
        assertEquals("G", new BasicNote(4,7).renderInKey(G_MINOR));
        // no accidental, originally sharp
        assertEquals("F", new BasicNote(3,6).renderInKey(D_MAJOR));
        assertEquals("C", new BasicNote(0,1).renderInKey(D_MAJOR));
        // no accidental, originally flat
        assertEquals("B", new BasicNote(6,10).renderInKey(G_MINOR));
        assertEquals("E", new BasicNote(2,3).renderInKey(G_MINOR));

        // +1 accidental, originally natural
        assertEquals("C+", new BasicNote(0,1).renderInKey(C_MAJOR));
        assertEquals("G+", new BasicNote(4,8).renderInKey(D_MAJOR));
        assertEquals("G+", new BasicNote(4,8).renderInKey(G_MINOR));
        // +1 accidental, originally sharp
        assertEquals("F++", new BasicNote(3,7).renderInKey(D_MAJOR));
        assertEquals("C++", new BasicNote(0,2).renderInKey(D_MAJOR));
        // +1 accidental, originally flat
        assertEquals("B=", new BasicNote(6,11).renderInKey(G_MINOR));
        assertEquals("E=", new BasicNote(2,4).renderInKey(G_MINOR));

        // -1 accidental, originally natural
        assertEquals("C-", new BasicNote(0,-1).renderInKey(C_MAJOR));
        assertEquals("G-", new BasicNote(4,6).renderInKey(D_MAJOR));
        assertEquals("G-", new BasicNote(4,6).renderInKey(G_MINOR));
        // -1 accidental, originally sharp
        assertEquals("F=", new BasicNote(3,5).renderInKey(D_MAJOR));
        assertEquals("C=", new BasicNote(0,0).renderInKey(D_MAJOR));
        // -1 accidental, originally flat
        assertEquals("B--", new BasicNote(6,9).renderInKey(G_MINOR));
        assertEquals("E--", new BasicNote(2,2).renderInKey(G_MINOR));      
        
        // +2 accidental, originally natural
        assertEquals("C++", new BasicNote(0,2).renderInKey(C_MAJOR));
        assertEquals("G++", new BasicNote(4,9).renderInKey(D_MAJOR));
        assertEquals("G++", new BasicNote(4,9).renderInKey(G_MINOR));
        // +2 accidental, originally flat
        assertEquals("B=+", new BasicNote(6,12).renderInKey(G_MINOR));
        assertEquals("E=+", new BasicNote(2,5).renderInKey(G_MINOR));

        // -2 accidental, originally natural
        assertEquals("C--", new BasicNote(0,-2).renderInKey(C_MAJOR));
        assertEquals("G--", new BasicNote(4,5).renderInKey(D_MAJOR));
        assertEquals("G--", new BasicNote(4,5).renderInKey(G_MINOR));
        // -2 accidental, originally sharp
        assertEquals("F=-", new BasicNote(3,4).renderInKey(D_MAJOR));
        assertEquals("C=-", new BasicNote(0,-1).renderInKey(D_MAJOR));
    }
    
    @Test 
    public void transposeTest(){
        assertEquals(new BasicNote(4,7), new BasicNote(3,5).transpose(
                new Interval(1,2), true));
        assertEquals(new BasicNote(0,0), new BasicNote(3,5).transpose(
                new Interval(4,7), true));
        assertEquals(new BasicNote(3,5), new BasicNote(0,0).transpose(
                new Interval(4,7), false));    }


}
