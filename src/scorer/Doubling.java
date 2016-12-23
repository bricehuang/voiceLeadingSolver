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

/**
 * A module that scores note doubling
 */
class Doubling {
    
    private static final int BAD_DOUBLING_PENALTY = 100;
    private static final int DOUBLE_DOUBLING_PENALTY = 200;
    private static final int BAD_TRIPLING_PENALTY = 100;
    private static final int OMITTED_FIFTH_PENALTY = 100;
    
    private static final Set<Integer> GOOD_NOTES_TO_DOUBLE = Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList(1, 4, 5)));
    
    /**
     * Scores the badness of a chord's doubling 
     * @param chord current chord
     * @param key key in which this chord should be analyzed
     * @return score
     */
    static Integer scoreDoubling(Chord chord, Key key){

        if (chord.getType().numberDistinctNotes() == 4){
            return 0;
        }
        
        int score = 0;
        
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
        if (fifth == 0){
            score += OMITTED_FIFTH_PENALTY;
            if (root == 3 && third == 1){
                if (!GOOD_NOTES_TO_DOUBLE.contains(key.findScaleDegree(triad.get(0)))){
                    score += BAD_TRIPLING_PENALTY;
                }
            }
            else if (root == 2 && third == 2){
                score += DOUBLE_DOUBLING_PENALTY;
            }
            else if (root == 1 && third == 3){
                if (!GOOD_NOTES_TO_DOUBLE.contains(key.findScaleDegree(triad.get(2)))){
                    score += BAD_TRIPLING_PENALTY;
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
            if (!GOOD_NOTES_TO_DOUBLE.contains(key.findScaleDegree(doubled))){
                score += BAD_DOUBLING_PENALTY;
            }
        }
        else{
            throw new RuntimeException("Should not get here.");
        }        
        return score;
    }
    

}
