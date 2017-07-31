package music;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import test_framework.MusicTestFramework;

public class BasicIntervalTest extends MusicTestFramework{

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    @Test
    public void toStringTest(){
        assertEquals("(5,7)", DIM_6TH.toString());
        assertEquals("(4,5)", DIMDIM_5TH.toString());
        assertEquals("(0,0)", PFT_UNISON.toString());
    }
    
    @Test
    public void intervalBetweenTest(){
        assertEquals(PFT_5TH, BasicInterval.intervalBetween(E, B));
        assertEquals(PFT_4TH, BasicInterval.intervalBetween(B, E));
    }


}
