package scorer;

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
import score_data.PenaltyType;
import score_data.Score;

class DoublingInCadence {

    private static final Map<ContextTag, Integer> PROPER_DOUBLING;
    static{
        Map<ContextTag, Integer> properDoublingTmp = new HashMap<>();
        properDoublingTmp.put(ContextTag.CADENTIAL_PREDOMINANT, 4);
        properDoublingTmp.put(ContextTag.CADENTIAL_I64, 5);
        properDoublingTmp.put(ContextTag.CADENTIAL_V, 5);
        properDoublingTmp.put(ContextTag.CADENCE, 1);
        PROPER_DOUBLING = Collections.unmodifiableMap(properDoublingTmp);
    }
    
    /**
     * Finds the doubled note in a chord
     * @param chord a chord; PRECONDITIION must be a triad
     * @return ditto
     */
    private static BasicNote findDoubledNote(Chord chord){
        List<Note> chordSpelled = Scorer.spellChord(chord);
        Set<BasicNote> seenNotes = new HashSet<>();
        for (Note note: chordSpelled){
            BasicNote basicNote = note.getBasicNote();
            if (seenNotes.contains(basicNote)){
                return basicNote;
            }
            seenNotes.add(basicNote);
        }
        throw new RuntimeException("Should not get here.");
    }
    
    /**
     * Scores for bad doubling by mutating an input score 
     * @param chord current chord
     * @param contextTags any relevant context tags
     * @param key key in which this transition should be analyzed
     * @param score a Score that gets mutated
     */
    static void scoreDoubling(Chord chord, Set<ContextTag> contextTags, 
            Key key, Score score){
        if (chord.getPrimitiveChord().numberDistinctNotes() == 3){
            boolean containsCadenceTag = false;
            ContextTag cadenceTag = null;
            for (ContextTag tag : contextTags){
                if (PROPER_DOUBLING.keySet().contains(tag)){
                    containsCadenceTag =  true;
                    cadenceTag = tag;
                }
            }
            if (containsCadenceTag){
                int doubledScaleDegree = key.findScaleDegree(findDoubledNote(chord));
                if (doubledScaleDegree != PROPER_DOUBLING.get(cadenceTag)){
                    score.addPenalty(PenaltyType.CADENCE_DOUBLING);
                }
            }            
        }
    }
}
