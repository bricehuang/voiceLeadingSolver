package parser;

import java.util.List;

import chord_data.ContextTag;
import chord_data.PrimitiveChordWithContext;
import chords.ChordType;
import chords.PrimitiveChord;
import music.BasicNote;
import music.Key;

public class CadenceConsistencyChecker implements ParsePostProcessor {

    @Override
    public void postProcess(List<PrimitiveChordWithContext> preprocess) {
        for (PrimitiveChordWithContext chordAndContext : preprocess) {
            if (chordAndContext.getContextTags().contains(ContextTag.CADENCE)) {
                Key key = chordAndContext.getKey();
                BasicNote tonic = key.getScaleDegree(1);
                ChordType chordType = key.getIsMajor() ? ChordType.MAJ : ChordType.MIN;
                PrimitiveChord expectedChord = new PrimitiveChord(tonic, chordType, 0);
                assert(chordAndContext.getChord().equals(expectedChord));
            }
        }
    }

}
