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
import solver.ContextTag;

/**
 * A module that scores parallel and direct intervals
 */
class ParallelsDirects {
    
    private static final BasicInterval UNISON = new BasicInterval(0,0);
    private static final BasicInterval PERFECT_FIFTH = new BasicInterval(4,7);
    private static final Set<BasicInterval> PERFECT_INTERVALS = Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList(UNISON, PERFECT_FIFTH)));
    
    private static boolean isParallelPerfectInterval(Note previousLower, Note previousUpper, 
            Note currentLower, Note currentUpper){
        BasicInterval previousInterval = BasicInterval.intervalBetween(
                previousLower.getBasicNote(), previousUpper.getBasicNote());
        BasicInterval currentInterval = BasicInterval.intervalBetween(
                currentLower.getBasicNote(), currentUpper.getBasicNote());
        if (previousInterval.equals(currentInterval) && 
                !(previousLower.equals(currentLower) && previousUpper.equals(currentUpper)) &&
                PERFECT_INTERVALS.contains(currentInterval)){
            return true;
        }
        else{
            return false;            
        }
    }
    
    /**
     * Scores for parallel intervals by mutating an input score
     * @param previous previous chord 
     * @param current current chord
     * @param key key in which this transition should be analyzed
     * @param contextTags any relevant context tags
     * @param score a Score that gets mutated
     */
    static void scoreParallels(Chord previous, Chord current, Key key, 
            Set<ContextTag> contextTags, Score score){
        List<Note> previousSpelled = Scorer.spellChord(previous);
        List<Note> currentSpelled = Scorer.spellChord(current);
        
        for (int upper=1; upper<4; upper++){
            for (int lower=0; lower<upper; lower++){
                if (isParallelPerfectInterval(previousSpelled.get(lower),
                        previousSpelled.get(upper),
                        currentSpelled.get(lower),
                        currentSpelled.get(upper))){
                    score.addPenalty(PenaltyType.PARALLEL);
                }
            }
        }
    }

    /**
     * Scores for direct intervals by mutating an input score
     * @param previous previous chord 
     * @param current current chord
     * @param key key in which this transition should be analyzed
     * @param contextTags any relevant context tags
     * @param score a Score that gets mutated
     */
    static void scoreDirects(Chord previous, Chord current, Key key, 
            Set<ContextTag> contextTags, Score score){
        Note prevSoprano = previous.getSoprano();
        Note prevBass = previous.getBass();
        Note currSoprano = current.getSoprano();
        Note currBass = current.getBass();
        
        BasicInterval bassSopranoInterval = BasicInterval.intervalBetween(
                currBass.getBasicNote(), 
                currSoprano.getBasicNote()
                );         
        if (PERFECT_INTERVALS.contains(bassSopranoInterval)){
            Interval sopranoMove = Interval.melodicIntervalBetween(prevSoprano, currSoprano);
            Interval bassMove = Interval.melodicIntervalBetween(prevBass, currBass);

            Integer sopranoNotesMoved = sopranoMove.getIncreasing() ? 
                    sopranoMove.getScaleDegrees() : - sopranoMove.getScaleDegrees();  
            Integer bassNotesMoved = bassMove.getIncreasing() ? 
                    bassMove.getScaleDegrees() : - bassMove.getScaleDegrees();  
            // soprano and bass move same direction, soprano leaps
            if (sopranoNotesMoved * bassNotesMoved > 0 && Math.abs(sopranoNotesMoved) > 1){
                score.addPenalty(PenaltyType.DIRECT);
            }
        }
    }
    
    
}
