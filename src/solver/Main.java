package solver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import chords.ChordProgression;
import player.ProgressionPlayer;
import scorer.Scorer;

/**
 * Main module, which parses an input and finds the best chord progressions
 * for it
 */
public class Main {
    
    private static boolean DEFAULT_REPORT = true;
    private static int DEFAULT_MAXREPORT = 1;
    
    /**
     * Finds the best chord progressions on a given input 
     * @param in the input
     * @param report if true, prints evaluation of best progressions
     * @param maxReport maximum number of best progressions to report
     * @return a List of top ChordProgressions of length at most
     * SortedFiniteProgList.PROGRESSIONS_TO_TRACK
     */
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
    
    /**
     * Finds the best chord progression on a given input and plays it
     * @param in the input
     */
    public static void solveAndPlay(String in){
        ChordProgression best = solve(in, DEFAULT_REPORT, DEFAULT_MAXREPORT).get(0);  
        ProgressionPlayer.playProgression(best); 
    }

    /**
     * Finds the best chord progression on a given input and plays it
     * @param filename the file containing the input
     * @throws IOException 
     */
    public static void solveAndPlayFile(String filename) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String in = "";
        for(String line = reader.readLine(); line != null; line = reader.readLine()){
            in += (line + "\n");
        }
        reader.close();
        solveAndPlay(in);
    }

}
