package solver;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import chord_data.ChordProgressionWithContext;
import chord_data.ChordWithContext;
import chords.Chord;
import test_framework.MusicTestFramework;

public class BestProgressionsByLastTest extends MusicTestFramework {
    
    private static final Chord G_DOM7_REALIZED = new Chord(
        B4, D4, F3, G2, G_DOM7_ROOT
    );
    private static final ChordWithContext G_DOM7_CONTEXT = new ChordWithContext(
    		G_DOM7_REALIZED, C_MAJOR, CADENTIAL_V
    	);
    
    private static final Chord C_MAJ_REALIZED = new Chord(
        C5, C4, E3, C3, C_MAJ_ROOT
    );
    private static final ChordWithContext C_MAJ_CONTEXT = new ChordWithContext(
    		C_MAJ_REALIZED, C_MAJOR, CADENCE
    	);
    
    private static final ChordProgressionWithContext TEST_G_DOM7= 
    		ChordProgressionWithContext.empty().append(G_DOM7_CONTEXT);
    private static final ChordProgressionWithContext TEST_PERFECT_CADENCE= 
    		ChordProgressionWithContext.empty().append(G_DOM7_CONTEXT).append(C_MAJ_CONTEXT);

    private static final ScoredProgression TEST1 = new ScoredProgression(TEST_G_DOM7, 1);
    private static final ScoredProgression TEST2 = new ScoredProgression(TEST_G_DOM7, 2);
    private static final ScoredProgression TEST3 = new ScoredProgression(TEST_G_DOM7, 3);
    private static final ScoredProgression TEST4 = new ScoredProgression(TEST_G_DOM7, 4);
    
    private static final ScoredProgression TEST2_PC = new ScoredProgression(TEST_PERFECT_CADENCE, 2);
    private static final ScoredProgression TEST4_PC = new ScoredProgression(TEST_PERFECT_CADENCE, 4);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    //NOTE: THESE TESTS DEPEND ON SortedFiniteProgList.PROGRESSIONS_TO_TRACK = 3

    @Test
    public void testBasic(){
        BestProgressionsByLast bestProgressionsByLast = new BestProgressionsByLast();
        bestProgressionsByLast.addProgression(TEST4);
        List<ScoredProgression> progressions = bestProgressionsByLast.getProgressions(G_DOM7_CONTEXT);
        		
        assertEquals(1, progressions.size());
        assertEquals(TEST4, progressions.get(0));
    }
    
    @Test
    public void testOrdering(){
	    	BestProgressionsByLast bestProgressionsByLast = new BestProgressionsByLast();
	    	bestProgressionsByLast.addProgression(TEST4);
	    	bestProgressionsByLast.addProgression(TEST2);
	    	bestProgressionsByLast.addProgression(TEST3);
	    	List<ScoredProgression> progressions = bestProgressionsByLast.getProgressions(G_DOM7_CONTEXT);
	    	
        assertEquals(3, progressions.size());
        assertEquals(TEST2, progressions.get(0));
        assertEquals(TEST3, progressions.get(1));
        assertEquals(TEST4, progressions.get(2));
    }
    
    @Test
    public void testOverflow(){
    		BestProgressionsByLast bestProgressionsByLast = new BestProgressionsByLast();
    		bestProgressionsByLast.addProgression(TEST4);
    		bestProgressionsByLast.addProgression(TEST2);
        bestProgressionsByLast.addProgression(TEST3);
        bestProgressionsByLast.addProgression(TEST1);
    		List<ScoredProgression> progressions = bestProgressionsByLast.getProgressions(G_DOM7_CONTEXT);

        assertEquals(3, progressions.size());
        assertEquals(TEST1, progressions.get(0));
        assertEquals(TEST2, progressions.get(1));
        assertEquals(TEST3, progressions.get(2));
    }

    @Test
    public void testIndependence(){
    		BestProgressionsByLast bestProgressionsByLast = new BestProgressionsByLast();
    		bestProgressionsByLast.addProgression(TEST4);
    		bestProgressionsByLast.addProgression(TEST2);
    		bestProgressionsByLast.addProgression(TEST3);
    		bestProgressionsByLast.addProgression(TEST1);
    		bestProgressionsByLast.addProgression(TEST2_PC);
    		bestProgressionsByLast.addProgression(TEST4_PC);
    		List<ScoredProgression> progressionsGDom7 = bestProgressionsByLast.getProgressions(G_DOM7_CONTEXT);
    		List<ScoredProgression> progressionsCMaj = bestProgressionsByLast.getProgressions(C_MAJ_CONTEXT);
    		
        assertEquals(3, progressionsGDom7.size());
        assertEquals(TEST1, progressionsGDom7.get(0));
        assertEquals(TEST2, progressionsGDom7.get(1));
        assertEquals(TEST3, progressionsGDom7.get(2));
        
        assertEquals(2, progressionsCMaj.size());
        assertEquals(TEST2_PC, progressionsCMaj.get(0));
        assertEquals(TEST4_PC, progressionsCMaj.get(1));
    }

}
