package music;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import test_framework.MusicTestFramework;

public class KeyTest extends MusicTestFramework {

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
        
    @Test
    public void getScaleDegreeTest(){
        assertEquals(C, C_MAJOR.getScaleDegree(1));
        assertEquals(G, C_MAJOR.getScaleDegree(5));
        assertEquals(B, C_MAJOR.getScaleDegree(7));
        
        assertEquals(D, D_MAJOR.getScaleDegree(1));
        assertEquals(Cs, D_MAJOR.getScaleDegree(7));

        assertEquals(G, G_MINOR.getScaleDegree(1));
        assertEquals(Bb, G_MINOR.getScaleDegree(3));        
    }
    
    @Test
    public void findScaleDegreeTest(){
        assertEquals(1, C_MAJOR.findScaleDegree(C));
        assertEquals(5, C_MAJOR.findScaleDegree(G));
        assertEquals(7, C_MAJOR.findScaleDegree(B));
        
        assertEquals(1, D_MAJOR.findScaleDegree(D));
        assertEquals(7, D_MAJOR.findScaleDegree(Cs));

        assertEquals(1, G_MINOR.findScaleDegree(G));
        assertEquals(3, G_MINOR.findScaleDegree(Bb));        
    }

    @Test
    public void renderInKeyTest(){
        // no accidental, originally natural
        assertEquals("C", C_MAJOR.renderBasicNote(C));
        assertEquals("G", D_MAJOR.renderBasicNote(G));
        assertEquals("G", G_MINOR.renderBasicNote(G));
        // no accidental, originally sharp
        assertEquals("F", D_MAJOR.renderBasicNote(Fs));
        assertEquals("C", D_MAJOR.renderBasicNote(Cs));
        // no accidental, originally flat
        assertEquals("B", G_MINOR.renderBasicNote(Bb));
        assertEquals("E", G_MINOR.renderBasicNote(Eb));

        // +1 accidental, originally natural
        assertEquals("C+", C_MAJOR.renderBasicNote(Cs));
        assertEquals("G+", D_MAJOR.renderBasicNote(Gs));
        assertEquals("G+", G_MINOR.renderBasicNote(Gs));
        // +1 accidental, originally sharp
        assertEquals("F++", D_MAJOR.renderBasicNote(Fss));
        assertEquals("C++", D_MAJOR.renderBasicNote(Css));
        // +1 accidental, originally flat
        assertEquals("B=", G_MINOR.renderBasicNote(B));
        assertEquals("E=", G_MINOR.renderBasicNote(E));

        // -1 accidental, originally natural
        assertEquals("C-", C_MAJOR.renderBasicNote(Cb));
        assertEquals("G-", D_MAJOR.renderBasicNote(Gb));
        assertEquals("G-", G_MINOR.renderBasicNote(Gb));
        // -1 accidental, originally sharp
        assertEquals("F=", D_MAJOR.renderBasicNote(F));
        assertEquals("C=", D_MAJOR.renderBasicNote(C));
        // -1 accidental, originally flat
        assertEquals("B--", G_MINOR.renderBasicNote(Bbb));
        assertEquals("E--", G_MINOR.renderBasicNote(Ebb));      
        
        // +2 accidental, originally natural
        assertEquals("C++", C_MAJOR.renderBasicNote(Css));
        assertEquals("G++", D_MAJOR.renderBasicNote(Gss));
        assertEquals("G++", G_MINOR.renderBasicNote(Gss));
        // +2 accidental, originally flat
        assertEquals("B=+", G_MINOR.renderBasicNote(Bs));
        assertEquals("E=+", G_MINOR.renderBasicNote(Es));
        
        // -2 accidental, originally natural
        assertEquals("C--", C_MAJOR.renderBasicNote(Cbb));
        assertEquals("G--", D_MAJOR.renderBasicNote(Gbb));
        assertEquals("G--", G_MINOR.renderBasicNote(Gbb));
        // -2 accidental, originally sharp
        assertEquals("F=-", D_MAJOR.renderBasicNote(Fb));
        assertEquals("C=-", D_MAJOR.renderBasicNote(Cb));
    }
    
    @Test
    public void toStringTest(){
        assertEquals("C Major", C_MAJOR.toString());
        assertEquals("D Major", D_MAJOR.toString());
        assertEquals("G Minor", G_MINOR.toString());
    }
    
}
