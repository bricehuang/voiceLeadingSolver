package scorer;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import chords.Chord;
import chords.ChordType;
import music.BasicInterval;
import music.BasicNote;
import music.Interval;
import music.Key;
import music.Note;
import solver.ContextTag;

/**
 * A module for scoring dominant seven resolutions
 */
class SevenChordResolution {
    
    // TODO: refactor code duplication for scoring dom and dim methods
    // TODO: alternative dim 7 resolutions? if dim7 has options like dom7, 
    // refactoring may be easier
    
    private static final BasicInterval P1 = new BasicInterval(0, 0);
    private static final BasicInterval m2 = new BasicInterval(1, 1);
    private static final BasicInterval M2 = new BasicInterval(1, 2);
    private static final BasicInterval P4 = new BasicInterval(3, 5);
    private static final BasicInterval P5 = new BasicInterval(4, 7);
    private static final BasicInterval M7 = new BasicInterval(6, 11);

    
    private static final Interval UNIS = new Interval(P1, 0, true);
    private static final Interval UP_M2 = new Interval(M2, 0, true); 
    private static final Interval UP_m2 = new Interval(m2, 0, true); 
    private static final Interval DOWN_m2 = new Interval(m2, 0, false); 
    private static final Interval DOWN_M2 = new Interval(M2, 0, false); 
    private static final Interval UP_P4 = new Interval(P4, 0, true); 
    private static final Interval DOWN_P5 = new Interval(P5, 0, false); 
    
    
    private static final Map<ChordType, List<Set<List<Interval>>>> DOM_RESOLUTIONS; 
    static{
        final List<Set<List<Interval>>> domMajorResolutionMovements = Arrays.asList(
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

        final List<Set<List<Interval>>> domMinorResolutionMovements = Arrays.asList(
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
        Map<ChordType, List<Set<List<Interval>>>> resolutionsTmp = new HashMap<>();
        resolutionsTmp.put(ChordType.MAJ, domMajorResolutionMovements);
        resolutionsTmp.put(ChordType.MIN, domMinorResolutionMovements);
        DOM_RESOLUTIONS = Collections.unmodifiableMap(resolutionsTmp);
    }

    private static final Map<ChordType, List<Interval>> DIM_RESOLUTIONS;
    static{
        final List<Interval> dimMajorResolutionMovement = Arrays.asList(
                UP_m2, UP_M2, DOWN_m2, DOWN_m2);
        final List<Interval> dimMinorResolutionMovement = Arrays.asList(
                UP_m2, UP_m2, DOWN_M2, DOWN_m2);
        Map<ChordType, List<Interval>> resolutionsTmp = new HashMap<>();
        resolutionsTmp.put(ChordType.MAJ, dimMajorResolutionMovement);
        resolutionsTmp.put(ChordType.MIN, dimMinorResolutionMovement);
        DIM_RESOLUTIONS = Collections.unmodifiableMap(resolutionsTmp);
    }
    
    
    /**
     * Helper function. Returns the index of a note, when spelled in a dominant chord,
     * from the bottom up. 
     * @param note a note. Must be in a dominant 7 chord.  
     * @param domRoot root of the dominant 7 chord. 
     * @return ''
     */
    private static int getIndexOfSevChordNote(Note note, BasicNote domRoot){
        return BasicInterval.intervalBetween(domRoot, note.getBasicNote()).getScaleDegrees()/2;
    }
    
    /**
     * Checks if in the transition from previousSpelled to currentSpelled, 
     * the voices move by four specified intervals 
     * @param previous previous chord. Must be dominant 7. 
     * @param current current chord. Must be major or minor. 
     * @param movements expected movements for root, 3rd, 5th, and 7th of dom chord
     * @return true if previous --> current followed movements
     */
    private static boolean matchesMovements(Chord previous, 
            Chord current, List<Interval> movements){
        List<Note> previousSpelled = Scorer.spellChord(previous);
        List<Note> currentSpelled = Scorer.spellChord(current);
        BasicNote domRoot = previous.getPrimitiveChord().getRoot();
        
        // magic number: 4 = size of chord
        for (int i=0; i<4; i++){
            Interval actualInterval = Interval.melodicIntervalBetween(
                    previousSpelled.get(i), currentSpelled.get(i));
            Interval expectedInterval = movements.get(
                    getIndexOfSevChordNote(previousSpelled.get(i), domRoot)); 
            if (!actualInterval.equals(expectedInterval)){
                return false;
            }
        }
        return true;
    }
    
    /**
     * Checks if transition from previous to current is valid dom7 resolution
     * @param previous previous chord. Must be dominant 7. 
     * @param current current chord. Must be major or minor. 
     * @return true if this transition is valid. 
     */
    private static boolean isValidDominantResolution(Chord previous, Chord current){
        Set<List<Interval>> properResolutions = DOM_RESOLUTIONS.get(current.getType()).get(
                previous.getPrimitiveChord().getInversion());
        for (List<Interval> movements : properResolutions){
            if (matchesMovements(previous, current, movements)){
                return true;
            }
        }
        return false;
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
        if (! isValidDominantResolution(previous, current)){
            score.addPenalty(PenaltyType.DOM_SEVEN_RES);
        }
    }
    
    /**
     * Checks if transition from previous to current is valid dim resolution
     * @param previous previous chord. Must be diminished 7. 
     * @param current current chord. Must be major or minor. 
     * @return true if this transition is valid. 
     */
    private static boolean isValidDiminishedResolution(Chord previous, Chord current){
        List<Interval> movements = DIM_RESOLUTIONS.get(current.getType());
        if (matchesMovements(previous, current, movements)){
            return true;
        }
        return false;
    }
    
    /**
     * Scores for diminished 7th resolutions by mutating an input score
     * @param previous previous chord 
     * @param current current chord
     * @param key key in which this transition should be analyzed
     * @param contextTags any relevant context tags
     * @param score a Score that gets mutated
     */
    static void scoreDimSevenResolutions(Chord previous, Chord current, Key key,
            Set<ContextTag> contextTags, Score score){
        // if previous isn't dim7, don't do anything
        if (previous.getType() != ChordType.DIM7){
            return;
        }
        // sanity checks
        assert(current.getType().equals(ChordType.MAJ) || current.getType().equals(ChordType.MIN));
        if (contextTags.contains(ContextTag.APPLIED_DOMINANT)){
            assert(previous.getPrimitiveChord().getRoot().equals(
                    current.getPrimitiveChord().getRoot().transpose(M7, true))
                    );
        }
        else{
            // magic numbers: 7 is leading tone, 1 is tonic
            assert(previous.getPrimitiveChord().getRoot().equals(key.getScaleDegree(7)));
            assert(current.getPrimitiveChord().getRoot().equals(key.getScaleDegree(1)));
        }        
        if (! isValidDiminishedResolution(previous, current)){
            score.addPenalty(PenaltyType.DIM_SEVEN_RES);
        }
    }

}
