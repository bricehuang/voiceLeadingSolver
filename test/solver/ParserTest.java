package solver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import chords.ChordType;
import chords.PrimitiveChord;
import music.BasicNote;
import music.Key;

public class ParserTest {

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    private static final BasicNote C = new BasicNote(0,0);
    private static final Key C_MAJOR = new Key(C,true);
    
    @Test
    public void cadenceTest(){
        ParseResult parse = Parser.parse("KEY:CMaj CMajT0Cad");
        List<PrimitiveChord> primitiveChords = parse.getPrimitiveChords();
        List<Key> keys = parse.getKeys();
        List<Set<ContextTag>> contextTags = parse.getContextTags();
        assertEquals(1, primitiveChords.size());
        assertEquals(1, keys.size());
        assertEquals(1, contextTags.size());
        assertEquals(new PrimitiveChord(C, ChordType.MAJ, 0), primitiveChords.get(0));
        assertEquals(C_MAJOR, keys.get(0));
        assertEquals(1, contextTags.get(0).size());
        assertTrue(contextTags.get(0).contains(ContextTag.CADENCE));
    }
}
