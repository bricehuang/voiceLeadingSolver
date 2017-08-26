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
        new PrimitiveChordWithContext(D_MIN7_65, C_MAJOR, CADENTIAL_PREDOMINANT);

    private static final PrimitiveChordWithContext I64_NO_TAG =
        new PrimitiveChordWithContext(C_MAJ_64, C_MAJOR, NO_CONTEXTS);
    private static final PrimitiveChordWithContext I6_NOT_CADENTIAL_NO_TAG =
        new PrimitiveChordWithContext(C_MAJ_6, C_MAJOR, NO_CONTEXTS);
    private static final PrimitiveChordWithContext I64_WITH_TAG =
        new PrimitiveChordWithContext(C_MAJ_64, C_MAJOR, CADENTIAL_I64);

    private static final PrimitiveChordWithContext V_NO_TAG =
        new PrimitiveChordWithContext(G_MAJ_ROOT, C_MAJOR, NO_CONTEXTS);
    private static final PrimitiveChordWithContext V_NOT_CADENTIAL_NO_TAG =
        new PrimitiveChordWithContext(G_MAJ_6, C_MAJOR, NO_CONTEXTS);
    private static final PrimitiveChordWithContext V_WITH_TAG =
        new PrimitiveChordWithContext(G_MAJ_ROOT, C_MAJOR, CADENTIAL_V);

    private static final PrimitiveChordWithContext I_NO_TAG =
        new PrimitiveChordWithContext(C_MAJ_ROOT, C_MAJOR, NO_CONTEXTS);
    private static final PrimitiveChordWithContext I_WITH_TAG =
        new PrimitiveChordWithContext(C_MAJ_ROOT, C_MAJOR, CADENCE);

    private static final PrimitiveChordWithContext I_BAD_TAG =
        new PrimitiveChordWithContext(C_MAJ_64, C_MAJOR, CADENCE);

    private static final PrimitiveChordWithContext PRE_V_NO_TAG_2 =
        new PrimitiveChordWithContext(Fs_DIM_6, E_MINOR, NO_CONTEXTS);
    private static final PrimitiveChordWithContext PRE_V_WITH_TAG_2 =
        new PrimitiveChordWithContext(Fs_DIM_6, E_MINOR, CADENTIAL_PREDOMINANT);

    private static final PrimitiveChordWithContext V_WITH_TAG_2 =
        new PrimitiveChordWithContext(B_MAJ_ROOT, E_MINOR, CADENTIAL_V);

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


    @Test
    public void testCadentialVBackfillerGood() {
        List<PrimitiveChordWithContext> preprocess = Arrays.asList(
            V_NO_TAG, I_WITH_TAG
        );
        new CadentialVBackfiller().postProcess(preprocess);
        List<PrimitiveChordWithContext> expectedPostProcess = Arrays.asList(
            V_WITH_TAG, I_WITH_TAG
        );
        assertEquals(expectedPostProcess, preprocess);
    }

    @Test
    public void testCadentialVBackfillerNotV() {
        List<PrimitiveChordWithContext> preprocess = Arrays.asList(
            PRE_V_NO_TAG, I_WITH_TAG
        );
        new CadentialVBackfiller().postProcess(preprocess);
        List<PrimitiveChordWithContext> expectedPostProcess = Arrays.asList(
            PRE_V_NO_TAG, I_WITH_TAG
        );
        assertEquals(expectedPostProcess, preprocess);
    }

    @Test
    public void testCadentialVBackfillerNotCadentialV() {
        List<PrimitiveChordWithContext> preprocess = Arrays.asList(
            V_NOT_CADENTIAL_NO_TAG, I_WITH_TAG
        );
        new CadentialVBackfiller().postProcess(preprocess);
        List<PrimitiveChordWithContext> expectedPostProcess = Arrays.asList(
            V_NOT_CADENTIAL_NO_TAG, I_WITH_TAG
        );
        assertEquals(expectedPostProcess, preprocess);
    }


    @Test
    public void testCadentialI64BackfillerGood() {
        List<PrimitiveChordWithContext> preprocess = Arrays.asList(
            I64_NO_TAG, V_WITH_TAG
        );
        new CadentialI64Backfiller().postProcess(preprocess);
        List<PrimitiveChordWithContext> expectedPostProcess = Arrays.asList(
            I64_WITH_TAG, V_WITH_TAG
        );
        assertEquals(expectedPostProcess, preprocess);
    }

    @Test
    public void testCadentialI64BackfillerNotI64() {
        List<PrimitiveChordWithContext> preprocess = Arrays.asList(
            PRE_V_NO_TAG, V_WITH_TAG
        );
        new CadentialI64Backfiller().postProcess(preprocess);
        List<PrimitiveChordWithContext> expectedPostProcess = Arrays.asList(
            PRE_V_NO_TAG, V_WITH_TAG
        );
        assertEquals(expectedPostProcess, preprocess);
    }

    @Test
    public void testCadentiaI64VBackfillerNotCadentialI64() {
        List<PrimitiveChordWithContext> preprocess = Arrays.asList(
            I6_NOT_CADENTIAL_NO_TAG, V_WITH_TAG
        );
        new CadentialI64Backfiller().postProcess(preprocess);
        List<PrimitiveChordWithContext> expectedPostProcess = Arrays.asList(
            I6_NOT_CADENTIAL_NO_TAG, V_WITH_TAG
        );
        assertEquals(expectedPostProcess, preprocess);
    }    
    
    @Test
    public void testCadentialPreVBackfillerGood() {
        List<PrimitiveChordWithContext> preprocess = Arrays.asList(
            PRE_V_NO_TAG, I64_WITH_TAG
        );
        new CadentialPreVBackfiller().postProcess(preprocess);
        List<PrimitiveChordWithContext> expectedPostProcess = Arrays.asList(
            PRE_V_WITH_TAG, I64_WITH_TAG
        );
        assertEquals(expectedPostProcess, preprocess);
    }

    @Test
    public void testCadentialPreVBackfillerGood1() {
        List<PrimitiveChordWithContext> preprocess = Arrays.asList(
            PRE_V_NO_TAG, V_WITH_TAG
        );
        new CadentialPreVBackfiller().postProcess(preprocess);
        List<PrimitiveChordWithContext> expectedPostProcess = Arrays.asList(
            PRE_V_WITH_TAG, V_WITH_TAG
        );
        assertEquals(expectedPostProcess, preprocess);
    }

    @Test
    public void testCadentialPreVBackfillerGood2() {
        List<PrimitiveChordWithContext> preprocess = Arrays.asList(
            PRE_V_NO_TAG_2, V_WITH_TAG_2
        );
        new CadentialPreVBackfiller().postProcess(preprocess);
        List<PrimitiveChordWithContext> expectedPostProcess = Arrays.asList(
            PRE_V_WITH_TAG_2, V_WITH_TAG_2
        );
        assertEquals(expectedPostProcess, preprocess);
    }

    @Test
    public void testCadentialPreVBackfillerNotPreV() {
        List<PrimitiveChordWithContext> preprocess = Arrays.asList(
            I_NO_TAG, V_WITH_TAG
        );
        new CadentialPreVBackfiller().postProcess(preprocess);
        List<PrimitiveChordWithContext> expectedPostProcess = Arrays.asList(
            I_NO_TAG, V_WITH_TAG
        );
        assertEquals(expectedPostProcess, preprocess);
    }

}
