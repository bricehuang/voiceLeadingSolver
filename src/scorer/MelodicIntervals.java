package scorer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import chord_data.ContextTag;
import chords.Chord;
import music.BasicInterval;
import music.Interval;
import music.IntervalQuality;
import music.Key;
import music.Note;

/**
 * A module that scores for good melodic intervals
 */
class MelodicIntervals {
    
    private static final BasicInterval SEMITONE = new BasicInterval(0,1);
    private static final Interval SEMITONE_UP = new Interval(SEMITONE, 0, true);
    private static final Interval SEMITONE_DOWN = new Interval(SEMITONE, 0, false);
    
    
    private static final Set<Interval> SEMITONE_STEPS = new HashSet<>(
            Arrays.asList(SEMITONE_UP, SEMITONE_DOWN));
    private static final Set<IntervalQuality> ACCEPTABLE_QUALITIES = new HashSet<>(
            Arrays.asList(IntervalQuality.MAJ, IntervalQuality.MIN, IntervalQuality.PFT)
            );
    
    /**
     * Scores for bad melodic intervals by mutating an input score
     * @param previous previous chord 
     * @param current current chord
     * @param contextTagsPrevious context tags associated with Previous
     * @param contextTagsCurrent context tags associated with current
     * @param key key in which this transition should be analyzed
     * @param score a Score that gets mutated
     */
    static void scoreMelodicIntervals(Chord previous, Chord current,
            Set<ContextTag> contextTagsPrevious, Set<ContextTag> contextTagsCurrent,
            Key key, Score score){
        List<Note> previousSpelled = Scorer.spellChord(previous);
        List<Note> currentSpelled = Scorer.spellChord(current);
        for (int i=0; i<4; i++){
            Interval interval = Interval.melodicIntervalBetween(
                    previousSpelled.get(i), currentSpelled.get(i));
            if (!ACCEPTABLE_QUALITIES.contains(interval.getQuality()) && 
                    !SEMITONE_STEPS.contains(interval)){
                score.addPenalty(PenaltyType.MELODIC_INTERVAL);
            }
        }
    }

}
