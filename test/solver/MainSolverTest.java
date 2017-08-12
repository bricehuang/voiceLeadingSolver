package solver;

import org.junit.Test;

public class MainSolverTest {
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    private static final boolean REPORT = true;
    private static final int MAX_REPORT = 1;
    
    @Test
    public void testI64(){
        String input = "KEY_C_MAJ F_maj_0 C_maj_2 G_maj_0 C_maj_0_cadence";
        Main.solve(input, REPORT, MAX_REPORT).get(0);
    }
    
    @Test
    public void testRealization1(){
        String input = "KEY_G_MAJ G_maj_0 D_dom7_0 G_maj_0 G_dom7_1_applieddom\n"
            + "C_maj_0 D_maj_1 G_maj_0 A_min7_0 G_maj_2 D_maj_0 G_maj_0_cadence";
        Main.solve(input, REPORT, MAX_REPORT).get(0);
    }
    
    @Test
    public void testRealization2(){
        String input = "KEY_C_MIN C_min_0 Ab_maj_0 F_min_0 G_dom7_2_applieddom\n"
            + "KEY_Bb_MAJ C_min_1 F_maj_0 Bb_maj_1 C_min7_1 F_maj_0 Bb_maj_0_cadence";
        Main.solve(input, REPORT, MAX_REPORT).get(0);
    }

}
