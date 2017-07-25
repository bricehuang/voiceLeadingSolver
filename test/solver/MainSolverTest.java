package solver;

import java.util.List;

import org.junit.Test;

import chords.ChordProgressionDeprecated;
import player.ProgressionPlayer;

public class MainSolverTest {
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    private static final boolean REPORT = true;
    private static final int MAX_REPORT = 1;
    
    @Test
    public void testI64(){
        String input = "KEY:CMaj FMajT0 CMajT2 GMajT0 CMajT0Cad";
        Main.solve(input, REPORT, MAX_REPORT).get(0);
    }
    
    @Test
    public void testRealization1(){
        String input = "KEY:GMaj GMajT0 DDomS0 GMajT0 GDomS1App\n"
                + "CMajT0 DMajT1 GMajT0 AMinS0 GMajT2 DMajT0 GMajT0Cad";
        Main.solve(input, REPORT, MAX_REPORT).get(0);
    }
    
    @Test
    public void testRealization2(){
        String input = "KEY:CMin CMinT0 A-MajT0 FMinT0 GDomS2App\n"
                + "KEY:B-Maj CMinT1 FMajT0 B-MajT1 CMinS1 FMajT0 B-MajT0Cad";
        Main.solve(input, REPORT, MAX_REPORT).get(0);
    }

}
