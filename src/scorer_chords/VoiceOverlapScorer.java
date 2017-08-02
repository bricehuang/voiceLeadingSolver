package scorer_chords;

import java.util.List;

import chord_data.ChordWithContext;
import music.Note;
import score_data.ChordPenaltyType;
import score_data.ChordScoreNew;
import solver.SolverUtils;

public class VoiceOverlapScorer implements ChordScorer {

    @Override
    public ChordScoreNew scoreChord(ChordWithContext chordAndContext) {
        ChordScoreNew score = new ChordScoreNew();
        
        List<Note> chordNotes = SolverUtils.spellNotes(chordAndContext.getChord());
        for (int i=0; i<3; i++) {
            if (chordNotes.get(i).equals(chordNotes.get(i+1)) ) {
                score.addPenalty(ChordPenaltyType.VOICE_OVERLAP);
            }
        }
        return score;        
    }

}
