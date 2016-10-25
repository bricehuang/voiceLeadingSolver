package music;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class IntervalTest {

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    @Test
    public void toStringTest(){
        assertEquals("(5,7)", new Interval(5,7).toString());
        assertEquals("(4,5)", new Interval(4,5).toString());
        assertEquals("(0,0)", new Interval(0,0).toString());
        assertEquals("(7,12)", new Interval(7,12).toString());
    }
    
    @Test
    public void intervalBetweenTest(){
        assertEquals(new Interval(4,7), Interval.intervalBetween(
                new BasicNote(2,4), new BasicNote(6,11)));
        assertEquals(new Interval(3,5), Interval.intervalBetween(
                new BasicNote(6,11), new BasicNote(2,4)));
    }


}
