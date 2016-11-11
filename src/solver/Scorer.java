package solver;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import chords.Chord;
import music.BasicNote;
import music.Key;
import music.Note;

/**
 * A module that scores individual chords and transitions between chords  
 */
public class Scorer {
    
    /*
     * Heuristics:
     * 
     * Individual chords: 
     * 
     * Doubling rules
     * No voice overlapping 
     * 
     * 
     * Transitions:
     * 
     * Small voice movement
     * No Parallels
     * No Directs
     * No melodic aug/dim intervals
     * No voice crossing
     * Dom7, Dim7, proper resolutions
     * ii65 hold tonic TODO
     * 
     * Last Transition:
     * 
     * Everything moves stepwise except bass
     * PAC
     * 
     */
    
    public static final int BAD_DOUBLING_PENALTY = 50;
    public static final int DOUBLE_DOUBLING_PENALTY = 50;
    public static final int BAD_TRIPLING_PENALTY = 50;
    public static final int OMITTED_FIFTH_PENALTY = 50;
    
    private static final boolean debug = true;
    
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
    private static Integer scoreDoubling(Chord chord, Key key){
        Set<BasicNote> goodToDouble = new HashSet<>(
                Arrays.asList(key.getScaleDegree(1), key.getScaleDegree(4), 
                        key.getScaleDegree(5)));

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
                if (!goodToDouble.contains(triad.get(0))){
                    score += BAD_TRIPLING_PENALTY;
                    if (debug){
                        System.err.println("Bad Tripling Penalty: "+BAD_TRIPLING_PENALTY);                        
                    }
                }
            }
            else if (root == 2 && third == 2){
                score += DOUBLE_DOUBLING_PENALTY;
                if (debug){
                    System.err.println("Double Doubling Penalty: "+BAD_TRIPLING_PENALTY);                        
                }
            }
            else if (root == 1 && third == 3){
                if (!goodToDouble.contains(triad.get(2))){
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
            if (!goodToDouble.contains(doubled)){
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
    private static Integer scoreVoiceOverlap(Chord chord, Key key){
        throw new RuntimeException("Unimplemented.");
    }

    
    /**
     * Scores the badness of a chord 
     * @param chord current chord
     * @param key key in which this chord should be analyzed
     * @return a score representing this chord's badness
     */
    public static Integer scoreChord(Chord chord, Key key){
        throw new RuntimeException("Unimplemented.");
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
    private static Integer scoreSmallMovement(Chord previous, Chord current, Key key){
        throw new RuntimeException("Unimplemented.");
    }

    /**
     * Scores for parallel perfect intervals
     * @param previous previous chord 
     * @param current current chord
     * @param key key in which this transition should be analyzed
     * @return score
     */
    private static Integer scoreParallels(Chord previous, Chord current, Key key){
        throw new RuntimeException("Unimplemented.");
    }

    /**
     * Scores for direct perfect intervals
     * @param previous previous chord 
     * @param current current chord
     * @param key key in which this transition should be analyzed
     * @return score
     */
    private static Integer scoreDirects(Chord previous, Chord current, Key key){
        throw new RuntimeException("Unimplemented.");
    }

    /**
     * Scores for bad melodic intervals
     * @param previous previous chord 
     * @param current current chord
     * @param key key in which this transition should be analyzed
     * @return score
     */
    private static Integer scoreMelodicIntervals(Chord previous, Chord current, Key key){
        throw new RuntimeException("Unimplemented.");
    }

    /**
     * Scores for voice crossing
     * @param previous previous chord 
     * @param current current chord
     * @param key key in which this transition should be analyzed
     * @return score
     */
    private static Integer scoreVoiceCrossing(Chord previous, Chord current, Key key){
        throw new RuntimeException("Unimplemented.");
    }

    /**
     * Scores for 7th chord resolutions
     * @param previous previous chord 
     * @param current current chord
     * @param key key in which this transition should be analyzed
     * @return score
     */
    private static Integer scoreSevenChordResolutions(Chord previous, Chord current, Key key){
        throw new RuntimeException("Unimplemented.");
    }

    /**
     * Scores the badness of a transition 
     * @param previous previous chord 
     * @param current current chord
     * @param key key in which this transition should be analyzed
     * @return a score representing this transition's badness
     */
    public static Integer scoreTransition(Chord previous, Chord current, Key key){
        throw new RuntimeException("Unimplemented.");
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
    private static Integer scoreEverythingStepwise(Chord previous, Chord current, Key key){
        throw new RuntimeException("Unimplemented.");
    }

    /**
     * Scores for PAC
     * @param previous previous chord 
     * @param current current chord
     * @param key key in which this transition should be analyzed
     * @return score
     */
    private static Integer scorePAC(Chord previous, Chord current, Key key){
        throw new RuntimeException("Unimplemented.");
    }

    
    /**
     * Additional score for last transition 
     * @param previous previous chord 
     * @param current current chord
     * @param key key in which this transition should be analyzed
     * @return a score representing this transition's additional badness
     */
    public static Integer scoreLastTransitionAdditional(Chord previous, Chord current, Key key){
        throw new RuntimeException("Unimplemented.");
    }

    
}
