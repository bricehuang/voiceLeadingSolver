package scorer_chords;

import java.util.HashMap;
import java.util.Map;

import chord_data.ChordWithContext;
import chord_data.ContextTag;
import chords.Chord;
import music.Key;
import score_data.ChordPenaltyType;

public class PacScorer implements ChordScorer {

    @Override
    public Map<ChordPenaltyType, Integer> scoreChord(ChordWithContext chordAndContext) {
        Chord chord = chordAndContext.getChord();
        Key key = chordAndContext.getKey();

        Map<ChordPenaltyType, Integer> penalties = new HashMap<>();         
        if (chordAndContext.getContextTags().contains(ContextTag.CADENCE)){
            if (
                    ! (chord.getSoprano().getBasicNote().equals(key.getTonic())  
                    && chord.getBass().getBasicNote().equals(key.getTonic()))
               ){
                penalties.put(ChordPenaltyType.NOT_PAC, 1);
            }
        }
        return penalties;
    }

}
