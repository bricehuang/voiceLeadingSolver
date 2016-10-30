package music;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class BasicIntervalTest {

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    @Test
    public void toStringTest(){
        assertEquals("(5,7)", new BasicInterval(5,7).toString());
        assertEquals("(4,5)", new BasicInterval(4,5).toString());
        assertEquals("(0,0)", new BasicInterval(0,0).toString());
        assertEquals("(7,12)", new BasicInterval(7,12).toString());
    }
    
    @Test
    public void intervalBetweenTest(){
        assertEquals(new BasicInterval(4,7), BasicInterval.intervalBetween(
                new BasicNote(2,4), new BasicNote(6,11)));
        assertEquals(new BasicInterval(3,5), BasicInterval.intervalBetween(
                new BasicNote(6,11), new BasicNote(2,4)));
    }


}
