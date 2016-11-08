package solver;

import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

import chords.Chord;
import chords.ChordType;
import chords.PrimitiveChord;
import music.BasicNote;
import solver.ChordGenerator;

public class ChordGeneratorTest {
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    @Test
    public void testCMajorRootTriad(){
        Set<Chord> cMajorRootChords = ChordGenerator.generateChords(
                Arrays.asList(new PrimitiveChord(
                        new BasicNote(0,0), ChordType.MAJ, 0)
                        )
                ).get(0);  
        for (Chord chord : cMajorRootChords){
            System.out.println(chord.toString());
        }
    }
}
