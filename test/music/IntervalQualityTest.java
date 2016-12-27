package music;

import static org.junit.Assert.*;

import org.junit.Test;

public class IntervalQualityTest {
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    @Test
    public void toStringTest(){
        assertEquals("Major", IntervalQuality.MAJ.toString());
        assertEquals("Minor", IntervalQuality.MIN.toString());
        assertEquals("Perfect", IntervalQuality.PFT.toString());
        assertEquals("Augmented", IntervalQuality.AUG.toString());
        assertEquals("Diminished", IntervalQuality.DIM.toString());
        assertEquals("ERROR", IntervalQuality.ERR.toString());
    }
    
    @Test
    public void qualityOfTest(){
        assertEquals(IntervalQuality.DIM, IntervalQuality.qualityOf(0, -1));
        assertEquals(IntervalQuality.PFT, IntervalQuality.qualityOf(0, 0));
        assertEquals(IntervalQuality.AUG, IntervalQuality.qualityOf(0, 1));

        assertEquals(IntervalQuality.DIM, IntervalQuality.qualityOf(2, 2));
        assertEquals(IntervalQuality.MIN, IntervalQuality.qualityOf(2, 3));
        assertEquals(IntervalQuality.MAJ, IntervalQuality.qualityOf(2, 4));
        assertEquals(IntervalQuality.AUG, IntervalQuality.qualityOf(2, 5));

        assertEquals(IntervalQuality.DIM, IntervalQuality.qualityOf(4, 6));
        assertEquals(IntervalQuality.PFT, IntervalQuality.qualityOf(4, 7));
        assertEquals(IntervalQuality.AUG, IntervalQuality.qualityOf(4, 8));
    }
}
