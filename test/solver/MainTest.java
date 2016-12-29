package solver;

import org.junit.Test;

public class MainTest {
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    private static boolean REPORT = true;

    @Test
    public void testI64(){
        Main.solve("KEY:CMaj FMajT0 CMajT2 GMajT0 CMajT0", REPORT, 1);
    }
}
