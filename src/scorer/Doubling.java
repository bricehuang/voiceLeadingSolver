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
    // TODO THIS IS BROKEN 
    
    private static final int BAD_DOUBLING_PENALTY = 100;
    private static final int DOUBLE_DOUBLING_PENALTY = 200;
    private static final int BAD_TRIPLING_PENALTY = 100;
    private static final int OMITTED_FIFTH_PENALTY = 100;
    
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
        
        int tmpScore = 0;
        
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
            tmpScore+= OMITTED_FIFTH_PENALTY;
            if (root == 3 && third == 1){
                if (!GOOD_NOTES_TO_DOUBLE.contains(key.findScaleDegree(triad.get(0)))){
                    tmpScore+= BAD_TRIPLING_PENALTY;
                }
            }
            else if (root == 2 && third == 2){
                tmpScore+= DOUBLE_DOUBLING_PENALTY;
            }
            else if (root == 1 && third == 3){
                if (!GOOD_NOTES_TO_DOUBLE.contains(key.findScaleDegree(triad.get(2)))){
                    tmpScore+= BAD_TRIPLING_PENALTY;
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
                tmpScore+= BAD_DOUBLING_PENALTY;
            }
        }
        else{
            throw new RuntimeException("Should not get here.");
        }        
        return;
    }
    

}
