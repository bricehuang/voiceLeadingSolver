package solver;

import java.util.Arrays;
import java.util.List;

import chords.BasicChord;
import chords.Chord;
import music.BasicNote;
import music.Note;

public class SolverUtils {

    public static List<Note> spellNotes(Chord chord){
        return Arrays.asList(
            chord.getSoprano(),
            chord.getAlto(),
            chord.getTenor(),
            chord.getBass()
        );
    }
    
    public static List<BasicNote> spellBasicNotes(Chord chord){
        BasicChord basicChord = chord.getBasicChord();
        return Arrays.asList(
            basicChord.getSoprano(),
            basicChord.getAlto(),
            basicChord.getTenor(),
            basicChord.getBass()
        );
    }
    
}
