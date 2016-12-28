package scorer;

import java.util.List;
import java.util.Set;

import chords.Chord;
import music.Key;
import music.Note;
import solver.ContextTag;

/**
 * A module that scores for small movements
 */
class SmallMovement {
    
    /**
     * Scores for small movements by mutating an input score
     * @param previous previous chord 
     * @param current current chord
     * @param key key in which this transition should be analyzed
     * @param contextTags any relevant context tags
     * @param score a Score that gets mutated
     */
    static void scoreSmallMovement(Chord previous, Chord current, Key key, 
            Set<ContextTag> contextTags, Score score){
        List<Note> previousSpelled = Scorer.spellChord(previous);
        List<Note> currentSpelled = Scorer.spellChord(current);
        for (int i=0; i<4; i++){
            int move = Math.abs(previousSpelled.get(i).getNoteID() - currentSpelled.get(i).getNoteID());
            if (i==0){
                if (move>7){
                    score.addPenalty(PenaltyType.MOVE_BIG_BASS);
                }
            }
            else{
                if (move==2){
                    score.addPenalty(PenaltyType.MOVE_THIRD);
                }
                else if (move==3){
                    score.addPenalty(PenaltyType.MOVE_FOURTH);
                }
                else if (move==4){
                    score.addPenalty(PenaltyType.MOVE_FIFTH);
                }
                else if (move>4){
                    score.addPenalty(PenaltyType.MOVE_BIG);
                }
            }
        }
    }
}
