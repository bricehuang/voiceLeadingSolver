package scorer;

import java.util.List;
import java.util.Set;

import chords.Chord;
import chords.ChordType;
import music.Key;
import music.Note;
import solver.ContextTag;

class II7Suspension {
    
    /**
     * Scores for suspension of 7th in ii7 chord by mutating an input score
     * @param previous previous chord 
     * @param current current chord
     * @param contextTagsPrevious context tags associated with Previous
     * @param contextTagsCurrent context tags associated with current
     * @param key key in which this transition should be analyzed
     * @param score a Score that gets mutated
     */
    static void scoreII7Suspension(Chord previous, Chord current,
            Set<ContextTag> contextTagsPrevious, Set<ContextTag> contextTagsCurrent,
            Key key, Score score){
        if (previous.getPrimitiveChord().getType().equals(ChordType.MIN7) 
                && key.findScaleDegree(previous.getPrimitiveChord().getRoot()) == 2 
                && contextTagsPrevious.contains(ContextTag.CADENTIAL_PREDOMINANT) 
                && (
                        (current.getPrimitiveChord().getType().equals(ChordType.MAJ) 
                                && key.getIsMajor()) 
                        || (current.getPrimitiveChord().getType().equals(ChordType.MIN) 
                                && !key.getIsMajor())
                        )                        
                && key.findScaleDegree(current.getPrimitiveChord().getRoot()) == 1
                && current.getPrimitiveChord().getInversion() == 2
                && contextTagsCurrent.contains(ContextTag.CADENTIAL_I64)
                ){
            List<Note> previousSpelled = Scorer.spellChord(previous);
            List<Note> currentSpelled = Scorer.spellChord(current);
            for (int i=0; i<4; i++){
                if (previousSpelled.get(i).getBasicNote().equals(key.getTonic()) 
                        && !currentSpelled.get(i).getBasicNote().equals(key.getTonic())){
                    score.addPenalty(PenaltyType.CADENTIAL_II7_SUSPEND);
                }
            }
        }
    }
}
