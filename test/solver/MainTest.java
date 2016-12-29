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
        String input = "KEY:CMaj FMajT0 CMajT2 GMajT0 CMajT0Cad";
        Main.solve(input, REPORT, 1);
    }

    @Test
    public void testRealization2(){
        String input = "KEY:CMin CMinT0 A-MajT0 FMinT0 GDomS2\n"
                + "KEY:B-Maj CMinT1App FMajT0 B-MajT1 CMinS1 FMajT0 B-MajT0Cad";
        Main.solve(input, REPORT, 1);
    }

}
