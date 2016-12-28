package scorer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import chords.Chord;
import chords.ChordProgression;
import music.Key;
import music.Note;
import solver.ContextTag;

/**
 * A module that scores individual chords and transitions between chords  
 */
public class Scorer {
    
    // TODO refactoring:
    // update method signatures 
    // refactor individual methods
     
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
     * Scores the badness of a chord by mutating an input Score  
     * @param chord current chord
     * @param key key in which this chord should be analyzed
     * @param contextTags set of context tags for this chord
     * @param score a Score that gets mutated
     * @return a score representing this chord's badness
     */
    public static Score scoreChord(Chord chord, Key key, Set<ContextTag> contextTags){
        Score score = new Score();
        Doubling.scoreDoubling(chord, key, contextTags, score);
        VoiceOverlap.scoreVoiceOverlap(chord, key, contextTags, score);
        return score;
    }
    
    /**
     * Scores the badness of a transition 
     * @param previous previous chord 
     * @param current current chord
     * @param key key in which this transition should be analyzed
     * @return a score representing this transition's badness
     */
    public static Score scoreTransition(Chord previous, Chord current, Key key, Set<ContextTag> contextTags){
        Score score = new Score();
        SmallMovement.scoreSmallMovement(previous, current, key, contextTags, score);
        ParallelsDirects.scoreParallels(previous, current, key, contextTags, score);
        ParallelsDirects.scoreDirects(previous, current, key, contextTags, score);
        MelodicIntervals.scoreMelodicIntervals(previous, current, key, contextTags, score);
        VoiceCrossing.scoreVoiceCrossing(previous, current, key, contextTags, score);
        SevenChordResolution.scoreDomSevenResolutions(previous, current, key, contextTags, score);
        SevenChordResolution.scoreDimSevenResolutions(previous, current, key, contextTags, score);
        return score;
    }
    
    private static String evaluateChord(Chord chord, Key key, Set<ContextTag> contextTags){
        String ans = "";
        ans += "Scoring chord " + chord.toString() + " in " + key.toString() + "\n";
        ans += scoreChord(chord, key, contextTags).toString();
        return ans;
    }
    
    private static String evaluateTransition(Chord previous, Chord current, Key key, Set<ContextTag> contextTags){
        String ans = "";
        ans += "Scoring transition " + previous.toString() + " --> " + current.toString() +
                " in " + key.toString() + "\n";
        ans += scoreTransition(previous, current, key, contextTags).toString();
        return ans;
    }
    
    public static String evaluateChordProgression(ChordProgression progression, 
            List<Key> keys, List<Set<ContextTag>> contextTagList){
        assert(progression.length() == keys.size() && 
                progression.length() == contextTagList.size());
        ChordProgression workingProgression = progression;
        final int length = progression.length();
        List<Chord> chordsInProgression = new ArrayList<>(length);
        for (int i=0; i<length; i++){
            chordsInProgression.set(length - 1 - i, workingProgression.getLast());
            workingProgression = workingProgression.getStart();
        }
        
        String ans = "";
        ans += "Evaluating Chord Progression: " + progression.toString() + "\n";
        for (int i=0; i<length; i++){
            if (i!=0){
                ans += evaluateTransition(chordsInProgression.get(i-1), 
                        chordsInProgression.get(i), 
                        keys.get(i), contextTagList.get(i)) + "\n";
            }
            ans += evaluateChord(chordsInProgression.get(i), 
                    keys.get(i), contextTagList.get(i)) + "\n";
        }
        return ans;
    }
    
}
