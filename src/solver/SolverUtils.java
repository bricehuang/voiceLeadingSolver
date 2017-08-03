package solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import chord_data.ContextTag;
import chords.BasicChord;
import chords.Chord;
import music.BasicNote;
import music.Interval;
import music.Note;

public class SolverUtils {

    public static List<Note> spellNotes(Chord chord){
        return Arrays.asList(
            chord.getSoprano(),
            chord.getAlto(),
            chord.getTenor(),
            chord.getBass()
        );
    }
    
    public static List<BasicNote> spellBasicNotes(Chord chord){
        BasicChord basicChord = chord.getBasicChord();
        return Arrays.asList(
            basicChord.getSoprano(),
            basicChord.getAlto(),
            basicChord.getTenor(),
            basicChord.getBass()
        );
    }
    
    /**
     * @param chord a chord
     * @return a Map from each BasicNote in the chord to the number of times 
     * it appears.  
     */
    public static Map<BasicNote, Integer> countBasicNotes(Chord chord){
        Map<BasicNote, Integer> noteCounts = new HashMap<>();
        for (BasicNote basicNote : spellBasicNotes(chord)) {
            if (!noteCounts.containsKey(basicNote)) {
                noteCounts.put(basicNote, 0);
            }
            noteCounts.put(basicNote, noteCounts.get(basicNote)+1);
        }
        return Collections.unmodifiableMap(noteCounts);
    }
    
    /**
     * Finds the doubled or tripled note in a chord
     * @param chord.  Must be a triad.    
     * @return the doubled or tripled BasicNote in this chord
     */
    public static Set<BasicNote> findDoubledOrMoreNotes(Chord chord){
        assert (chord.getPrimitiveChord().numberDistinctNotes() == 3);
        Set<BasicNote> doubledOrMoreNotes = new HashSet<>();
        
        Map<BasicNote, Integer> noteCounts = SolverUtils.countBasicNotes(chord);
        for (BasicNote basicNote : noteCounts.keySet()){
            if (noteCounts.get(basicNote) > 1){
                doubledOrMoreNotes.add(basicNote);
            }
        }
        return Collections.unmodifiableSet(doubledOrMoreNotes);
    }
    
    /**
     * @param previous previous chord
     * @param current current chord
     * @return a list of the intervals made by each voice in this transition
     */
    public static List<Interval> getVoiceMovements(Chord previous, Chord current) {
        List<Note> previousNotes = spellNotes(previous);
        List<Note> currentNotes = spellNotes(current);
        
        List<Interval> movements = new ArrayList<>();
        for (int i=0; i<4; i++) {
            movements.add(Interval.melodicIntervalBetween(
                previousNotes.get(i), 
                currentNotes.get(i)
            ));
        }
        return Collections.unmodifiableList(movements);
    }

    // TODO make this generic?
    public static Set<ContextTag> intersect(Set<ContextTag> set1, Set<ContextTag>set2){
        Set<ContextTag> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        return Collections.unmodifiableSet(intersection);
    }
}
