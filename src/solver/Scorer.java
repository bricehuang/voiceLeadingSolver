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
     * Dom7 proper resolutions
     * Dim7 proper resolutionss
     * ii65 hold tonic
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
