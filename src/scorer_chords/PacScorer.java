package scorer_chords;

import chord_data.ChordWithContext;
import chord_data.ContextTag;
import chords.Chord;
import music.Key;
import score_data.ChordPenaltyType;
import score_data.ChordScoreNew;

public class PacScorer implements ChordScorer {

    @Override
    public ChordScoreNew scoreChord(ChordWithContext chordAndContext) {
        Chord chord = chordAndContext.getChord();
        Key key = chordAndContext.getKey();

        ChordScoreNew score = new ChordScoreNew();         
        if (chordAndContext.getContextTags().contains(ContextTag.CADENCE)){
            if (
                    ! (chord.getSoprano().getBasicNote().equals(key.getTonic())  
                    && chord.getBass().getBasicNote().equals(key.getTonic()))
               ){
                score.addPenalty(ChordPenaltyType.NOT_PAC);
            }
        }
        return score;
    }

}
