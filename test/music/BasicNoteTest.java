package music;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import test_framework.MusicTestFramework;

public class BasicNoteTest extends MusicTestFramework {

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    @Test
    public void toStringTest(){
        // naturals
        assertEquals("C", C.toString());
        assertEquals("F", F.toString());

        // sharps
        assertEquals("F+", Fs.toString());
        assertEquals("B+", Bs.toString());
        
        // flats
        assertEquals("B-", Bb.toString());
        assertEquals("C-", Cb.toString());
        
        // double sharps
        assertEquals("G++", Gss.toString());
        assertEquals("B++", Bss.toString());

        // double flats
        assertEquals("G--", Gbb.toString());
        assertEquals("D--", Dbb.toString());
        assertEquals("C--", Cbb.toString());
    }
    
    @Test 
    public void transposeTest(){
        assertEquals(G, F.transpose(MAJ_2ND, true));
        assertEquals(C, F.transpose(PFT_5TH, true));
        assertEquals(F, C.transpose(PFT_5TH, false));
    }
    
    // renderInKey tested in KeyTest

}
