package solver;

import chords.Chord;
import music.Key;

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
        throw new RuntimeException("Unimplemented.");
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
