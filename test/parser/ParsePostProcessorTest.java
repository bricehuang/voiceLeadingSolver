package parser;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import chord_data.PrimitiveChordWithContext;
import test_framework.MusicTestFramework;

public class ParsePostProcessorTest extends MusicTestFramework {

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }

    @Test
    public void testApplyPostProcessors() {
        List<PrimitiveChordWithContext> preprocess = Arrays.asList(
            new PrimitiveChordWithContext(D_MIN7_65, C_MAJOR, NO_CONTEXTS), 
            new PrimitiveChordWithContext(C_MAJ_64, C_MAJOR, NO_CONTEXTS), 
            new PrimitiveChordWithContext(G_MAJ_ROOT, C_MAJOR, NO_CONTEXTS),
            new PrimitiveChordWithContext(C_MAJ_ROOT, C_MAJOR, CADENCE)
        );
        ParsePostProcessor.applyPostProcessors(preprocess);
        List<PrimitiveChordWithContext> expectedPostProcess = Arrays.asList(
            new PrimitiveChordWithContext(D_MIN7_65, C_MAJOR, CADENTIAL_PREDOMINANT), 
            new PrimitiveChordWithContext(C_MAJ_64, C_MAJOR, CADENTIAL_I64), 
            new PrimitiveChordWithContext(G_MAJ_ROOT, C_MAJOR, CADENTIAL_V),
            new PrimitiveChordWithContext(C_MAJ_ROOT, C_MAJOR, CADENCE)
        );
        assertEquals(expectedPostProcess, preprocess);
    }
}
