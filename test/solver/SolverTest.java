package solver;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import chords.ChordProgression;
import chords.ChordType;
import chords.PrimitiveChord;
import music.BasicNote;
import music.Key;
import scorer.Scorer;

public class SolverTest {
    
    private static final boolean DEBUG = false;
    
    private static final BasicNote C = new BasicNote(0,0);
    private static final BasicNote D = new BasicNote(1,2);
    private static final BasicNote F = new BasicNote(3,5);
    private static final BasicNote G = new BasicNote(4,7);
    private static final BasicNote Ab = new BasicNote(5,8);
    private static final BasicNote A = new BasicNote(5,9);
    private static final BasicNote Bb = new BasicNote(6,10);

    Key C_MAJOR = new Key(C,true);
    Key G_MAJOR = new Key(G,true);
    Key C_MINOR = new Key(C,false);
    Key Bb_MAJOR = new Key(Bb, true);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    private static void outputEvaluation(ChordProgression chordProgression, 
            List<Key> keys, List<Set<ContextTag>> contextTagsList){
        if (DEBUG){
            System.err.println(Scorer.evaluateChordProgression(
                    chordProgression, keys, contextTagsList));
        }
    }
    
    @Test
    public void PerfectCadenceTest(){
        List<PrimitiveChord> primitiveChords = Arrays.asList(
                new PrimitiveChord(G, ChordType.MAJ, 0),
                new PrimitiveChord(C, ChordType.MAJ, 0)
                );
        List<Key> keys = Arrays.asList(
                C_MAJOR, C_MAJOR
                );
        List<Set<ContextTag>> contextTagsList = Arrays.asList(
                new HashSet<>(Arrays.asList(ContextTag.CADENTIAL_V)), 
                new HashSet<>(Arrays.asList(ContextTag.CADENCE))
                );
        List<ChordProgression> bestProgressions = Solver.solve(primitiveChords, keys, contextTagsList);
        outputEvaluation(bestProgressions.get(0), keys, contextTagsList);
    }
    
    @Test
    public void I64Test(){
        List<PrimitiveChord> primitiveChords = Arrays.asList(
                new PrimitiveChord(F, ChordType.MAJ, 0),
                new PrimitiveChord(C, ChordType.MAJ, 2),
                new PrimitiveChord(G, ChordType.MAJ, 0),
                new PrimitiveChord(C, ChordType.MAJ, 0)
                );
        List<Key> keys = Arrays.asList(
                C_MAJOR, C_MAJOR, C_MAJOR, C_MAJOR
                );
        List<Set<ContextTag>> contextTagsList = Arrays.asList(
                new HashSet<>(Arrays.asList(ContextTag.CADENTIAL_PREDOMINANT)), 
                new HashSet<>(Arrays.asList(ContextTag.CADENTIAL_I64)), 
                new HashSet<>(Arrays.asList(ContextTag.CADENTIAL_V)), 
                new HashSet<>(Arrays.asList(ContextTag.CADENCE))
                );
        List<ChordProgression> bestProgressions = Solver.solve(primitiveChords, keys, contextTagsList);
        outputEvaluation(bestProgressions.get(0), keys, contextTagsList);
    }
    
    @Test
    public void ExampleRealization1(){
        List<PrimitiveChord> primitiveChords = Arrays.asList(
                new PrimitiveChord(G, ChordType.MAJ, 0),
                new PrimitiveChord(D, ChordType.DOM7, 0),
                new PrimitiveChord(G, ChordType.MAJ, 0),
                new PrimitiveChord(G, ChordType.DOM7, 1),
                new PrimitiveChord(C, ChordType.MAJ, 0),
                new PrimitiveChord(D, ChordType.MAJ, 1),
                new PrimitiveChord(G, ChordType.MAJ, 0),
                new PrimitiveChord(A, ChordType.MIN7, 0),
                new PrimitiveChord(G, ChordType.MAJ, 2),
                new PrimitiveChord(D, ChordType.MAJ, 0),
                new PrimitiveChord(G, ChordType.MAJ, 0)
                );
        List<Key> keys = Arrays.asList(
                G_MAJOR,
                G_MAJOR,
                G_MAJOR,
                G_MAJOR,
                G_MAJOR,
                G_MAJOR,
                G_MAJOR,
                G_MAJOR,
                G_MAJOR,
                G_MAJOR,
                G_MAJOR
                );
        List<Set<ContextTag>> contextTagsList = Arrays.asList(
                new HashSet<>(),
                new HashSet<>(),
                new HashSet<>(),
                new HashSet<>(Arrays.asList(ContextTag.APPLIED_DOMINANT)),
                new HashSet<>(),
                new HashSet<>(),
                new HashSet<>(),
                new HashSet<>(Arrays.asList(ContextTag.CADENTIAL_PREDOMINANT)), 
                new HashSet<>(Arrays.asList(ContextTag.CADENTIAL_I64)), 
                new HashSet<>(Arrays.asList(ContextTag.CADENTIAL_V)), 
                new HashSet<>(Arrays.asList(ContextTag.CADENCE))
                );
        List<ChordProgression> bestProgressions = Solver.solve(primitiveChords, keys, contextTagsList);
        outputEvaluation(bestProgressions.get(0), keys, contextTagsList);
    }
    
    @Test
    public void ExampleRealization2(){
        List<PrimitiveChord> primitiveChords = Arrays.asList(
                new PrimitiveChord(C, ChordType.MIN, 0),
                new PrimitiveChord(Ab, ChordType.MAJ, 0),
                new PrimitiveChord(F, ChordType.MIN, 0),
                new PrimitiveChord(G, ChordType.DOM7, 2),
                new PrimitiveChord(C, ChordType.MIN, 1),
                new PrimitiveChord(F, ChordType.MAJ, 0),
                new PrimitiveChord(Bb, ChordType.MAJ, 1),
                new PrimitiveChord(C, ChordType.MIN7, 1),
                new PrimitiveChord(F, ChordType.MAJ, 0),
                new PrimitiveChord(Bb, ChordType.MAJ, 0)
                );
        List<Key> keys = Arrays.asList(
                C_MINOR,
                C_MINOR,
                C_MINOR,
                C_MINOR,
                Bb_MAJOR,
                Bb_MAJOR,
                Bb_MAJOR,
                Bb_MAJOR,
                Bb_MAJOR,
                Bb_MAJOR
                );
        List<Set<ContextTag>> contextTagsList = Arrays.asList(
                new HashSet<>(),
                new HashSet<>(),
                new HashSet<>(),
                new HashSet<>(Arrays.asList(ContextTag.APPLIED_DOMINANT)),
                new HashSet<>(),
                new HashSet<>(),
                new HashSet<>(),
                new HashSet<>(Arrays.asList(ContextTag.CADENTIAL_PREDOMINANT)), 
                new HashSet<>(Arrays.asList(ContextTag.CADENTIAL_V)), 
                new HashSet<>(Arrays.asList(ContextTag.CADENCE))
                );
        List<ChordProgression> bestProgressions = Solver.solve(primitiveChords, keys, contextTagsList);
        outputEvaluation(bestProgressions.get(0), keys, contextTagsList);
    }
}
