package parser;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import chord_data.PrimitiveChordWithContext;
import test_framework.MusicTestFramework;

public class ParsePostProcessorTest extends MusicTestFramework {
    
    private static final PrimitiveChordWithContext PRE_V_NO_TAG = 
        new PrimitiveChordWithContext(D_MIN7_65, C_MAJOR, NO_CONTEXTS); 
    private static final PrimitiveChordWithContext PRE_V_WITH_TAG = 
        new PrimitiveChordWithContext(D_MIN7_65, C_MAJOR, NO_CONTEXTS); 

    private static final PrimitiveChordWithContext I64_NO_TAG = 
        new PrimitiveChordWithContext(C_MAJ_64, C_MAJOR, NO_CONTEXTS); 
    private static final PrimitiveChordWithContext I64_WITH_TAG = 
        new PrimitiveChordWithContext(C_MAJ_64, C_MAJOR, CADENTIAL_I64); 
    
    private static final PrimitiveChordWithContext V_NO_TAG = 
        new PrimitiveChordWithContext(G_MAJ_ROOT, C_MAJOR, NO_CONTEXTS);            
    private static final PrimitiveChordWithContext V_WITH_TAG = 
        new PrimitiveChordWithContext(G_MAJ_ROOT, C_MAJOR, CADENTIAL_V);            

    private static final PrimitiveChordWithContext I_WITH_TAG = 
        new PrimitiveChordWithContext(C_MAJ_ROOT, C_MAJOR, CADENCE);            

    private static final PrimitiveChordWithContext I_BAD_TAG = 
        new PrimitiveChordWithContext(C_MAJ_64, C_MAJOR, CADENCE);            
    
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }

    @Test
    public void testApplyPostProcessors() {
        List<PrimitiveChordWithContext> preprocess = Arrays.asList(
            PRE_V_NO_TAG, I64_NO_TAG, V_NO_TAG, I_WITH_TAG
        );
        ParsePostProcessor.applyPostProcessors(preprocess);
        List<PrimitiveChordWithContext> expectedPostProcess = Arrays.asList(
            PRE_V_WITH_TAG, I64_WITH_TAG, V_WITH_TAG, I_WITH_TAG
        );
        assertEquals(expectedPostProcess, preprocess);
    }
    
    @Test
    public void testCadenceConsistencyCheckerGood() {
        List<PrimitiveChordWithContext> preprocess = Arrays.asList(
            PRE_V_NO_TAG, I64_NO_TAG, V_NO_TAG, I_WITH_TAG
        );
        new CadenceConsistencyChecker().postProcess(preprocess);
    }

    @Test(expected=AssertionError.class)
    public void testCadenceConsistencyCheckerBad() {
        List<PrimitiveChordWithContext> preprocess = Arrays.asList(
            PRE_V_NO_TAG, I64_NO_TAG, V_NO_TAG, I_BAD_TAG
        );
        new CadenceConsistencyChecker().postProcess(preprocess);
    }
    
}
