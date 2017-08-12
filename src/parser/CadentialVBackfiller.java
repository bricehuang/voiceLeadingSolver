package parser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import chord_data.ContextTag;
import chord_data.PrimitiveChordWithContext;
import chords.ChordType;
import chords.PrimitiveChord;
import music.BasicNote;
import music.Key;

public class CadentialVBackfiller implements ParsePostProcessor {

    private static boolean isCadentialVChord(PrimitiveChordWithContext chord) {
        BasicNote fifth = chord.getKey().getScaleDegree(5);
        Set<PrimitiveChord> cadentialVChords = new HashSet<>(Arrays.asList(
            new PrimitiveChord(fifth, ChordType.MAJ, 0),
            new PrimitiveChord(fifth, ChordType.DOM7, 0)
        ));
        return cadentialVChords.contains(chord.getChord());
    }
    
    @Override
    public void postProcess(List<PrimitiveChordWithContext> preprocess) {
        for (int i=0; i<preprocess.size()-1; i++) {
            PrimitiveChordWithContext current = preprocess.get(i);
            PrimitiveChordWithContext next = preprocess.get(i+1);
            if (next.getContextTags().contains(ContextTag.CADENCE) && 
                    isCadentialVChord(current)) {
                PrimitiveChord chord = current.getChord();
                Key key = current.getKey();
                Set<ContextTag> contexts = new HashSet<>(current.getContextTags());
                contexts.add(ContextTag.CADENTIAL_V);
                PrimitiveChordWithContext newCurrent = 
                    new PrimitiveChordWithContext(chord, key, contexts);
                
                preprocess.set(i, newCurrent);
            }
        }
     }

}
