package parser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import chord_data.ContextTag;
import chord_data.PrimitiveChordWithContext;
import chords.ChordType;
import chords.PrimitiveChord;
import music.BasicInterval;
import music.BasicNote;
import music.Key;

public class CadentialPreVBackfiller implements ParsePostProcessor {
    
    private static final BasicInterval SEMITONE = new BasicInterval(0,1);

    private static boolean isCadentialPreVChord(PrimitiveChordWithContext chord) {
        Key key = chord.getKey();
        BasicNote second = key.getScaleDegree(2);
        BasicNote flatSecond = second.transpose(SEMITONE, false);
        BasicNote fourth = key.getScaleDegree(4);
        
        PrimitiveChord primitiveChord = chord.getChord();
        BasicNote root = primitiveChord.getRoot();
        if (root.equals(second)) {
            if (key.getIsMajor()) {
                Set<ChordType> allowedChordTypes = new HashSet<>(Arrays.asList(
                    ChordType.MIN, ChordType.MIN7
                ));
                return allowedChordTypes.contains(primitiveChord.getType());
            } else {
                Set<ChordType> allowedChordTypes = new HashSet<>(Arrays.asList(
                    ChordType.DIM // pending halfdim7
                ));
                return allowedChordTypes.contains(primitiveChord.getType());
            }
        } else if (root.equals(fourth)) {
            ChordType expectedChordType = key.getIsMajor() ? ChordType.MAJ : ChordType.MIN;
            return primitiveChord.getType().equals(expectedChordType);
        } else if (root.equals(flatSecond)) {
            PrimitiveChord expectedNeapolitanChord = new PrimitiveChord(
                root, ChordType.MAJ, 1
            );
            return primitiveChord.equals(expectedNeapolitanChord);
        } else {
            return false;
        }
    }
    
    @Override
    public void postProcess(List<PrimitiveChordWithContext> preprocess) {
        for (int i=0; i<preprocess.size()-1; i++) {
            PrimitiveChordWithContext current = preprocess.get(i);
            PrimitiveChordWithContext next = preprocess.get(i+1);
            if (
                    (next.getContextTags().contains(ContextTag.CADENTIAL_I64) ||
                    next.getContextTags().contains(ContextTag.CADENTIAL_V))
                    && isCadentialPreVChord(current)
                ) {
                PrimitiveChord chord = current.getChord();
                Key key = current.getKey();
                Set<ContextTag> contexts = new HashSet<>(current.getContextTags());
                contexts.add(ContextTag.CADENTIAL_PREDOMINANT);
                PrimitiveChordWithContext newCurrent = 
                    new PrimitiveChordWithContext(chord, key, contexts);

                preprocess.set(i, newCurrent);
            }
        }
     }

}
