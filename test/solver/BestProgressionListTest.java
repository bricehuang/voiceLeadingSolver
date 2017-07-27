package solver;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import chord_data.ChordProgressionWithContext;
import chord_data.ChordWithContext;
import chord_data.ContextTag;
import chords.Chord;
import chords.ChordType;
import chords.PrimitiveChord;
import music.BasicNote;
import music.Key;
import music.Note;

public class BestProgressionListTest {

	private static final BasicNote C = new BasicNote(0,0);
    private static final BasicNote D = new BasicNote(1,2);
    private static final BasicNote F = new BasicNote(3,5);
    private static final BasicNote G = new BasicNote(4,7);
    private static final BasicNote B = new BasicNote(6,11);
    
    private static final Key C_MAJOR = new Key(C, true);

    private static final PrimitiveChord G_DOM7 = 
        new PrimitiveChord(G,ChordType.DOM7, 0);
    private static final Chord G_DOM7_REALIZED = new Chord(
        new Note(B, 4), 
        new Note(D, 4), 
        new Note(F, 3), 
        new Note(G, 2),  
        G_DOM7
    );
    private static final ChordWithContext G_DOM7_CONTEXT = new ChordWithContext(
    		G_DOM7_REALIZED,
    		C_MAJOR,
    		new HashSet<>(Arrays.asList(ContextTag.CADENTIAL_V))
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
