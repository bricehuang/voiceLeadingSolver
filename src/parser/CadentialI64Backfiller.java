package parser;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import chord_data.ContextTag;
import chord_data.PrimitiveChordWithContext;
import chords.ChordType;
import chords.PrimitiveChord;
import music.BasicNote;
import music.Key;

public class CadentialI64Backfiller implements ParsePostProcessor {

    private static boolean isCadentialI64Chord(PrimitiveChordWithContext chord) {
        Key key = chord.getKey();
        BasicNote tonic = key.getScaleDegree(1);
        ChordType chordType = key.getIsMajor() ? ChordType.MAJ : ChordType.MIN;
        PrimitiveChord expectedI64Chord = new PrimitiveChord(tonic, chordType, 2);
        return chord.getChord().equals(expectedI64Chord);
    }
    
    @Override
    public void postProcess(List<PrimitiveChordWithContext> preprocess) {
        for (int i=0; i<preprocess.size()-1; i++) {
            PrimitiveChordWithContext current = preprocess.get(i);
            PrimitiveChordWithContext next = preprocess.get(i+1);
            if (next.getContextTags().contains(ContextTag.CADENTIAL_V) && 
                    isCadentialI64Chord(current)) {
                PrimitiveChord chord = current.getChord();
                Key key = current.getKey();
                Set<ContextTag> contexts = new HashSet<>(current.getContextTags());
                contexts.add(ContextTag.CADENTIAL_I64);
                PrimitiveChordWithContext newCurrent = 
                    new PrimitiveChordWithContext(chord, key, contexts);
                
                preprocess.set(i, newCurrent);
            }
        }
     }

}
