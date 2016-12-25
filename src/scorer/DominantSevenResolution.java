package scorer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import chords.Chord;
import chords.ChordType;
import music.BasicInterval;
import music.Interval;
import music.Key;
import music.Note;
import solver.ContextTag;

/**
 * A module for scoring dominant seven resolutions
 */
class DominantSevenResolution {
    
    private static final BasicInterval P1 = new BasicInterval(0, 0);
    private static final BasicInterval m2 = new BasicInterval(1, 1);
    private static final BasicInterval M2 = new BasicInterval(1, 2);
    private static final BasicInterval P4 = new BasicInterval(3, 5);
    private static final BasicInterval P5 = new BasicInterval(4, 7);
    
    private static final Interval UNIS = new Interval(P1, 0, true);
    private static final Interval UP_M2 = new Interval(M2, 0, true); 
    private static final Interval UP_m2 = new Interval(m2, 0, true); 
    private static final Interval DOWN_m2 = new Interval(m2, 0, false); 
    private static final Interval DOWN_M2 = new Interval(M2, 0, false); 
    private static final Interval UP_P4 = new Interval(P4, 0, true); 
    private static final Interval DOWN_P5 = new Interval(P5, 0, false); 
    
    
    private static final List<Set<List<Interval>>> DOM_MAJOR_RESOLUTION_MOVEMENTS = Arrays.asList(
                new HashSet<>(Arrays.asList(
                    Arrays.asList(UP_P4, UP_m2, DOWN_M2, DOWN_m2),
                    Arrays.asList(DOWN_P5, UP_m2, DOWN_M2, DOWN_m2)
                )),
                new HashSet<>(Arrays.asList(
                    Arrays.asList(UNIS, UP_m2, DOWN_M2, DOWN_m2)
                )),
                new HashSet<>(Arrays.asList(
                    Arrays.asList(UNIS, UP_m2, DOWN_M2, DOWN_m2),
                    Arrays.asList(UNIS, UP_m2, UP_M2, DOWN_m2)
                )),                
                new HashSet<>(Arrays.asList(
                    Arrays.asList(UNIS, UP_m2, UP_M2, DOWN_m2)
                ))
            );

    private static final List<Set<List<Interval>>> DOM_MINOR_RESOLUTION_MOVEMENTS = Arrays.asList(
            new HashSet<>(Arrays.asList(
                Arrays.asList(UP_P4, UP_m2, DOWN_M2, DOWN_M2),
                Arrays.asList(DOWN_P5, UP_m2, DOWN_M2, DOWN_M2)
            )),
            new HashSet<>(Arrays.asList(
                Arrays.asList(UNIS, UP_m2, DOWN_M2, DOWN_M2)
            )),
            new HashSet<>(Arrays.asList(
                Arrays.asList(UNIS, UP_m2, DOWN_M2, DOWN_M2),
                Arrays.asList(UNIS, UP_m2, UP_m2, DOWN_M2)
            )),                
            new HashSet<>(Arrays.asList(
                Arrays.asList(UNIS, UP_m2, UP_m2, DOWN_M2)
            ))
        );


    
    
    private static boolean matchesMovements(List<Note> previousSpelled, 
            List<Note> currentSpelled, List<Interval> movements){
        // magic number: 4 = size of chord
        for (int i=0; i<4; i++){
            if (! Interval.melodicIntervalBetween(
                    previousSpelled.get(i), currentSpelled.get(i)
                    ).equals(movements.get(i))){
                return false;
            }
        }
        return true;
    }

    /**
     * Scores for dominant 7th resolutions by mutating an input score
     * @param previous previous chord 
     * @param current current chord
     * @param key key in which this transition should be analyzed
     * @param contextTags any relevant context tags
     * @param score a Score that gets mutated
     */
    static void scoreDomSevenResolutions(Chord previous, Chord current, Key key,
            Set<ContextTag> contextTags, Score score){
        // if previous isn't dom7, don't do anything
        if (previous.getType() != ChordType.DOM7){
            return;
        }
        
        // some sanity checks
        assert(current.getType().equals(ChordType.MAJ) || current.getType().equals(ChordType.MIN));
        if (contextTags.contains(ContextTag.APPLIED_DOMINANT)){
            assert(previous.getPrimitiveChord().getRoot().equals(
                    current.getPrimitiveChord().getRoot().transpose(P5, true))
                    );
        }
        else{
            // magic numbers: 5 is dominant, 1 is tonic
            assert(previous.getPrimitiveChord().getRoot().equals(key.getScaleDegree(5)));
            assert(current.getPrimitiveChord().getRoot().equals(key.getScaleDegree(1)));
        }
        throw new RuntimeException("Unimplemented.");
    }

}
