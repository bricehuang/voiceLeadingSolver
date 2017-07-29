package scorer;

import java.util.Set;

import chord_data.ContextTag;
import chords.Chord;
import music.Key;
import score_data.ChordPenaltyType;
import score_data.ScoreDeprecated;

class Pac {

    /**
     * Scores for bad doubling by mutating an input score 
     * @param chord current chord
     * @param contextTags any relevant context tags
     * @param key key in which this transition should be analyzed
     * @param score a Score that gets mutated
     */
    static void scorePAC(Chord chord, Set<ContextTag> contextTags, 
            Key key, ScoreDeprecated score){
        if (contextTags.contains(ContextTag.CADENCE)){
            if (!(chord.getSoprano().getBasicNote().equals(key.getTonic()) 
                    && chord.getBass().getBasicNote().equals(key.getTonic()))){
                score.addPenalty(ChordPenaltyType.NOT_PAC);
            }
        }
    }
}
