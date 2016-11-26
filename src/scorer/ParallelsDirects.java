package scorer;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import chords.Chord;
import music.BasicInterval;
import music.Interval;
import music.Key;
import music.Note;

/**
 * A module that scores parallel and direct intervals
 */
public class ParallelsDirects {

    private static final int PARALLEL_INTERVAL_PENALTY = 1000000;
    private static final int DIRECT_INTERVAL_PENALTY = 1000000;

    private static final BasicInterval UNISON = new BasicInterval(0,0);
    private static final BasicInterval PERFECT_FIFTH = new BasicInterval(4,7);
    private static final Set<BasicInterval> PERFECT_INTERVALS = Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList(UNISON, PERFECT_FIFTH)));
    
    /**
     * Scores for parallel perfect intervals
     * @param previous previous chord 
     * @param current current chord
     * @param key key in which this transition should be analyzed
     * @return score
     */
    static Integer scoreParallels(Chord previous, Chord current, Key key, boolean debug){
        int score = 0;
        List<Note> previousSpelled = Scorer.spellChord(previous);
        List<Note> currentSpelled = Scorer.spellChord(current);
        
        for (int upper=1; upper<4; upper++){
            for (int lower=0; lower<upper; lower++){
                Note lowerPrevNote = previousSpelled.get(lower);
                Note upperPrevNote = previousSpelled.get(upper); 
                Note lowerCurrNote = currentSpelled.get(lower);
                Note upperCurrNote = currentSpelled.get(upper); 
                BasicInterval previousInterval = BasicInterval.intervalBetween(
                        lowerPrevNote.getBasicNote(), 
                        upperPrevNote.getBasicNote()
                        );
                BasicInterval currentInterval = BasicInterval.intervalBetween(
                        lowerCurrNote.getBasicNote(), 
                        upperCurrNote.getBasicNote()
                        );
                if (previousInterval.equals(currentInterval) && 
                        !(lowerPrevNote.equals(lowerCurrNote) && 
                                upperPrevNote.equals(upperCurrNote)) &&
                        PERFECT_INTERVALS.contains(currentInterval)){
                    score += PARALLEL_INTERVAL_PENALTY;
                    if (debug){
                        System.err.println("Parallel Interval Penalty: "+PARALLEL_INTERVAL_PENALTY); 
                    }
                }
            }
        }
        return score;
    }

    /**
     * Scores for direct perfect intervals
     * @param previous previous chord 
     * @param current current chord
     * @param key key in which this transition should be analyzed
     * @return score
     */
    static Integer scoreDirects(Chord previous, Chord current, Key key, boolean debug){
        Note prevSoprano = previous.getSoprano();
        Note prevBass = previous.getBass();
        Note currSoprano = current.getSoprano();
        Note currBass = current.getBass();
        
        BasicInterval bassSopranoInterval = BasicInterval.intervalBetween(
                currBass.getBasicNote(), 
                currSoprano.getBasicNote()
                );         
        if (!PERFECT_INTERVALS.contains(bassSopranoInterval)){
            return 0;
        }
        Interval sopranoMove = Interval.melodicIntervalBetween(prevSoprano, currSoprano);
        Integer sopranoNotesMoved = sopranoMove.getIncreasing() ? 
                sopranoMove.getScaleDegrees() : - sopranoMove.getScaleDegrees();  
        Interval bassMove = Interval.melodicIntervalBetween(prevBass, currBass);
        Integer bassNotesMoved = bassMove.getIncreasing() ? 
                bassMove.getScaleDegrees() : - bassMove.getScaleDegrees();  
        if (sopranoNotesMoved * bassNotesMoved <= 0){
            return 0;
        }
        if (Math.abs(sopranoNotesMoved) == 1){
            return 0;
        }
        if (debug){
            System.err.println("Direct Interval Penalty: "+DIRECT_INTERVAL_PENALTY);
        }
        return DIRECT_INTERVAL_PENALTY;        
    }
    
    
}
