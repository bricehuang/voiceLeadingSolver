package scorer;

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
    
    // TODO refactoring:
    // refactor individual methods
    // decide how to architecture key/debug; right now will be passed as params everywhere
    // make a Penalty datatype? Or, centralize penalties? 
    
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
     * Dom7 proper resolution // TODO: differentiate between irregular and bad?
     * Dim7 proper resolution
     * neopolitan 6 proper resolution // TODO
     * aug6 proper resolutions // TODO implement these chords
     * ii65 hold tonic TODO
     * 
     * Last Transition:
     * 
     * Everything moves stepwise except bass //TODO
     * PAC // TODO
     * 
     */
    
    /**
     * Returns a list spelling the notes of a chord
     * @param chord a chord
     * @return a list of the 4 notes of a chord, spelled from
     * the bass up
     */
    static List<Note> spellChord(Chord chord){
        return Arrays.asList(chord.getBass(), 
                chord.getTenor(), chord.getAlto(), chord.getSoprano());
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
        score += Doubling.scoreDoubling(chord, key, debug);
        score += VoiceOverlap.scoreVoiceOverlap(chord, key, debug);
        if (debug){
            System.err.println("Total Penalty: "+score+"\n");
        }
        return score;
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
        //score += SmallMovement.scoreSmallMovement(previous, current, key, debug);
        score += ParallelsDirects.scoreParallels(previous, current, key, debug);
        score += ParallelsDirects.scoreDirects(previous, current, key, debug);
        //score += MelodicIntervals.scoreMelodicIntervals(previous, current, key, debug);
        //score += VoiceCrossing.scoreVoiceCrossing(previous, current, key, debug);
        //score += DominantSevenResolution.scoreDomSevenResolutions(previous, current, key, debug);
        //score += DiminishedSevenResolution.scoreDimSevenResolutions(previous, current, key, debug);
        if (debug){
            System.err.println("Total Penalty: "+score+"\n");
        }
        return score;
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
    public Integer scoreLastTransitionAdditional(Chord previous, Chord current){
        throw new RuntimeException("Unimplemented.");
    }

    
}