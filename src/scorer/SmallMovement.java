package scorer;

import java.util.List;
import java.util.Set;

import chord_data.ContextTag;
import chords.Chord;
import music.Key;
import music.Note;
import score_data.PenaltyTypeDeprecated;
import score_data.Score;

/**
 * A module that scores for small movements
 */
class SmallMovement {
    
    /**
     * Scores for small movements by mutating an input score
     * @param previous previous chord 
     * @param current current chord
     * @param contextTagsPrevious context tags associated with Previous
     * @param contextTagsCurrent context tags associated with current
     * @param key key in which this transition should be analyzed
     * @param score a Score that gets mutated
     */
    static void scoreSmallMovement(Chord previous, Chord current,
            Set<ContextTag> contextTagsPrevious, Set<ContextTag> contextTagsCurrent,
            Key key, Score score){
        List<Note> previousSpelled = Scorer.spellChord(previous);
        List<Note> currentSpelled = Scorer.spellChord(current);
        for (int i=0; i<4; i++){
            int move = Math.abs(previousSpelled.get(i).getNoteID() - currentSpelled.get(i).getNoteID());
            if (i==0){
                if (move>7){
                    score.addPenalty(PenaltyTypeDeprecated.MOVE_BIG_BASS);
                }
            }
            else{
                if (move==2){
                    score.addPenalty(PenaltyTypeDeprecated.MOVE_THIRD);
                }
                else if (move==3){
                    score.addPenalty(PenaltyTypeDeprecated.MOVE_FOURTH);
                }
                else if (move==4){
                    score.addPenalty(PenaltyTypeDeprecated.MOVE_FIFTH);
                }
                else if (move>4){
                    score.addPenalty(PenaltyTypeDeprecated.MOVE_BIG);
                }
            }
        }
    }
}
