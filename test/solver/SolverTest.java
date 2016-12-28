package solver;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import chords.ChordType;
import chords.PrimitiveChord;
import music.BasicNote;
import music.Key;
import scorer.Scorer;
import solver.ContextTag;

public class SolverTest {
    
    private static final BasicNote C = new BasicNote(0,0);
    private static final BasicNote D = new BasicNote(1,2);
    private static final BasicNote F = new BasicNote(3,5);
    private static final BasicNote G = new BasicNote(4,7);
    private static final BasicNote A = new BasicNote(5,9);

    Key C_MAJOR = new Key(0,true);
    Key G_MAJOR = new Key(1,true);

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
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
        List<ChordProgWithScore> bestProgressions = Solver.solve(primitiveChords, keys, contextTagsList);
        System.err.print(Scorer.evaluateChordProgression(bestProgressions.get(0).getChordProg(), keys, contextTagsList));
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
        List<ChordProgWithScore> bestProgressions = Solver.solve(primitiveChords, keys, contextTagsList);
        System.err.print(Scorer.evaluateChordProgression(bestProgressions.get(0).getChordProg(), keys, contextTagsList));
    }
    
    @Test
    public void ExampleRealization(){
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
                new HashSet<>(),
                new HashSet<>(Arrays.asList(ContextTag.APPLIED_DOMINANT)),
                new HashSet<>(),
                new HashSet<>(),
                new HashSet<>(Arrays.asList(ContextTag.CADENTIAL_PREDOMINANT)), 
                new HashSet<>(Arrays.asList(ContextTag.CADENTIAL_I64)), 
                new HashSet<>(Arrays.asList(ContextTag.CADENTIAL_V)), 
                new HashSet<>(Arrays.asList(ContextTag.CADENCE))
                );
        List<ChordProgWithScore> bestProgressions = Solver.solve(primitiveChords, keys, contextTagsList);
        System.err.print(Scorer.evaluateChordProgression(bestProgressions.get(0).getChordProg(), keys, contextTagsList));
    }
}
