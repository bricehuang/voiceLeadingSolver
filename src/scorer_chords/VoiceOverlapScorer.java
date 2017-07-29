package scorer_chords;

import java.util.Map;

import chord_data.ChordWithContext;
import score_data.ChordPenaltyType;

public class VoiceOverlapScorer implements ChordScorer {

    @Override
    public Map<ChordPenaltyType, Integer> scoreChord(ChordWithContext chordAndContext) {
        throw new RuntimeException("Unimplemented");
    }

}
