package music;

import static org.junit.Assert.*;

import org.junit.Test;

public class KeyTest {

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    private static final Key C_MAJOR = new Key(0,true);
    private static final Key D_FL_MAJOR = new Key(-5,true);
    private static final Key C_SH_MINOR = new Key(4,false);
    
    /****************************************
     *      TESTING isReducedPitchInKey     *
     ****************************************/
    
    @Test
    public void testIsReducedPitchInKey(){
        assertEquals(true, C_MAJOR.isReducedPitchInKey(0));
        assertEquals(false, C_MAJOR.isReducedPitchInKey(1));
        assertEquals(false, C_MAJOR.isReducedPitchInKey(6));
        assertEquals(true, C_MAJOR.isReducedPitchInKey(7));
        assertEquals(true, C_SH_MINOR.isReducedPitchInKey(1));
        assertEquals(true, C_SH_MINOR.isReducedPitchInKey(4));
        assertEquals(false, C_SH_MINOR.isReducedPitchInKey(5));
        assertEquals(true, C_SH_MINOR.isReducedPitchInKey(11));
        assertEquals(false, C_SH_MINOR.isReducedPitchInKey(0));
    }
    
    /****************************************
     *         TESTING CONVERSIONS          *
     ****************************************/
    
    @Test
    public void testGetReducedPitchOfScaleDegree(){
        assertEquals(0, C_MAJOR.getReducedPitchOfScaleDegree(1, 0));
        assertEquals(2, C_MAJOR.getReducedPitchOfScaleDegree(2, 0));
        assertEquals(9, C_MAJOR.getReducedPitchOfScaleDegree(6, 0));
        assertEquals(11, C_MAJOR.getReducedPitchOfScaleDegree(7, 0));
        assertEquals(0, C_MAJOR.getReducedPitchOfScaleDegree(7, 1));
        assertEquals(10, C_MAJOR.getReducedPitchOfScaleDegree(7, -1));
        assertEquals(1, D_FL_MAJOR.getReducedPitchOfScaleDegree(1, 0));
        assertEquals(0, D_FL_MAJOR.getReducedPitchOfScaleDegree(7, 0));
        assertEquals(1, C_SH_MINOR.getReducedPitchOfScaleDegree(1, 0));
        assertEquals(4, C_SH_MINOR.getReducedPitchOfScaleDegree(3, 0));
        assertEquals(8, C_SH_MINOR.getReducedPitchOfScaleDegree(5, 0));
        assertEquals(11, C_SH_MINOR.getReducedPitchOfScaleDegree(7, 0));
    }
    
    @Test
    public void testGetReducedPitchOfLetterNote(){
        
        assertEquals(0, C_MAJOR.getReducedPitchOfLetterNote('C', 0));
        assertEquals(2, C_MAJOR.getReducedPitchOfLetterNote('D', 0));
        assertEquals(9, C_MAJOR.getReducedPitchOfLetterNote('A', 0));
        assertEquals(11, C_MAJOR.getReducedPitchOfLetterNote('B', 0));
        assertEquals(0, C_MAJOR.getReducedPitchOfLetterNote('B', 1));
        assertEquals(10, C_MAJOR.getReducedPitchOfLetterNote('B', -1));
        assertEquals(1, D_FL_MAJOR.getReducedPitchOfLetterNote('D', 0));
        assertEquals(0, D_FL_MAJOR.getReducedPitchOfLetterNote('C', 0));
        assertEquals(1, C_SH_MINOR.getReducedPitchOfLetterNote('C', 0));
        assertEquals(4, C_SH_MINOR.getReducedPitchOfLetterNote('E', 0));
        assertEquals(8, C_SH_MINOR.getReducedPitchOfLetterNote('G', 0));
        assertEquals(11, C_SH_MINOR.getReducedPitchOfLetterNote('B', 0));
    }
    
    
    /****************************************
     * TESTING getWrittenNameOfReducedPitch *
     ****************************************/
    
    @Test
    public void basicWrittenNameTest(){
        assertEquals("F",C_MAJOR.getWrittenNameOfReducedPitch(5, 0));
        assertEquals("F+",C_MAJOR.getWrittenNameOfReducedPitch(6, 1));
        assertEquals("G+",C_MAJOR.getWrittenNameOfReducedPitch(8, 1));
        assertEquals("B-",C_MAJOR.getWrittenNameOfReducedPitch(10, -1));
        assertEquals("B+",C_SH_MINOR.getWrittenNameOfReducedPitch(0, 1));
        assertEquals("F=",C_SH_MINOR.getWrittenNameOfReducedPitch(5, -1));
        assertEquals("G=",D_FL_MAJOR.getWrittenNameOfReducedPitch(7, 1));
    }

    @Test
    public void doubleAccidentalsWrittenNameTest(){
        assertEquals("F++",C_MAJOR.getWrittenNameOfReducedPitch(7, 2));
        assertEquals("G++",C_MAJOR.getWrittenNameOfReducedPitch(9, 2));
        assertEquals("B--",C_MAJOR.getWrittenNameOfReducedPitch(9, -2));
    }
    
    @Test
    public void basicWrittenNameWithDoubleAccidentals(){
        assertEquals("F++",C_MAJOR.getWrittenNameOfReducedPitch(7, 2));
        assertEquals("G++",C_MAJOR.getWrittenNameOfReducedPitch(9, 2));
        assertEquals("B--",C_MAJOR.getWrittenNameOfReducedPitch(9, -2));
        assertEquals("F=-",C_SH_MINOR.getWrittenNameOfReducedPitch(4, -2));
        assertEquals("G=+",D_FL_MAJOR.getWrittenNameOfReducedPitch(8, 2));
    }

}
