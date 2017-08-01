package scorer_chords;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import chord_data.ChordWithContext;
import chord_data.ContextTag;
import chords.Chord;
import music.BasicNote;
import score_data.ChordPenaltyType;
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
        
    /**
     * Finds the doubled or tripled note in a chord
     * @param chord.  Must be a triad.    
     * @return the doubled or tripled BasicNote in this chord
     */
    private static Set<BasicNote> findDoubledOrMoreNotes(Chord chord){
        assert (chord.getPrimitiveChord().numberDistinctNotes() == 3);
        Set<BasicNote> doubledOrMoreNotes = new HashSet<>();
        
        Map<BasicNote, Integer> noteCounts = SolverUtils.countBasicNotes(chord);
        for (BasicNote basicNote : noteCounts.keySet()){
            if (noteCounts.get(basicNote) > 1){
                doubledOrMoreNotes.add(basicNote);
            }
        }
        return Collections.unmodifiableSet(doubledOrMoreNotes);
    }
    
    @Override
    public Map<ChordPenaltyType, Integer> scoreChord(ChordWithContext chordAndContext) {
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

            Map<ChordPenaltyType, Integer> penalties = new HashMap<>();
            Set<BasicNote> doubledOrMoreNotes = findDoubledOrMoreNotes(chord);
            
            for (BasicNote doubledNote : doubledOrMoreNotes){
                if (doubledNote.equals(expectedDoubledNote)){
                    penalties.put(ChordPenaltyType.CADENCE_DOUBLING, 1);
                }
            }
            return Collections.unmodifiableMap(penalties);
        } else {
            return Collections.unmodifiableMap(new HashMap<>());
        }
    }

}
