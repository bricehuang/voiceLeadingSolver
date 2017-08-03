package scorer_chords;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import chord_data.ChordWithContext;
import chords.Chord;
import music.BasicNote;
import music.Key;
import score_data.ChordPenaltyType;
import score_data.ChordScoreNew;
import solver.SolverUtils;

public class DoublingScorer implements ChordScorer {

    private static final Set<Integer> GOOD_NOTES_TO_DOUBLE = 
        Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList(1, 4, 5))
        );
    private static final Integer LEADING_TONE = 7;

    @Override
    public ChordScoreNew scoreChord(ChordWithContext chordAndContext) {
        Chord chord = chordAndContext.getChord();
        Key key = chordAndContext.getKey();
        if (chord.getType().numberDistinctNotes() == 4) {
            return new ChordScoreNew();
        }
         
        Map<BasicNote, Integer> noteCounts  = SolverUtils.countBasicNotes(chord);
        
        List<BasicNote> basicNotes = chord.getPrimitiveChord().noteList();
        BasicNote root = basicNotes.get(0);
        BasicNote third = basicNotes.get(1);
        BasicNote fifth = basicNotes.get(2); 
        if (! noteCounts.keySet().contains(fifth)) {
            ChordScoreNew score = new ChordScoreNew(); 
            score.addPenalty(ChordPenaltyType.OMITTED_FIFTH);
            if (noteCounts.get(root) == 2 && noteCounts.get(third) == 2) {
                score.addPenalty(ChordPenaltyType.DOUBLE_DOUBLING);
            } else {
                BasicNote tripledNote = (noteCounts.get(root) == 3) ? root : third;
                if (! GOOD_NOTES_TO_DOUBLE.contains(key.findScaleDegree(tripledNote))) {
                    score.addPenalty(ChordPenaltyType.BAD_TRIPLING);
                }
                if (key.findScaleDegree(tripledNote) == LEADING_TONE) {
                    score.addPenalty(ChordPenaltyType.DOUBLED_LEADING_TONE);
                }
            }
            return score;
        } else {
            ChordScoreNew score = new ChordScoreNew(); 
            Set<BasicNote> doubledNotes = SolverUtils.findDoubledOrMoreNotes(chord);
            assert (doubledNotes.size() == 1);
            
            for (BasicNote doubledNote : doubledNotes) {
                assert (noteCounts.get(doubledNote) == 2);
                if (! GOOD_NOTES_TO_DOUBLE.contains(key.findScaleDegree(doubledNote))) {
                    score.addPenalty(ChordPenaltyType.BAD_DOUBLING);
                }
                if (key.findScaleDegree(doubledNote) == LEADING_TONE) {
                    score.addPenalty(ChordPenaltyType.DOUBLED_LEADING_TONE);
                }
            }
            return score;
        }
    }

}
