package scorer_chords;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import chord_data.ChordWithContext;
import chord_data.ContextTag;
import chords.Chord;
import music.BasicNote;
import score_data.ChordPenaltyType;
import score_data.ChordScore;
import solver.SolverUtils;

public class DoublingCadenceScorer implements ChordScorer {

    private static final Map<ContextTag, Integer> PROPER_DOUBLING;
    static{
        Map<ContextTag, Integer> properDoublingTmp = new HashMap<>();
        properDoublingTmp.put(ContextTag.CADENTIAL_PREDOMINANT, 4);
        properDoublingTmp.put(ContextTag.CADENTIAL_I64, 5);
        properDoublingTmp.put(ContextTag.CADENTIAL_V, 5);
        properDoublingTmp.put(ContextTag.CADENCE, 1);
        PROPER_DOUBLING = Collections.unmodifiableMap(properDoublingTmp);
    }
        
    @Override
    public ChordScore scoreChord(ChordWithContext chordAndContext) {
        Chord chord = chordAndContext.getChord();
        Set<ContextTag> relevantContexts = SolverUtils.intersect(
            chordAndContext.getContextTags(), PROPER_DOUBLING.keySet()
        );

        if (chord.getPrimitiveChord().numberDistinctNotes() == 3 
                && relevantContexts.size() > 0){
            assert (relevantContexts.size() == 1);
            // get only context
            ContextTag contextTag = relevantContexts.iterator().next();
            BasicNote expectedDoubledNote = chordAndContext.getKey().getScaleDegree(
                PROPER_DOUBLING.get(contextTag)
            );
            
            Set<BasicNote> doubledOrMoreNotes = 
                SolverUtils.findDoubledOrMoreNotes(chord);
            
            ChordScore score = new ChordScore();
            for (BasicNote doubledNote : doubledOrMoreNotes){
                if (! doubledNote.equals(expectedDoubledNote)){
                    score.addPenalty(ChordPenaltyType.CADENCE_DOUBLING);
                }
            }
            return score;
        } else {
            return new ChordScore();
        }
    }

}
