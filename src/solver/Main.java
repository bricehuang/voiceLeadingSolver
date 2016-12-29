package solver;

import java.util.List;

import chords.ChordProgression;

/**
 * Main module, which parses an input and finds the best chord progressions
 * for it
 */
public class Main {
    
    public static List<ChordProgression> solve(String in){
        ParseResult parsedInput = Parser.parse(in);
        return Solver.solve(
                parsedInput.getPrimitiveChords(), 
                parsedInput.getKeys(), 
                parsedInput.getContextTags());
    }
    
    // TODO: solve and play
}
