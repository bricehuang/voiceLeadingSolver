package solver;

import java.util.List;

import chord_data.ChordProgressionWithContext;
import chord_data.PrimitiveChordWithContext;

public class Solver {
    
    /**
     * Finds the best chord progressions, post parsing
     * @param primitiveChords output of parser
     * @return List of BestProgressionList.PROGRESSIONS_TO_TRACK 
     * best chord progressions 
     */
    public static List<ChordProgressionWithContext> solve(
        List<PrimitiveChordWithContext> primitiveChordsAndContexts
    ){
        return Sequencer.findBestChordProgressions(
            ChordGenerator.generateChordsWithContext(
                primitiveChordsAndContexts
            )
        );
    }

}
