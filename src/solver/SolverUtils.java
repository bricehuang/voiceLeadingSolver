package solver;

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

    // TODO make this generic?
    public static Set<ContextTag> intersect(Set<ContextTag> set1, Set<ContextTag>set2){
        Set<ContextTag> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        return Collections.unmodifiableSet(intersection);
    }
}
