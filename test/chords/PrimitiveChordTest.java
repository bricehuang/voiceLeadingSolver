package chords;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import music.BasicNote;
import test_framework.MusicTestFramework;


public class PrimitiveChordTest extends MusicTestFramework {
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }

    @Test
    public void noteSetTest(){
        List<BasicNote> cMajorTriad = Arrays.asList(C, E, G);
        assertEquals(cMajorTriad, C_MAJ_ROOT.noteList());

        List<BasicNote> eMinorTriad = Arrays.asList(E, G, B);
        assertEquals(eMinorTriad, E_MIN_ROOT.noteList());

        List<BasicNote> bDimTriad = Arrays.asList(B, D, F);
        assertEquals(bDimTriad, B_DIM_ROOT.noteList());        
        
        List<BasicNote> gDomSeven = Arrays.asList(G, B, D, F);
        assertEquals(gDomSeven, G_DOM7_ROOT.noteList());

        List<BasicNote> dbMajSeven = Arrays.asList(Db, F, Ab, C);
        assertEquals(dbMajSeven, Db_MAJ7_ROOT.noteList());

        List<BasicNote> aMinSeven = Arrays.asList(A, C, E, G);
        assertEquals(aMinSeven, A_MIN7_ROOT.noteList());

        List<BasicNote> cshDimSeven = Arrays.asList(Cs, E, G, Bb);
        assertEquals(cshDimSeven, Cs_DIM7_ROOT.noteList());
    }
    
    @Test
    public void toStringTest(){
        assertEquals("C MAJOR INVERSION 0", C_MAJ_ROOT.toString());
        assertEquals("B DIMINISHED INVERSION 0", B_DIM_ROOT.toString());
        assertEquals("G MINOR INVERSION 2", G_MIN_64.toString());
        assertEquals("E- DOMINANT 7TH INVERSION 3", Eb_DOM7_42.toString()); 
    }
}
