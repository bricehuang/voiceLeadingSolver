package solver;

import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

import chord_data.ChordWithContext;
import chord_data.PrimitiveChordWithContext;
import chords.Chord;
import chords.ChordType;
import chords.PrimitiveChord;
import test_framework.MusicTestFramework;

public class ChordGeneratorTest extends MusicTestFramework {

    private static final PrimitiveChord C_MAJ_ROOT = new PrimitiveChord(
    		C, ChordType.MAJ, 0
    	);
    private static final PrimitiveChord Db_DOM7_ROOT = new PrimitiveChord(
    		Ab, ChordType.DOM7, 0
    	);
    
    private static final boolean PRINT_TESTS = false;
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
        
    @Test
    public void testCMajorRootTriadDeprecated(){
        Set<Chord> cMajorRootChords = ChordGenerator.generateChordsDeprecated(
            Arrays.asList(C_MAJ_ROOT)
        ).get(0);  
        if(PRINT_TESTS){
            for (Chord chord : cMajorRootChords){
                System.err.println(chord.toString());
            }            
        }
    }
    
    @Test
    public void testDbDomSevenRootDeprecated(){
        Set<Chord> dbDomSevenChords = ChordGenerator.generateChordsDeprecated(
        		Arrays.asList(Db_DOM7_ROOT)
        	).get(0);  
        if(PRINT_TESTS){
            for (Chord chord : dbDomSevenChords){
                System.err.println(chord.toString());
            }            
        }
    }

    @Test
    public void testCMajorRootTriad(){
    		PrimitiveChordWithContext primitiveAndContext = new PrimitiveChordWithContext(
    			C_MAJ_ROOT, C_MAJOR, NO_CONTEXTS
    		);
        Set<ChordWithContext> chordsAndContexts = ChordGenerator.generateChordsWithContext(
            Arrays.asList(primitiveAndContext)
        ).get(0);  
        if(PRINT_TESTS){
            for (ChordWithContext chordAndContext : chordsAndContexts){
                System.err.println(chordAndContext.toString());
            }            
        }
    }
    
    @Test
    public void testDbDomSevenRoot(){
    		PrimitiveChordWithContext primitiveAndContext = new PrimitiveChordWithContext(
    			Db_DOM7_ROOT, Db_MAJOR, CADENTIAL_V
    		);
        Set<ChordWithContext> chordsAndContexts = ChordGenerator.generateChordsWithContext(
            Arrays.asList(primitiveAndContext)
        ).get(0);  
        if(PRINT_TESTS){
            for (ChordWithContext chordAndContext : chordsAndContexts){
                System.err.println(chordAndContext.toString());
            }            
        }
    }

}
