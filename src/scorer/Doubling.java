package scorer;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import chord_data.ContextTag;
import chords.Chord;
import music.BasicNote;
import music.Key;
import music.Note;
import score_data.ChordPenaltyType;
import score_data.Score;

/**
 * A module that scores note doubling
 */
class Doubling { 
    
    private static final Set<Integer> GOOD_NOTES_TO_DOUBLE = Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList(1, 4, 5)));
    private static final Integer LEADING_TONE = 7;
    
    /**
     * Scores for bad doubling by mutating an input score 
     * @param chord current chord
     * @param contextTags any relevant context tags
     * @param key key in which this transition should be analyzed
     * @param score a Score that gets mutated
     */
    static void scoreDoubling(Chord chord, Set<ContextTag> contextTags, 
            Key key, Score score){

        if (chord.getType().numberDistinctNotes() == 4){
            return;
        }
                
        List<Note> chordSpelled = Scorer.spellChord(chord);

        // this Map counts the number of times each note appears
        List<BasicNote> triad = chord.getPrimitiveChord().noteList();
        Map<BasicNote, Integer> noteCounts = new HashMap<>();
        noteCounts.put(triad.get(0), 0);
        noteCounts.put(triad.get(1), 0);
        noteCounts.put(triad.get(2), 0);
        
        for (Note note: chordSpelled){
            noteCounts.put(note.getBasicNote(), noteCounts.get(note.getBasicNote())+1);
        }
        if (noteCounts.get(triad.get(2)) == 0){
            score.addPenalty(ChordPenaltyType.OMITTED_FIFTH);
            for (BasicNote note : triad){
                if (noteCounts.get(note) == 3 && !GOOD_NOTES_TO_DOUBLE.contains(key.findScaleDegree(note))){
                    score.addPenalty(ChordPenaltyType.BAD_TRIPLING);
                    if (key.findScaleDegree(note) == LEADING_TONE){
                        score.addPenalty(ChordPenaltyType.DOUBLED_LEADING_TONE);
                    }
                }
            }
            if (noteCounts.get(triad.get(0)) == 2 && noteCounts.get(triad.get(1)) == 2){
                score.addPenalty(ChordPenaltyType.DOUBLE_DOUBLING);
            }
        }
        else{
            for (BasicNote note : triad){
                if (noteCounts.get(note) == 2 && !GOOD_NOTES_TO_DOUBLE.contains(key.findScaleDegree(note))){
                    score.addPenalty(ChordPenaltyType.BAD_DOUBLING);
                    if (key.findScaleDegree(note) == LEADING_TONE){
                        score.addPenalty(ChordPenaltyType.DOUBLED_LEADING_TONE);
                    }
                }
            }            
        }
    }
    
    
}
