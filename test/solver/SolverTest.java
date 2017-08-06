package solver;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import chord_data.ChordProgressionWithContext;
import chord_data.PrimitiveChordWithContext;
import test_framework.MusicTestFramework;

public class SolverTest extends MusicTestFramework {
    
    private static final boolean DEBUG = true;
        
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    private static void outputEvaluation(ChordProgressionWithContext progression){
        if (DEBUG){
            System.err.println("Evaluating:\n"+progression.toString()+"\n");
            System.err.println(Sequencer.getPenaltyReport(progression));
        }
    }
    
    @Test
    public void PerfectCadenceTest(){
        List<PrimitiveChordWithContext> primitiveChordsAndContexts = Arrays.asList(
            new PrimitiveChordWithContext(G_MAJ_ROOT, C_MAJOR, CADENTIAL_V),
            new PrimitiveChordWithContext(C_MAJ_ROOT, C_MAJOR, CADENCE)
        );
        List<ChordProgressionWithContext> bestProgressions = Solver.solve(primitiveChordsAndContexts);
        outputEvaluation(bestProgressions.get(0));
    }
    
    @Test
    public void I64Test(){
        List<PrimitiveChordWithContext> primitiveChordsAndContexts = Arrays.asList(
            new PrimitiveChordWithContext(F_MAJ_ROOT, C_MAJOR, CADENTIAL_PREDOMINANT),
            new PrimitiveChordWithContext(C_MAJ_64, C_MAJOR, CADENTIAL_I64),
            new PrimitiveChordWithContext(G_MAJ_ROOT, C_MAJOR, CADENTIAL_V),
            new PrimitiveChordWithContext(C_MAJ_ROOT, C_MAJOR, CADENCE)
        );
        List<ChordProgressionWithContext> bestProgressions = Solver.solve(primitiveChordsAndContexts);
        outputEvaluation(bestProgressions.get(0));
    }
    
    @Test
    public void ExampleRealization1(){
        List<PrimitiveChordWithContext> primitiveChordsAndContexts = Arrays.asList(
            new PrimitiveChordWithContext(G_MAJ_ROOT, G_MAJOR, NO_CONTEXTS),
            new PrimitiveChordWithContext(D_DOM7_ROOT, G_MAJOR, NO_CONTEXTS),
            new PrimitiveChordWithContext(G_MAJ_ROOT, G_MAJOR, NO_CONTEXTS),
            new PrimitiveChordWithContext(G_DOM7_63, G_MAJOR, APPLIED_DOMINANT),
            new PrimitiveChordWithContext(C_MAJ_ROOT, G_MAJOR, NO_CONTEXTS),
            new PrimitiveChordWithContext(D_MAJ_6, G_MAJOR, NO_CONTEXTS),
            new PrimitiveChordWithContext(G_MAJ_ROOT, G_MAJOR, NO_CONTEXTS),
            new PrimitiveChordWithContext(A_MIN7_ROOT, G_MAJOR, CADENTIAL_PREDOMINANT),
            new PrimitiveChordWithContext(G_MAJ_64, G_MAJOR, CADENTIAL_I64),
            new PrimitiveChordWithContext(D_MAJ_ROOT, G_MAJOR, CADENTIAL_V),
            new PrimitiveChordWithContext(G_MAJ_ROOT, G_MAJOR, CADENCE)
        );
        List<ChordProgressionWithContext> bestProgressions = Solver.solve(primitiveChordsAndContexts);
        outputEvaluation(bestProgressions.get(0));
    }
    
    @Test
    public void ExampleRealization2(){
        List<PrimitiveChordWithContext> primitiveChordsAndContexts = Arrays.asList(
            new PrimitiveChordWithContext(C_MIN_ROOT, C_MINOR, NO_CONTEXTS),
            new PrimitiveChordWithContext(Ab_MAJ_ROOT, C_MINOR, NO_CONTEXTS),
            new PrimitiveChordWithContext(F_MIN_ROOT, C_MINOR, NO_CONTEXTS),
            new PrimitiveChordWithContext(G_DOM7_43, C_MINOR, APPLIED_DOMINANT),
            new PrimitiveChordWithContext(C_MIN_6, Bb_MAJOR, NO_CONTEXTS),
            new PrimitiveChordWithContext(F_MAJ_ROOT, Bb_MAJOR, NO_CONTEXTS),
            new PrimitiveChordWithContext(Bb_MAJ_6, Bb_MAJOR, NO_CONTEXTS),
            new PrimitiveChordWithContext(C_MIN7_65, Bb_MAJOR, CADENTIAL_PREDOMINANT),
            new PrimitiveChordWithContext(F_MAJ_ROOT, Bb_MAJOR, CADENTIAL_V),
            new PrimitiveChordWithContext(Bb_MAJ_ROOT, Bb_MAJOR, CADENCE)
        );
        List<ChordProgressionWithContext> bestProgressions = Solver.solve(primitiveChordsAndContexts);
        outputEvaluation(bestProgressions.get(0));
    }
}
