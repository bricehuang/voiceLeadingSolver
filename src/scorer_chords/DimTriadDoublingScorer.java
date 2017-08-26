package scorer_chords;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import chord_data.ChordWithContext;
import chords.Chord;
import chords.ChordType;
import music.BasicNote;
import music.Key;
import score_data.ChordPenaltyType;
import score_data.ChordScore;
import solver.SolverUtils;

public class DimTriadDoublingScorer implements ChordScorer {
    
    private static final Set<Integer> GOOD_NOTES_TO_DOUBLE = 
        Collections.unmodifiableSet(new HashSet<>(Arrays.asList(1,4,5)));

    @Override
    public ChordScore scoreChord(ChordWithContext chordAndContext) {
        ChordScore score = new ChordScore();
        Chord chord = chordAndContext.getChord();
        if (chord.getType() != ChordType.DIM) {
            return score;
        }

        Set<BasicNote> doubledNotes = SolverUtils.findDoubledOrMoreNotes(chord);

        // sanity check
        assert doubledNotes.size() == 1;

        BasicNote doubledNote = doubledNotes.iterator().next();
        BasicNote expectedDouble = chord.getPrimitiveChord().noteList().get(1);
        if (!doubledNote.equals(expectedDouble)) {
            score.addPenalty(ChordPenaltyType.BAD_DIM_DOUBLING);
        } else {
            Key key = chordAndContext.getKey();
            if (! GOOD_NOTES_TO_DOUBLE.contains(key.findScaleDegree(expectedDouble))) {
                score.addPenalty(ChordPenaltyType.BAD_DOUBLING_OK_BECAUSE_DIM);
            }
        }
        return score;
    }

}
