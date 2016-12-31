package player;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;

import chords.ChordProgression;

/**
 * A module that plays chord progressions
 */
public class ProgressionPlayer {
    
    private static final int TICKS_PER_BEAT = 1;
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
    
    /**
     * Plays a chord progression
     * @param progression
     * @throws MidiUnavailableException 
     * @throws InvalidMidiDataException 
     */
    public static void playProgression(ChordProgression progression) 
            throws MidiUnavailableException, InvalidMidiDataException{
        Sequence sequence = new Sequence(Sequence.PPQ, TICKS_PER_BEAT);
        
        Sequencer sequencer = MidiSystem.getSequencer();
        sequencer.setSequence(sequence);
        Track track = sequence.createTrack();

        // TODO: add notes from progression
        
        play(sequencer);
        throw new RuntimeException("Unimplemented");
    }
}
