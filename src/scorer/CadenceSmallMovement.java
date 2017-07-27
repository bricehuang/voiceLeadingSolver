package scorer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import chord_data.ContextTag;
import chords.Chord;
import music.Key;
import music.Note;
import score_data.PenaltyType;
import score_data.Score;

class CadenceSmallMovement {
    private static Set<ContextTag> CADENCE_TAGS = 
            new HashSet<>(Arrays.asList(
                    ContextTag.CADENTIAL_PREDOMINANT,
                    ContextTag.CADENTIAL_I64,
                    ContextTag.CADENTIAL_V,
                    ContextTag.CADENCE
                    ));
    
    private static boolean containsCadenceTag(Set<ContextTag> contextTags){
        for (ContextTag contextTag : contextTags){
            if (CADENCE_TAGS.contains(contextTag)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Scores for small movements in cadences by mutating an input score
     * @param previous previous chord 
     * @param current current chord
     * @param contextTagsPrevious context tags associated with Previous
     * @param contextTagsCurrent context tags associated with current
     * @param key key in which this transition should be analyzed
     * @param score a Score that gets mutated
     */
    static void scoreSmallMovementCadence(Chord previous, Chord current,
            Set<ContextTag> contextTagsPrevious, Set<ContextTag> contextTagsCurrent,
            Key key, Score score){
        if (containsCadenceTag(contextTagsPrevious) &&
                containsCadenceTag(contextTagsCurrent)){
            List<Note> previousSpelled = Scorer.spellChord(previous);
            List<Note> currentSpelled = Scorer.spellChord(current);
            for (int i=1; i<4; i++){
                int move = Math.abs(previousSpelled.get(i).getNoteID() - currentSpelled.get(i).getNoteID());
                if (move>1){
                    score.addPenalty(PenaltyType.MOVE_BIG_CADENCE);
                }
            }
        }
    }
    
}
