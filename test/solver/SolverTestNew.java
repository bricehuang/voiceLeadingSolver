package solver;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import chord_data.ChordProgressionWithContext;
import chord_data.ContextTag;
import chord_data.PrimitiveChordWithContext;
import chords.ChordType;
import chords.PrimitiveChord;
import music.BasicNote;
import music.Key;

public class SolverTestNew {
    
    private static final boolean DEBUG = true;
    
    private static final BasicNote C = new BasicNote(0,0);
    private static final BasicNote D = new BasicNote(1,2);
    private static final BasicNote F = new BasicNote(3,5);
    private static final BasicNote G = new BasicNote(4,7);
    private static final BasicNote Ab = new BasicNote(5,8);
    private static final BasicNote A = new BasicNote(5,9);
    private static final BasicNote Bb = new BasicNote(6,10);

    private static final Key C_MAJOR = new Key(C,true);
    private static final Key G_MAJOR = new Key(G,true);
    private static final Key C_MINOR = new Key(C,false);
    private static final Key Bb_MAJOR = new Key(Bb, true);
    
    private static final PrimitiveChord C_MAJ_ROOT = 
        new PrimitiveChord(C, ChordType.MAJ, 0);
    private static final PrimitiveChord C_MAJ_64 = 
        new PrimitiveChord(C, ChordType.MAJ, 2);
    private static final PrimitiveChord C_MIN_ROOT = 
        new PrimitiveChord(C, ChordType.MIN, 0);
    private static final PrimitiveChord C_MIN_6 = 
        new PrimitiveChord(C, ChordType.MIN, 1);
    private static final PrimitiveChord C_MIN7_63 = 
        new PrimitiveChord(C, ChordType.MIN7, 1);
    private static final PrimitiveChord D_MAJ_6 = 
        new PrimitiveChord(D, ChordType.MAJ, 1);
    private static final PrimitiveChord D_MAJ_ROOT = 
        new PrimitiveChord(D, ChordType.MAJ, 0);
    private static final PrimitiveChord D_DOM7_ROOT = 
        new PrimitiveChord(D, ChordType.DOM7, 0);
    private static final PrimitiveChord F_MAJ_ROOT = 
        new PrimitiveChord(F, ChordType.MAJ, 0);
    private static final PrimitiveChord F_MIN_ROOT = 
        new PrimitiveChord(F, ChordType.MIN, 0);
    private static final PrimitiveChord G_MAJ_ROOT = 
        new PrimitiveChord(G, ChordType.MAJ, 0);
    private static final PrimitiveChord G_MAJ_64 = 
        new PrimitiveChord(G, ChordType.MAJ, 2);
    private static final PrimitiveChord G_DOM7_63 = 
        new PrimitiveChord(G, ChordType.DOM7, 1);
    private static final PrimitiveChord G_DOM7_43 = 
        new PrimitiveChord(G, ChordType.DOM7, 2);
    private static final PrimitiveChord Ab_MAJ_ROOT = 
        new PrimitiveChord(Ab, ChordType.MAJ, 0);
    private static final PrimitiveChord A_MIN7_ROOT = 
        new PrimitiveChord(A, ChordType.MIN7, 0);
    private static final PrimitiveChord Bb_MAJ_ROOT =
        new PrimitiveChord(Bb, ChordType.MAJ, 0);
    private static final PrimitiveChord Bb_MAJ_6 =
        new PrimitiveChord(Bb, ChordType.MAJ, 1);
    
    private static final Set<ContextTag> APPLIED_DOMINANT= 
        Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList(ContextTag.APPLIED_DOMINANT))
        );
    private static final Set<ContextTag> CADENCE = 
        Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList(ContextTag.CADENCE))
        );
    private static final Set<ContextTag> CADENTIAL_I64 = 
        Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList(ContextTag.CADENTIAL_I64))
        );
    private static final Set<ContextTag> CADENTIAL_PREDOMINANT = 
        Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList(ContextTag.CADENTIAL_PREDOMINANT))
        );
    private static final Set<ContextTag> CADENTIAL_V = 
        Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList(ContextTag.CADENTIAL_V))
        );
    private static final Set<ContextTag> NO_CONTEXT = 
        Collections.unmodifiableSet(new HashSet<>());

    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    private static void outputEvaluation(ChordProgressionWithContext progression){
        if (DEBUG){
            System.err.println("Evaluating:\n"+progression.toString()+"\n");
            System.err.println(SequencerNew.getPenaltyReport(progression));
        }
    }
    
    @Test
    public void PerfectCadenceTest(){
        List<PrimitiveChordWithContext> primitiveChordsAndContexts = Arrays.asList(
            new PrimitiveChordWithContext(G_MAJ_ROOT, C_MAJOR, CADENTIAL_V),
            new PrimitiveChordWithContext(C_MAJ_ROOT, C_MAJOR, CADENCE)
        );
        List<ChordProgressionWithContext> bestProgressions = SolverNew.solve(primitiveChordsAndContexts);
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
        List<ChordProgressionWithContext> bestProgressions = SolverNew.solve(primitiveChordsAndContexts);
        outputEvaluation(bestProgressions.get(0));
    }
    
    @Test
    public void ExampleRealization1(){
        List<PrimitiveChordWithContext> primitiveChordsAndContexts = Arrays.asList(
            new PrimitiveChordWithContext(G_MAJ_ROOT, G_MAJOR, NO_CONTEXT),
            new PrimitiveChordWithContext(D_DOM7_ROOT, G_MAJOR, NO_CONTEXT),
            new PrimitiveChordWithContext(G_MAJ_ROOT, G_MAJOR, NO_CONTEXT),
            new PrimitiveChordWithContext(G_DOM7_63, G_MAJOR, APPLIED_DOMINANT),
            new PrimitiveChordWithContext(C_MAJ_ROOT, G_MAJOR, NO_CONTEXT),
            new PrimitiveChordWithContext(D_MAJ_6, G_MAJOR, NO_CONTEXT),
            new PrimitiveChordWithContext(G_MAJ_ROOT, G_MAJOR, NO_CONTEXT),
            new PrimitiveChordWithContext(A_MIN7_ROOT, G_MAJOR, CADENTIAL_PREDOMINANT),
            new PrimitiveChordWithContext(G_MAJ_64, G_MAJOR, CADENTIAL_I64),
            new PrimitiveChordWithContext(D_MAJ_ROOT, G_MAJOR, CADENTIAL_V),
            new PrimitiveChordWithContext(G_MAJ_ROOT, G_MAJOR, CADENCE)
        );
        List<ChordProgressionWithContext> bestProgressions = SolverNew.solve(primitiveChordsAndContexts);
        outputEvaluation(bestProgressions.get(0));
    }
    
    @Test
    public void ExampleRealization2(){
        List<PrimitiveChordWithContext> primitiveChordsAndContexts = Arrays.asList(
            new PrimitiveChordWithContext(C_MIN_ROOT, C_MINOR, NO_CONTEXT),
            new PrimitiveChordWithContext(Ab_MAJ_ROOT, C_MINOR, NO_CONTEXT),
            new PrimitiveChordWithContext(F_MIN_ROOT, C_MINOR, NO_CONTEXT),
            new PrimitiveChordWithContext(G_DOM7_43, C_MINOR, APPLIED_DOMINANT),
            new PrimitiveChordWithContext(C_MIN_6, Bb_MAJOR, NO_CONTEXT),
            new PrimitiveChordWithContext(F_MAJ_ROOT, Bb_MAJOR, NO_CONTEXT),
            new PrimitiveChordWithContext(Bb_MAJ_6, Bb_MAJOR, NO_CONTEXT),
            new PrimitiveChordWithContext(C_MIN7_63, Bb_MAJOR, CADENTIAL_PREDOMINANT),
            new PrimitiveChordWithContext(F_MAJ_ROOT, Bb_MAJOR, CADENTIAL_V),
            new PrimitiveChordWithContext(Bb_MAJ_ROOT, Bb_MAJOR, CADENCE)
        );
        List<ChordProgressionWithContext> bestProgressions = SolverNew.solve(primitiveChordsAndContexts);
        outputEvaluation(bestProgressions.get(0));
    }
}
