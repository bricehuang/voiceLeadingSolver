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
    // refactor individual methods
     
    /*
     * Heuristics:
     * 
     * Individual chords: 
     * 
     * Doubling rules
     * No voice overlapping 
     * 
     * Transitions:
     * Small voice movement
     * No Parallels // TODO except resolution of Ger6+? 
     * No Directs
     * No melodic aug/dim intervals
     * No voice crossing
     * Dom7 proper resolution 
     * Dim7 proper resolution 
     * neopolitan 6 proper resolution // TODO
     * aug6 proper resolutions // TODO implement these chords
     * ii65 hold tonic 
     * 
     * Cadences:
     * Cadence doubling
     * PAC
     * Everything moves stepwise except bass 
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
     * @return a score representing this chord's badness
     */
    public static Score scoreChord(Chord chord, Set<ContextTag> contextTags, Key key){
        Score score = new Score();
        Doubling.scoreDoubling(chord, contextTags, key, score);
        VoiceOverlap.scoreVoiceOverlap(chord, contextTags, key, score);
        DoublingInCadence.scoreDoubling(chord, contextTags, key, score);
        Pac.scorePAC(chord, contextTags, key, score);
        return score;
    }
    
    /**
     * Scores the badness of a transition 
     * @param previous previous chord 
     * @param current current chord
     * @param contextTagsPrevious context tags associated with Previous
     * @param contextTagsCurrent context tags associated with current
     * @param key key in which this transition should be analyzed
     * @return a score representing this transition's badness
     */
    public static Score scoreTransition(Chord previous, Chord current, 
            Set<ContextTag> contextTagsPrevious, Set<ContextTag> contextTagsCurrent,
            Key key){
        Score score = new Score();
        SmallMovement.scoreSmallMovement(previous, current, 
                contextTagsPrevious, contextTagsCurrent, key, score);
        ParallelsDirects.scoreParallels(previous, current, 
                contextTagsPrevious, contextTagsCurrent, key, score);
        ParallelsDirects.scoreDirects(previous, current, 
                contextTagsPrevious, contextTagsCurrent, key, score);
        MelodicIntervals.scoreMelodicIntervals(previous, current, 
                contextTagsPrevious, contextTagsCurrent, key, score);
        VoiceCrossing.scoreVoiceCrossing(previous, current, 
                contextTagsPrevious, contextTagsCurrent, key, score);
        SevenChordResolution.scoreDomSevenResolutions(previous, current, 
                contextTagsPrevious, contextTagsCurrent, key, score);
        SevenChordResolution.scoreDimSevenResolutions(previous, current, 
                contextTagsPrevious, contextTagsCurrent, key, score);
        CadenceSmallMovement.scoreSmallMovementCadence(previous, current, 
                contextTagsPrevious, contextTagsCurrent, key, score);
        II7Suspension.scoreII7Suspension(previous, current, 
                contextTagsPrevious, contextTagsCurrent, key, score);        
        return score;
    }
    
    /**
     * Returns string representation of chord scoring, with itemized penalties
     * @param chord 
     * @param key
     * @param score, must be the scoring of this chord
     * @return ditto
     */
    private static String evaluateChord(Chord chord, Key key, Score score){
        String ans = "";
        ans += "Scoring chord " + chord.toString() + " in " + key.toString() + "\n";
        ans += score.toString();
        return ans;
    }
    
    /**
     * Returns string representation of transition scoring, with itemized penalties
     * @param previous
     * @param current
     * @param key
     * @param score, must be the scoring of this transition
     * @return ditto
     */
    private static String evaluateTransition(Chord previous, Chord current, Key key, Score score){
        String ans = "";
        ans += "Scoring transition " + previous.toString() + " --> " + current.toString() +
                " in " + key.toString() + "\n";
        ans += score.toString();
        return ans;
    }
    
    /**
     * Returns a string reporting the scoring of a chord progression, with itemized 
     * penalties for each chord and transition
     * @param progression
     * @param keys
     * @param contextTagsList
     * @return ditto
     */
    public static String evaluateChordProgression(ChordProgression progression, 
            List<Key> keys, List<Set<ContextTag>> contextTagsList){
        assert(progression.length() == keys.size() && 
                progression.length() == contextTagsList.size());
        ChordProgression workingProgression = progression;
        final int length = progression.length();
        List<Chord> chordsInProgression = new ArrayList<>();
        // TODO this is a bit janky
        for (int i=0; i<length; i++){
            chordsInProgression.add(null);
        }
        for (int i=0; i<length; i++){
            chordsInProgression.set(length - 1 - i, workingProgression.getLast());
            workingProgression = workingProgression.getStart();
        }
        
        int totalScore = 0;
        String ans = "";
        ans += "Evaluating Chord Progression: " + progression.toString() + "\n";
        String report = "";
        for (int i=0; i<length; i++){
            Chord chord = chordsInProgression.get(i);
            Key key = keys.get(i);
            Set<ContextTag> contextTags = contextTagsList.get(i);
            if (i!=0){
                Chord previous = chordsInProgression.get(i-1);
                Set<ContextTag> contextTagsPrevious = contextTagsList.get(i-1);
                Score transitionScore = scoreTransition(previous, chord, 
                        contextTagsPrevious, contextTags, key);
                totalScore += transitionScore.totalScore();
                report += evaluateTransition(previous, chord, key, transitionScore) + "\n";
            }
            Score chordScore = scoreChord(chord, contextTags, key);
            totalScore += chordScore.totalScore();
            report += evaluateChord(chord, key, chordScore) + "\n";
        }
        ans += "Total Score: " + totalScore + "\n\n" + report + "\n\n";
        return ans;
    }
    
}
