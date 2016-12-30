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
    private static final BasicNote D = new BasicNote(1,2);
    private static final BasicNote F = new BasicNote(3,5);
    private static final BasicNote G = new BasicNote(4,7);
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

    @Test
    public void I64Test(){
        ParseResult parse = Parser.parse("KEY:CMaj FMajT0 CMajT2 GMajT0 CMajT0Cad");
        List<PrimitiveChord> primitiveChords = parse.getPrimitiveChords();
        List<Key> keys = parse.getKeys();
        List<Set<ContextTag>> contextTags = parse.getContextTags();
        assertEquals(4, primitiveChords.size());
        assertEquals(4, keys.size());
        assertEquals(4, contextTags.size());
        assertEquals(new PrimitiveChord(F, ChordType.MAJ, 0), primitiveChords.get(0));
        assertEquals(new PrimitiveChord(C, ChordType.MAJ, 2), primitiveChords.get(1));
        assertEquals(new PrimitiveChord(G, ChordType.MAJ, 0), primitiveChords.get(2));
        assertEquals(new PrimitiveChord(C, ChordType.MAJ, 0), primitiveChords.get(3));
        assertEquals(C_MAJOR, keys.get(0));
        assertEquals(C_MAJOR, keys.get(1));
        assertEquals(C_MAJOR, keys.get(2));
        assertEquals(C_MAJOR, keys.get(3));
        assertEquals(1, contextTags.get(0).size());
        assertTrue(contextTags.get(0).contains(ContextTag.CADENTIAL_PREDOMINANT));
        assertEquals(1, contextTags.get(1).size());
        assertTrue(contextTags.get(1).contains(ContextTag.CADENTIAL_I64));
        assertEquals(1, contextTags.get(2).size());
        assertTrue(contextTags.get(2).contains(ContextTag.CADENTIAL_V));
        assertEquals(1, contextTags.get(3).size());
        assertTrue(contextTags.get(3).contains(ContextTag.CADENCE));
    }
    
    @Test
    public void II65I64Test(){
        ParseResult parse = Parser.parse("KEY:CMaj DMinS1 CMajT2 GMajT0 CMajT0Cad");
        List<PrimitiveChord> primitiveChords = parse.getPrimitiveChords();
        List<Key> keys = parse.getKeys();
        List<Set<ContextTag>> contextTags = parse.getContextTags();
        assertEquals(4, primitiveChords.size());
        assertEquals(4, keys.size());
        assertEquals(4, contextTags.size());
        assertEquals(new PrimitiveChord(D, ChordType.MIN7, 1), primitiveChords.get(0));
        assertEquals(new PrimitiveChord(C, ChordType.MAJ, 2), primitiveChords.get(1));
        assertEquals(new PrimitiveChord(G, ChordType.MAJ, 0), primitiveChords.get(2));
        assertEquals(new PrimitiveChord(C, ChordType.MAJ, 0), primitiveChords.get(3));
        assertEquals(C_MAJOR, keys.get(0));
        assertEquals(C_MAJOR, keys.get(1));
        assertEquals(C_MAJOR, keys.get(2));
        assertEquals(C_MAJOR, keys.get(3));
        assertEquals(1, contextTags.get(0).size());
        assertTrue(contextTags.get(0).contains(ContextTag.CADENTIAL_PREDOMINANT));
        assertEquals(1, contextTags.get(1).size());
        assertTrue(contextTags.get(1).contains(ContextTag.CADENTIAL_I64));
        assertEquals(1, contextTags.get(2).size());
        assertTrue(contextTags.get(2).contains(ContextTag.CADENTIAL_V));
        assertEquals(1, contextTags.get(3).size());
        assertTrue(contextTags.get(3).contains(ContextTag.CADENCE));
    }

}
