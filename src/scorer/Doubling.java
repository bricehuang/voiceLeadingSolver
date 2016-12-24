package scorer;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import chords.Chord;
import music.BasicNote;
import music.Key;
import music.Note;
import solver.ContextTag;

/**
 * A module that scores note doubling
 */
class Doubling {
    // TODO REFACTOR THIS 
    
    private static final Set<Integer> GOOD_NOTES_TO_DOUBLE = Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList(1, 4, 5)));
    
    /**
     * Scores for bad doubling by mutating an input score 
     * @param chord current chord
     * @param key key in which this transition should be analyzed
     * @param contextTags any relevant context tags
     * @param score a Score that gets mutated
     */
    static void scoreDoubling(Chord chord, Key key, 
            Set<ContextTag> contextTags, Score score){

        if (chord.getType().numberDistinctNotes() == 4){
            return;
        }
                
        List<BasicNote> triad = chord.getPrimitiveChord().noteList();
        List<Note> chordSpelled = Scorer.spellChord(chord);       
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
        if (root == 3 && third == 1 && fifth == 0){
            if (!GOOD_NOTES_TO_DOUBLE.contains(key.findScaleDegree(triad.get(0)))){
                    score.addPenalty(PenaltyType.BAD_TRIPLING);
            }
        }
        else if (root == 2 && third == 2 && fifth == 0){
            score.addPenalty(PenaltyType.DOUBLE_DOUBLING);
        }
        else if (root == 1 && third == 3 && fifth == 0){
            if (!GOOD_NOTES_TO_DOUBLE.contains(key.findScaleDegree(triad.get(2)))){
                score.addPenalty(PenaltyType.BAD_TRIPLING);             }
        }
        else if (root == 2 && third == 1 && fifth == 1){
            if (!GOOD_NOTES_TO_DOUBLE.contains(key.findScaleDegree(triad.get(0)))){
                score.addPenalty(PenaltyType.BAD_DOUBLING);
            }
        }
        else if (root == 1 && third == 2 && fifth == 1){
            if (!GOOD_NOTES_TO_DOUBLE.contains(key.findScaleDegree(triad.get(1)))){
                score.addPenalty(PenaltyType.BAD_DOUBLING);
            }
        }
        else if (root == 1 && third == 1 && fifth == 2){
            if (!GOOD_NOTES_TO_DOUBLE.contains(key.findScaleDegree(triad.get(2)))){
                score.addPenalty(PenaltyType.BAD_DOUBLING);
            }
        }
        else{
            throw new RuntimeException("Should not get here.");
        }        
    }
    
    
}
