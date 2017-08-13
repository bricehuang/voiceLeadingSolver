package parser;

import java.util.List;
import java.util.Set;

import chord_data.ContextTag;
import chord_data.PrimitiveChordWithContext;
import chords.ChordType;
import chords.PrimitiveChord;
import music.BasicInterval;
import music.BasicNote;
import music.Key;

public class NeapolitanConsistencyChecker implements ParsePostProcessor {

    private static final BasicInterval SEMITONE = new BasicInterval(0,1);

    @Override
    public void postProcess(List<PrimitiveChordWithContext> preprocess) {
        for (PrimitiveChordWithContext chordAndContext : preprocess) {
            Set<ContextTag> contexts = chordAndContext.getContextTags();
            if (contexts.contains(ContextTag.NEAPOLITAN)) {
                assert (contexts.contains(ContextTag.CADENTIAL_PREDOMINANT));
                
                Key key = chordAndContext.getKey();
                BasicNote flatSecond = key.getScaleDegree(2).transpose(
                    SEMITONE, false
                );
                PrimitiveChord expectedChord = new PrimitiveChord(
                    flatSecond, ChordType.MAJ, 1
                );
                assert(chordAndContext.getChord().equals(expectedChord));
            }
        }    
    }

}
