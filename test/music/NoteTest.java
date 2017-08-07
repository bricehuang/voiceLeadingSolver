package music;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import test_framework.MusicTestFramework;

public class NoteTest extends MusicTestFramework {

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }

    @Test
    public void testGetBasicNote(){
        assertEquals(C, C5.getBasicNote());
        assertEquals(Ab, Ab4.getBasicNote());
        assertEquals(Gs, Gs3.getBasicNote());
    }

    @Test
    public void testGetOctave(){
        assertEquals(5, C5.getOctave());
        assertEquals(4, Ab4.getOctave());
        assertEquals(3, Gs3.getOctave());
        assertEquals(4, Cb4.getOctave());
        assertEquals(3, Bs3.getOctave());
    }    

    @Test
    public void testGetPitchID(){
        assertEquals(60, C5.getPitchID());
        assertEquals(56, Ab4.getPitchID());
        assertEquals(44, Gs3.getPitchID());
        assertEquals(47, Cb4.getPitchID());
        assertEquals(48, Bs3.getPitchID());        
    }

    @Test
    public void testGetNoteID(){
        assertEquals(35, C5.getNoteID());
        assertEquals(33, Ab4.getNoteID());
        assertEquals(25, Gs3.getNoteID());
        assertEquals(28, Cb4.getNoteID());
        assertEquals(27, Bs3.getNoteID());        
    }

    @Test
    public void testRenderInKey(){
        assertEquals("C--4", Cbb4.renderInKey(C_MAJOR));
        assertEquals("A-4", Ab4.renderInKey(C_MAJOR));
        assertEquals("C4", C4.renderInKey(C_MAJOR));
        assertEquals("G+3", Gs3.renderInKey(C_MAJOR));
        assertEquals("B++3", Bss3.renderInKey(C_MAJOR));
        
        assertEquals("F=-4", Fb4.renderInKey(G_MAJOR));
        assertEquals("F=4", F4.renderInKey(G_MAJOR));
        assertEquals("F3", Fs3.renderInKey(G_MAJOR));
        assertEquals("F++3", Fss3.renderInKey(G_MAJOR));
        
        assertEquals("B--4", Bbb4.renderInKey(D_MINOR));
        assertEquals("B4", Bb4.renderInKey(D_MINOR));
        assertEquals("B=4", B4.renderInKey(D_MINOR));
        assertEquals("B=+3", Bs3.renderInKey(D_MINOR));
    }

    @Test
    public void testToString(){
        assertEquals("C--4", Cbb4.toString());
        assertEquals("A-4", Ab4.toString());
        assertEquals("C4", C4.toString());
        assertEquals("G+3", Gs3.toString());
        assertEquals("B++3", Bss3.toString());
    }

}
