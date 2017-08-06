package scorer_chords;

import chord_data.ChordWithContext;
import chord_data.ContextTag;
import chords.Chord;
import music.Key;
import score_data.ChordPenaltyType;
import score_data.ChordScore;

public class PacScorer implements ChordScorer {

    @Override
    public ChordScore scoreChord(ChordWithContext chordAndContext) {
        Chord chord = chordAndContext.getChord();
        Key key = chordAndContext.getKey();

        ChordScore score = new ChordScore();         
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
