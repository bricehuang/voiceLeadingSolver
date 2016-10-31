package music;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IntervalTest {
    
    private static final BasicInterval PERFECT_FOURTH = new BasicInterval(3,5);
    private static final BasicInterval PERFECT_FIFTH = new BasicInterval(4,7);
    private static final Interval MELODIC_UP_PERFECT_FIFTH= new Interval(PERFECT_FIFTH, 0, true);
    private static final Interval MELODIC_DOWN_PERFECT_FOURTH_2OCT = new Interval(PERFECT_FOURTH, 2, false);

    private static final Note D4 = new Note(new BasicNote(1,2), 4);
    private static final Note A4 = new Note(new BasicNote(5,9), 4);
    private static final Note A1 = new Note(new BasicNote(5,9), 1);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    @Test
    public void toStringTest(){
        assertEquals("UP (4,7) + 0 OCTAVES", MELODIC_UP_PERFECT_FIFTH.toString());
        assertEquals("DOWN (3,5) + 2 OCTAVES", MELODIC_DOWN_PERFECT_FOURTH_2OCT.toString());
    }
    
    @Test
    public void getterTest(){
        assertEquals(4, MELODIC_UP_PERFECT_FIFTH.getScaleDegrees());
        assertEquals(7, MELODIC_UP_PERFECT_FIFTH.getSemitones());
        assertEquals(-17, MELODIC_DOWN_PERFECT_FOURTH_2OCT.getScaleDegrees());
        assertEquals(-29, MELODIC_DOWN_PERFECT_FOURTH_2OCT.getSemitones());
    }
    
    @Test 
    public void melodicIntervalBetweenTest(){
        assertEquals(MELODIC_UP_PERFECT_FIFTH,
                Interval.melodicIntervalBetween(D4,A4));
        assertEquals(MELODIC_DOWN_PERFECT_FOURTH_2OCT,
                Interval.melodicIntervalBetween(D4,A1));
    }

}
