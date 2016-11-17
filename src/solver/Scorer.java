package solver;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import chords.Chord;
import music.BasicInterval;
import music.BasicNote;
import music.Interval;
import music.Key;
import music.Note;

/**
 * A module that scores individual chords and transitions between chords  
 */
public class Scorer {
    
    
    private final Key key;
    private final boolean debug;

    
    /*
     * Abstraction Function:
     * Represents a scorer in key key.  
     * Prints debugging output if debug is on.  
     * 
     * Rep Invariant:
     * N/A
     * 
     * Rep Exposure:
     * Returns only immutables
     */
    
    /**
     * Constructs a scorer
     * @param key
     * @param debug prints debugging output if true
     */
    public Scorer(Key key, boolean debug){
        this.key = key;
        this.debug = debug;
    }
    
    /*
     * Heuristics:
     * 
     * Individual chords: 
     * 
     * Doubling rules
     * No voice overlapping 
     * i64 in cadential 64 should double 5 TODO
     * 
     * Transitions:
     * 
     * Small voice movement
     * No Parallels // TODO except resolution of Ger6+? 
     * No Directs
     * No melodic aug/dim intervals
     * No voice crossing
     * Dom7 proper resolution
     * Dim7 proper resolution
     * neopolitan 6 proper resolution // TODO
     * aug6 proper resolutions // TODO implement these chords
     * ii65 hold tonic TODO
     * 
     * Last Transition:
     * 
     * Everything moves stepwise except bass
     * PAC
     * 
     */
    
    // doubling
    private static final int BAD_DOUBLING_PENALTY = 100;
    private static final int DOUBLE_DOUBLING_PENALTY = 200;
    private static final int BAD_TRIPLING_PENALTY = 100;
    private static final int OMITTED_FIFTH_PENALTY = 100;
    private static final Set<Integer> GOOD_NOTES_TO_DOUBLE = Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList(1, 4, 5)));

    // voice overlap
    private static final int VOICE_OVERLAP_PENALTY = 25;
    
    // parallels, directs
    private static final BasicInterval UNISON = new BasicInterval(0,0);
    private static final BasicInterval PERFECT_FIFTH = new BasicInterval(4,7);
    private static final Set<BasicInterval> PERFECT_INTERVALS = Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList(UNISON, PERFECT_FIFTH)));
    private static final int PARALLEL_INTERVAL_PENALTY = 1000000;
    private static final int DIRECT_INTERVAL_PENALTY = 1000000;
    
    /**
     * Returns a list spelling the notes of a chord
     * @param chord a chord
     * @return a list of the 4 notes of a chord, spelled from
     * the bass up
     */
    private static List<Note> spellChord(Chord chord){
        return Arrays.asList(chord.getBass(), 
                chord.getTenor(), chord.getAlto(), chord.getSoprano());
    }
    
    /*********************************
     * scoreChord and helper methods *
     *********************************/
    
    /**
     * Scores the badness of a chord's doubling 
     * @param chord current chord
     * @param key key in which this chord should be analyzed
     * @return score
     */
    private Integer scoreDoubling(Chord chord){

        if (chord.getType().numberDistinctNotes() == 4){
            return 0;
        }
        
        int score = 0;
        
        List<BasicNote> triad = chord.getPrimitiveChord().noteList();
        List<Note> chordSpelled = spellChord(chord);       
        // counts of each note
        int root = 0;
        int third = 0;
        int fifth = 0;
        for (Note note: chordSpelled){
            if (note.getBasicNote().equals(triad.get(0))){
                root++;
            }
            else if (note.getBasicNote().equals(triad.get(1))){
                third++;
            }
            else if (note.getBasicNote().equals(triad.get(2))){
                fifth++;
            }
            else{
                throw new RuntimeException("Should not get here.");
            }
        }
        if (fifth == 0){
            score += OMITTED_FIFTH_PENALTY;
            if (debug){
                System.err.println("Missing fifth penalty: "+OMITTED_FIFTH_PENALTY);
            }
            if (root == 3 && third == 1){
                if (!GOOD_NOTES_TO_DOUBLE.contains(key.findScaleDegree(triad.get(0)))){
                    score += BAD_TRIPLING_PENALTY;
                    if (debug){
                        System.err.println("Bad Tripling Penalty: "+BAD_TRIPLING_PENALTY);                        
                    }
                }
            }
            else if (root == 2 && third == 2){
                score += DOUBLE_DOUBLING_PENALTY;
                if (debug){
                    System.err.println("Double Doubling Penalty: "+DOUBLE_DOUBLING_PENALTY);                        
                }
            }
            else if (root == 1 && third == 3){
                if (!GOOD_NOTES_TO_DOUBLE.contains(key.findScaleDegree(triad.get(2)))){
                    score += BAD_TRIPLING_PENALTY;
                    if (debug){
                        System.err.println("Bad Tripling Penalty: "+BAD_TRIPLING_PENALTY);                        
                    }
                }
            }
            
        }
        else if (root >= 1 && third >= 1 && fifth >= 1){
            BasicNote doubled;
            if (root == 2){
                doubled = triad.get(0); 
            }
            else if (third == 2){
                doubled = triad.get(1);
            }
            else if (fifth == 2){
                doubled = triad.get(2);
            }
            else{
                throw new RuntimeException("Should not get here.");
            }
            if (!GOOD_NOTES_TO_DOUBLE.contains(key.findScaleDegree(doubled))){
                score += BAD_DOUBLING_PENALTY;
                if (debug){
                    System.err.println("Doubling Penalty: "+BAD_DOUBLING_PENALTY);                    
                }
            }
        }
        else{
            throw new RuntimeException("Should not get here.");
        }        
        return score;
    }
    
    /**
     * Scores the badness of voice overlapping
     * @param chord current chord
     * @param key key in which this chord should be analyzed
     * @return score
     */
    private Integer scoreVoiceOverlap(Chord chord){
        int score = 0;
        List<Note> chordSpelled = spellChord(chord);
        for (int i=0; i<3; i++){
            if (chordSpelled.get(i).equals(chordSpelled.get(i+1))){
                score += VOICE_OVERLAP_PENALTY;
                if (debug){
                    System.err.println("Voice Overlap Penalty: "+VOICE_OVERLAP_PENALTY); 
                }
            }
        }
        return score;
    }
    
    /**
     * Scores the badness of a chord 
     * @param chord current chord
     * @param key key in which this chord should be analyzed
     * @return a score representing this chord's badness
     */
    public Integer scoreChord(Chord chord){
        if (debug){
            System.err.println("Scoring Chord "+chord.toString()+" in key "+key.toString());
        }
        int score = 0;
        score += scoreDoubling(chord);
        score += scoreVoiceOverlap(chord);
        if (debug){
            System.err.println("Total Penalty: "+score+"\n");
        }
        return score;
    }
    
    /**************************************
     * scoreTransition and helper methods *
     **************************************/

    /**
     * Scores for small movements
     * @param previous previous chord 
     * @param current current chord
     * @param key key in which this transition should be analyzed
     * @return score
     */
    private Integer scoreSmallMovement(Chord previous, Chord current){
        throw new RuntimeException("Unimplemented.");
    }

    /**
     * Scores for parallel perfect intervals
     * @param previous previous chord 
     * @param current current chord
     * @param key key in which this transition should be analyzed
     * @return score
     */
    private Integer scoreParallels(Chord previous, Chord current){
        int score = 0;
        List<Note> previousSpelled = spellChord(previous);
        List<Note> currentSpelled = spellChord(current);
        
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
    private Integer scoreDirects(Chord previous, Chord current){
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

    /**
     * Scores for bad melodic intervals
     * @param previous previous chord 
     * @param current current chord
     * @param key key in which this transition should be analyzed
     * @return score
     */
    private Integer scoreMelodicIntervals(Chord previous, Chord current){
        throw new RuntimeException("Unimplemented.");
    }

    /**
     * Scores for voice crossing
     * @param previous previous chord 
     * @param current current chord
     * @param key key in which this transition should be analyzed
     * @return score
     */
    private Integer scoreVoiceCrossing(Chord previous, Chord current){
        throw new RuntimeException("Unimplemented.");
    }

    /**
     * Scores for dominant 7th resolutions
     * @param previous previous chord 
     * @param current current chord
     * @param key key in which this transition should be analyzed
     * @return score
     */
    private Integer scoreDomSevenResolutions(Chord previous, Chord current){
        throw new RuntimeException("Unimplemented.");
    }

    /**
     * Scores for diminished 7th resolutions
     * @param previous previous chord 
     * @param current current chord
     * @param key key in which this transition should be analyzed
     * @return score
     */
    private Integer scoreDimSevenResolutions(Chord previous, Chord current){
        throw new RuntimeException("Unimplemented.");
    }

    /**
     * Scores the badness of a transition 
     * @param previous previous chord 
     * @param current current chord
     * @param key key in which this transition should be analyzed
     * @return a score representing this transition's badness
     */
    public Integer scoreTransition(Chord previous, Chord current){
        if (debug){
            System.err.println("Scoring Transition " + previous.toString() 
            + " --> " + current.toString() + " in key "+key.toString());
        }
        int score = 0;
        //score += scoreSmallMovement(previous, current);
        score += scoreParallels(previous, current);
        score += scoreDirects(previous, current);
        //score += scoreMelodicIntervals(previous, current);
        //score += scoreVoiceCrossing(previous, current);
        //score += scoreDomSevenResolutions(previous, current);
        //score += scoreDimSevenResolutions(previous, current);
        if (debug){
            System.err.println("Total Penalty: "+score+"\n");
        }
        return score;
    }
    
    /****************************************************
     * scoreLastTransitionAdditional and helper methods *
     ****************************************************/
    
    /**
     * Scores for everything moves stepwise in final cadence
     * @param previous previous chord 
     * @param current current chord
     * @param key key in which this transition should be analyzed
     * @return score
     */
    private Integer scoreEverythingStepwise(Chord previous, Chord current){
        throw new RuntimeException("Unimplemented.");
    }

    /**
     * Scores for PAC
     * @param previous previous chord 
     * @param current current chord
     * @param key key in which this transition should be analyzed
     * @return score
     */
    private Integer scorePAC(Chord previous, Chord current){
        throw new RuntimeException("Unimplemented.");
    }

    
    /**
     * Additional score for last transition 
     * @param previous previous chord 
     * @param current current chord
     * @param key key in which this transition should be analyzed
     * @return a score representing this transition's additional badness
     */
    public Integer scoreLastTransitionAdditional(Chord previous, Chord current){
        throw new RuntimeException("Unimplemented.");
    }

    
}
