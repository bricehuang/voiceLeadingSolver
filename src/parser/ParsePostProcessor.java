package parser;

import java.util.Arrays;
import java.util.List;

import chord_data.PrimitiveChordWithContext;

/**
 * Algorithm that applies changes to a List<PrimitiveChordWithContext>
 * specifying a chords progression, post-parsing.  
 */
public interface ParsePostProcessor {
    /*
     * Collection of post-processors, to be run in this order.  If one post-processor
     * should be run after another, it should be put after it in this ordering.  
     * 
     * Corollary: post-processors should not have cyclic run-order dependencies.  
     */
    public static final List<ParsePostProcessor> ALL_POST_PROCESSORS = Arrays.asList(
        new CadenceConsistencyChecker(),
        new CadentialVBackfiller()
    );

    /**
     * Apply this postprocessor's logic to in-progress chord 
     * progression specification.  Mutates the input.  
     * @param inProgressProgression a List<PrimitiveChordWithContext> 
     * specifying a chord progression being post-processed.    
     */
    public void postProcess(List<PrimitiveChordWithContext> preprocess);

    public static void applyPostProcessors(List<PrimitiveChordWithContext> preprocess) {
        for (ParsePostProcessor postProcessor : ALL_POST_PROCESSORS) {
            postProcessor.postProcess(preprocess);
        }
    }

}
