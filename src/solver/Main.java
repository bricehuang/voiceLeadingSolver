package solver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import chord_data.ChordProgressionWithContext;
import chord_data.PrimitiveChordWithContext;
import player.ProgressionPlayer;

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
    public static List<ChordProgressionWithContext> solve(String in, boolean report, int maxReport){
        List<PrimitiveChordWithContext> parsedInput = ParserDeprecated.parse(in);
        List<ChordProgressionWithContext> bestProgressions = 
            Solver.solve(parsedInput);
        if (report){
            System.out.println("Results report on input: \n" + in + "\n");
            for (int i=0; i<Math.min(maxReport, bestProgressions.size()); i++){
                ChordProgressionWithContext progression = bestProgressions.get(i);
                System.out.println(
                    "Progression " + (i+1) + ":\n" 
                    + progression.toString() + "\n\n"
                    + Sequencer.getPenaltyReport(progression)
                );
            }
        }
        return bestProgressions;
    }
    
    /**
     * Finds the best chord progression on a given input and plays it
     * @param in the input
     * @throws MidiUnavailableException 
     * @throws InvalidMidiDataException 
     */
    public static void solveAndPlay(String in) 
            throws InvalidMidiDataException, MidiUnavailableException{
        ChordProgressionWithContext best = solve(in, DEFAULT_REPORT, DEFAULT_MAXREPORT).get(0);  
        ProgressionPlayer.playProgression(best); 
    }

    /**
     * Finds the best chord progression on a given input and plays it
     * @param filename the file containing the input
     * @throws IOException 
     * @throws MidiUnavailableException 
     * @throws InvalidMidiDataException 
     */
    public static void solveAndPlayFile(String filename) 
            throws IOException, InvalidMidiDataException, MidiUnavailableException{
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String in = "";
        for(String line = reader.readLine(); line != null; line = reader.readLine()){
            in += (line + "\n");
        }
        reader.close();
        solveAndPlay(in);
    }
    
    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("usage: java satb.player.Main mysatbfile.satb [--debug]");
        }
        else{
            try {
                solveAndPlayFile(args[0]);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidMidiDataException e) {
                e.printStackTrace();
            } catch (MidiUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

}
