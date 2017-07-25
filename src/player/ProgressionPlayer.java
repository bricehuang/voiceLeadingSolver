package player;

import java.util.Arrays;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import chords.Chord;
import chords.ChordProgressionDeprecated;
import chords.ChordType;
import chords.PrimitiveChord;
import music.BasicNote;
import music.Note;

/**
 * A module that plays chord progressions
 */
public class ProgressionPlayer {
    
    private static final int CHANNEL = 0;
    private static final int VOLUME = 50;
    
    private static final int TICKS_PER_BEAT = 1;
    private static final int NOTE_LENGTH = 4;
    private static final int DEFAULT_TEMPO = 60;
    
    private static final int META_END_OF_TRACK = 47;
    private static final int THREAD_SLEEP_MILLISECONDS = 1000;

    private static void play(Sequencer sequencer) throws MidiUnavailableException{
        sequencer.setTempoInBPM(DEFAULT_TEMPO);;
        sequencer.open();
        MetaEventListener endTrackDetector = new MetaEventListener(){
            public void meta(MetaMessage meta) {
                if (meta.getType() == META_END_OF_TRACK) {
                    try {
                        Thread.sleep(THREAD_SLEEP_MILLISECONDS); 
                    } 
                    catch (InterruptedException ie) { 
                    }
                    sequencer.stop();
                    sequencer.close();
                }
            }            
        };
        sequencer.addMetaEventListener(endTrackDetector);
        sequencer.start();
    }
    
    private static void addChord(Track track, Chord chord, int tick) 
            throws InvalidMidiDataException {
        List<Note> chordSpelled = Arrays.asList(
                chord.getBass(), chord.getTenor(), chord.getAlto(), chord.getSoprano());
        for (Note note: chordSpelled){
                track.add(new MidiEvent(new ShortMessage(
                        ShortMessage.NOTE_ON, CHANNEL, note.getMidiNote(), VOLUME
                        ), tick));
                track.add(new MidiEvent(new ShortMessage(
                        ShortMessage.NOTE_OFF, CHANNEL, note.getMidiNote(), VOLUME
                        ), tick + NOTE_LENGTH));
        }
    }
    
    private static void addProgression(Track track, ChordProgressionDeprecated progression) 
            throws InvalidMidiDataException {
        if (progression.length() == 0){
            return;
        }
        addProgression(track, progression.getStart());
        addChord(track, progression.getLast(), progression.getStart().length()*NOTE_LENGTH);
    }
    
    /**
     * Plays a chord progression
     * @param progression
     * @throws MidiUnavailableException 
     * @throws InvalidMidiDataException 
     */
    public static void playProgression(ChordProgressionDeprecated progression) 
            throws InvalidMidiDataException, MidiUnavailableException {
        Sequence sequence;
        sequence = new Sequence(Sequence.PPQ, TICKS_PER_BEAT);
        Sequencer sequencer = MidiSystem.getSequencer();
        sequencer.setSequence(sequence);
        Track track = sequence.createTrack();
        
        addProgression(track, progression);
        
        play(sequencer);
    }
    
}
