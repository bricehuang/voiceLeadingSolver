package solver;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import chord_data.ChordProgressionWithContext;
import chord_data.ChordWithContext;
import chords.Chord;
import test_framework.MusicTestFramework;

public class BestProgressionListTest extends MusicTestFramework {

    private static final Chord G_DOM7_REALIZED = new Chord(
        B4, D4, F3, G2, G_DOM7_ROOT
    );
    private static final ChordWithContext G_DOM7_CONTEXT = new ChordWithContext(
    		G_DOM7_REALIZED, C_MAJOR, CADENTIAL_V
    	);
    private static final ChordProgressionWithContext TEST_PROGRESSION = 
    		ChordProgressionWithContext.empty().append(G_DOM7_CONTEXT); 

    private static final ScoredProgression TEST1 = new ScoredProgression(TEST_PROGRESSION, 1);
    private static final ScoredProgression TEST2 = new ScoredProgression(TEST_PROGRESSION, 2);
    private static final ScoredProgression TEST3 = new ScoredProgression(TEST_PROGRESSION, 3);
    private static final ScoredProgression TEST4 = new ScoredProgression(TEST_PROGRESSION, 4);

    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    //NOTE: THESE TESTS DEPEND ON PROGRESSIONS_TO_TRACK = 3

    @Test
    public void testBasic(){
        BestProgressionList list = new BestProgressionList();
        list.addProgression(TEST4);
        assertEquals(1,list.size());
        ScoredProgression EXPECTED = list.getProgressions().get(0);
        assertEquals(TEST4.getChordProg().getLast(), EXPECTED.getChordProg().getLast());
        assertEquals(TEST4,list.getProgressions().get(0));
    }
    
    @Test
    public void testOrdering(){
    		BestProgressionList list = new BestProgressionList();
        list.addProgression(TEST4);
        list.addProgression(TEST2);
        list.addProgression(TEST3);
        assertEquals(3,list.getProgressions().size());
        assertEquals(TEST2,list.getProgressions().get(0));
        assertEquals(TEST3,list.getProgressions().get(1));
        assertEquals(TEST4,list.getProgressions().get(2));
    }
    
    @Test
    public void testOverflow(){
    		BestProgressionList list = new BestProgressionList();
        list.addProgression(TEST4);
        list.addProgression(TEST2);
        list.addProgression(TEST3);
        list.addProgression(TEST1);
        assertEquals(3,list.getProgressions().size());
        assertEquals(TEST1,list.getProgressions().get(0));
        assertEquals(TEST2,list.getProgressions().get(1));
        assertEquals(TEST3,list.getProgressions().get(2));
    }

}
