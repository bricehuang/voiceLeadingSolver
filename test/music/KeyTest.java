package music;

import static org.junit.Assert.*;

import org.junit.Test;

public class KeyTest {

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    private static final BasicNote C = new BasicNote(0,0);
    private static final BasicNote D = new BasicNote(1,2);
    private static final BasicNote G = new BasicNote(4,7);
    
    private static final Key C_MAJOR = new Key(C,true);
    private static final Key D_MAJOR = new Key(D,true);
    private static final Key G_MINOR = new Key(G,false);
    
    @Test
    public void getScaleDegreeTest(){
        assertEquals(new BasicNote(0,0), C_MAJOR.getScaleDegree(1));
        assertEquals(new BasicNote(4,7), C_MAJOR.getScaleDegree(5));
        assertEquals(new BasicNote(6,11), C_MAJOR.getScaleDegree(7));
        
        assertEquals(new BasicNote(1,2), D_MAJOR.getScaleDegree(1));
        assertEquals(new BasicNote(0,1), D_MAJOR.getScaleDegree(7));

        assertEquals(new BasicNote(4,7), G_MINOR.getScaleDegree(1));
        assertEquals(new BasicNote(6,10), G_MINOR.getScaleDegree(3));        
    }
    
    @Test
    public void findScaleDegreeTest(){
        assertEquals(1, C_MAJOR.findScaleDegree(new BasicNote(0,0)));
        assertEquals(5, C_MAJOR.findScaleDegree(new BasicNote(4,7)));
        assertEquals(7, C_MAJOR.findScaleDegree(new BasicNote(6,11)));
        
        assertEquals(1, D_MAJOR.findScaleDegree(new BasicNote(1,2)));
        assertEquals(7, D_MAJOR.findScaleDegree(new BasicNote(0,1)));

        assertEquals(1, G_MINOR.findScaleDegree(new BasicNote(4,7)));
        assertEquals(3, G_MINOR.findScaleDegree(new BasicNote(6,10)));        
    }

    @Test
    public void renderInKeyTest(){
        // no accidental, originally natural
        assertEquals("C", C_MAJOR.renderBasicNote(new BasicNote(0,0)));
        assertEquals("G", D_MAJOR.renderBasicNote(new BasicNote(4,7)));
        assertEquals("G", G_MINOR.renderBasicNote(new BasicNote(4,7)));
        // no accidental, originally sharp
        assertEquals("F", D_MAJOR.renderBasicNote(new BasicNote(3,6)));
        assertEquals("C", D_MAJOR.renderBasicNote(new BasicNote(0,1)));
        // no accidental, originally flat
        assertEquals("B", G_MINOR.renderBasicNote(new BasicNote(6,10)));
        assertEquals("E", G_MINOR.renderBasicNote(new BasicNote(2,3)));

        // +1 accidental, originally natural
        assertEquals("C+", C_MAJOR.renderBasicNote(new BasicNote(0,1)));
        assertEquals("G+", D_MAJOR.renderBasicNote(new BasicNote(4,8)));
        assertEquals("G+", G_MINOR.renderBasicNote(new BasicNote(4,8)));
        // +1 accidental, originally sharp
        assertEquals("F++", D_MAJOR.renderBasicNote(new BasicNote(3,7)));
        assertEquals("C++", D_MAJOR.renderBasicNote(new BasicNote(0,2)));
        // +1 accidental, originally flat
        assertEquals("B=", G_MINOR.renderBasicNote(new BasicNote(6,11)));
        assertEquals("E=", G_MINOR.renderBasicNote(new BasicNote(2,4)));

        // -1 accidental, originally natural
        assertEquals("C-", C_MAJOR.renderBasicNote(new BasicNote(0,-1)));
        assertEquals("G-", D_MAJOR.renderBasicNote(new BasicNote(4,6)));
        assertEquals("G-", G_MINOR.renderBasicNote(new BasicNote(4,6)));
        // -1 accidental, originally sharp
        assertEquals("F=", D_MAJOR.renderBasicNote(new BasicNote(3,5)));
        assertEquals("C=", D_MAJOR.renderBasicNote(new BasicNote(0,0)));
        // -1 accidental, originally flat
        assertEquals("B--", G_MINOR.renderBasicNote(new BasicNote(6,9)));
        assertEquals("E--", G_MINOR.renderBasicNote(new BasicNote(2,2)));      
        
        // +2 accidental, originally natural
        assertEquals("C++", C_MAJOR.renderBasicNote(new BasicNote(0,2)));
        assertEquals("G++", D_MAJOR.renderBasicNote(new BasicNote(4,9)));
        assertEquals("G++", G_MINOR.renderBasicNote(new BasicNote(4,9)));
        // +2 accidental, originally flat
        assertEquals("B=+", G_MINOR.renderBasicNote(new BasicNote(6,12)));
        assertEquals("E=+", G_MINOR.renderBasicNote(new BasicNote(2,5)));
        
        // -2 accidental, originally natural
        assertEquals("C--", C_MAJOR.renderBasicNote(new BasicNote(0,-2)));
        assertEquals("G--", D_MAJOR.renderBasicNote(new BasicNote(4,5)));
        assertEquals("G--", G_MINOR.renderBasicNote(new BasicNote(4,5)));
        // -2 accidental, originally sharp
        assertEquals("F=-", D_MAJOR.renderBasicNote(new BasicNote(3,4)));
        assertEquals("C=-", D_MAJOR.renderBasicNote(new BasicNote(0,-1)));
    }
    
    @Test
    public void toStringTest(){
        assertEquals("C Major", C_MAJOR.toString());
        assertEquals("D Major", D_MAJOR.toString());
        assertEquals("G Minor", G_MINOR.toString());
    }
    
}
