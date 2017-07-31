package music;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import test_framework.MusicTestFramework;

public class IntervalTest extends MusicTestFramework {
    
    private static final Interval UP_PFT_5TH = new Interval(PFT_5TH, 0, true);
    private static final Interval DOWN_PFT_4TH_2_OCT = new Interval(PFT_4TH, 2, false);

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    @Test
    public void toStringTest(){
        assertEquals("UP (4,7) + 0 OCTAVES", UP_PFT_5TH.toString());
        assertEquals("DOWN (3,5) + 2 OCTAVES", DOWN_PFT_4TH_2_OCT.toString());
    }
    
    @Test
    public void getterTest(){
        assertEquals(4, UP_PFT_5TH.getScaleDegrees());
        assertEquals(7, UP_PFT_5TH.getSemitones());
        assertEquals(-17, DOWN_PFT_4TH_2_OCT.getScaleDegrees());
        assertEquals(-29, DOWN_PFT_4TH_2_OCT.getSemitones());
    }
    
    @Test 
    public void melodicIntervalBetweenTest(){
        assertEquals(UP_PFT_5TH, Interval.melodicIntervalBetween(D4, A4));
        assertEquals(DOWN_PFT_4TH_2_OCT, Interval.melodicIntervalBetween(D4, A1));
    }

}
