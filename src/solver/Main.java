package solver;

import java.util.List;

import chords.ChordProgression;
import scorer.Scorer;

/**
 * Main module, which parses an input and finds the best chord progressions
 * for it
 */
public class Main {
    
    public static List<ChordProgression> solve(String in, boolean report, int maxReport){
        ParseResult parsedInput = Parser.parse(in);
        List<ChordProgression> bestProgressions = Solver.solve(
                parsedInput.getPrimitiveChords(), 
                parsedInput.getKeys(), 
                parsedInput.getContextTags());
        if (report){
            System.out.println("Results report on input: \n" + in + "\n");
            for (int i=0; i<Math.min(maxReport, bestProgressions.size()); i++){
                System.out.println("Progression " + (i+1) + ":\n" 
                        + bestProgressions.get(i).toString() + "\n\n"
                        + Scorer.evaluateChordProgression(
                                bestProgressions.get(i), 
                                parsedInput.getKeys(), 
                                parsedInput.getContextTags()));
            }
        }
        return bestProgressions;
    }
    
    // TODO: solve and play
}
