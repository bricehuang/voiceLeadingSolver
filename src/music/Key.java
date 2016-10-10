package music;

import java.util.Arrays;
import java.util.List;

/**
 * Immutable class representing a key 
 */
public class Key {
    
    private final Integer sharps;
    private final Integer tonicReducedPitch;
    private final String tonicAbsoluteName;
    private final Boolean isMajor;

    /*
     * Abstraction Function:
     * represents the key with sharps sharps.  Tonality is major if isMajor = true, else minor.  
     * 
     * Rep Invariant:
     * None. 
     * 
     * Rep Exposure:
     * only returns primitives and immutables
     */
    
    private static final Integer MAX_SHARPS_FLATS = 6;
    static final Integer SEMITONES_IN_OCTAVE = 12;
    static final Integer PITCHES_IN_SCALE = 7;
    private static final List<String> MAJOR_KEY_NAMES = Arrays.asList(
            "Gb", "Db", "Ab", "Eb", "Bb", "F", "C", "G", "D", "A", "E", "B", "F#");
    private static final List<String> MINOR_KEY_NAMES = Arrays.asList(
            "Eb", "Bb", "F", "C", "G", "D", "A", "E", "B", "F#", "C#", "G#", "D#");
    
    private static final List<Integer> MAJOR_SCALE = Arrays.asList(
            0, 2, 4, 5, 7, 9, 11);
    private static final List<Integer> MAJOR_KEY_PITCHES = Arrays.asList(
            6, 1, 8, 3, 10, 5, 0, 7, 2, 9, 4, 11, 6);
    private static final List<Integer> MINOR_SCALE = Arrays.asList(
            0, 2, 3, 5, 7, 8, 10);
    private static final List<Integer> MINOR_KEY_PITCHES = Arrays.asList(
            3, 10, 5, 0, 7, 2, 9, 4, 11, 6, 1, 8, 3);
    
    
    private static final Key C_MAJOR = new Key(0,true); 
            
    
    /**
     * Constructs a key with given key signature and tonality
     * @param sharps number of sharps/flats in key signature, in -6,...,6
     * @param isMajor if true, then major; else minor
     */
    public Key(int sharps, boolean isMajor){
        if (sharps < -6 || sharps > 6){
            throw new UnsupportedOperationException("sharps should be in -6,...,6.");
        }
        this.sharps = sharps;
        this.isMajor = isMajor;
        if (isMajor){
            this.tonicAbsoluteName = MAJOR_KEY_NAMES.get(sharps + MAX_SHARPS_FLATS);
            this.tonicReducedPitch = MAJOR_KEY_PITCHES.get(sharps + MAX_SHARPS_FLATS);
        }
        else{
            this.tonicAbsoluteName = MINOR_KEY_NAMES.get(sharps + MAX_SHARPS_FLATS);
            this.tonicReducedPitch = MINOR_KEY_PITCHES.get(sharps + MAX_SHARPS_FLATS);
        }
    }

    
    /******************
     * BASIC CHECKING *
     ******************/
    
    /**
     * @param reducedPitch a pitch in (0...11)
     * @return whether reducedPitch is in this key
     */
    public boolean isReducedPitchInKey(int reducedPitch){
        int reducedPitchRelativeToTonic = (reducedPitch - tonicReducedPitch + SEMITONES_IN_OCTAVE) % SEMITONES_IN_OCTAVE;
        if (isMajor){
            return MAJOR_SCALE.contains(reducedPitchRelativeToTonic);
        }
        else{
            return MINOR_SCALE.contains(reducedPitchRelativeToTonic);
        }
    }
    
    /***************
     * CONVERSIONS *
     ***************/
    
    /**
     * Gets the reduced pitch of a scale degree
     * @param scaleDegree an integer in 1,...,7
     * @param accidental number of semitones to raise/lower
     * @return the reduced (0-11) pitch of a scale degree
     */
    public int getReducedPitchOfScaleDegree(int scaleDegree, int accidental){
        if (scaleDegree<0 || scaleDegree>7){
            throw new UnsupportedOperationException("Bad scale degree.");
        }
        if (isMajor){
            return (MAJOR_SCALE.get(scaleDegree-1)+ tonicReducedPitch + accidental)
                    % SEMITONES_IN_OCTAVE;
        }
        else{
            return (MINOR_SCALE.get(scaleDegree-1)+ tonicReducedPitch + accidental)
                    % SEMITONES_IN_OCTAVE;
        }
    }
    
    /**
     * Gets the reduced pitch of a letter note
     * @param letter note A-G
     * @param accidental number of semitones to raise/lower from the default in scale
     * @return the resulting reduced (0-11) pitch 
     */
    public int getReducedPitchOfLetterNote(char c, int accidental){
        int scaleDegree = letterNoteToScaleDegree(c);
        System.err.println(scaleDegree);
        return getReducedPitchOfScaleDegree(scaleDegree, accidental);
    }
    
    /**
     * Converts a letter note to a scale degree
     * @param c an uppercase character in 'A'...'G'. Represents the corresponding
     * note in the key, where the key signature is implied.  
     * @return the scale degree (1-7) pitch of the note with the inputted letter
     * name in this key
     */
    private int letterNoteToScaleDegree(char c){
        if ((c - 'A' < 0) || (c - 'G' > 0)){
            throw new UnsupportedOperationException("Invalid note.");
        }
        char tonic = tonicAbsoluteName.charAt(0);
        System.err.println(c);
        System.err.println(tonic);
        return (c - tonic + PITCHES_IN_SCALE) % PITCHES_IN_SCALE + 1;
    }
    
    /**
     * Converts a scale degree to a letter note
     * @param A scale degree in 1,...,7
     * @return the letter note (minus accidentals, which are implied) corresponding
     * to the inputted scale degree of the key.  
     */
    private char scaleDegreeToLetterNote(int scaleDegree){
        char tonicLetter = tonicAbsoluteName.charAt(0);
        int tonicLetterIndex = tonicLetter - 'A';
        int ansLetterIndex = (tonicLetterIndex + scaleDegree - 1)%PITCHES_IN_SCALE;
        return (char) ('A' + ansLetterIndex);
    }
    
    /**
     * Converts a reduced pitch in the key to a scale degree
     * @param reducedPitch a reduced pitch in the key
     * @return the scale degree (1-7) corresponding to this pitch
     */
    private int reducedPitchToScaleDegree(int reducedPitch){
        if (!isReducedPitchInKey(reducedPitch)){
            throw new UnsupportedOperationException("Note not in key.");
        }
        
        int reducedPitchRelativeToTonic = (reducedPitch - tonicReducedPitch + SEMITONES_IN_OCTAVE) 
                % SEMITONES_IN_OCTAVE;
        int index = 0;
        if (isMajor){
            while(MAJOR_SCALE.get(index) != reducedPitchRelativeToTonic){
                index++;
                if (index == 7){
                    throw new RuntimeException("Should not get here.");
                }
            }
        }
        else{
            while(MINOR_SCALE.get(index) != reducedPitchRelativeToTonic){
                index++;
                if (index == 7){
                    throw new RuntimeException("Should not get here.");
                }
            }
        }
        return index+1;
    }
    
    /**
     * Converts a reduced pitch to a letter note
     * @param reducedPitch a reduced pitch in the key
     * @return the letter note (A-G, key signature omitted) corresponding
     * to this reduced pitch)
     */
    private char reducedPitchToLetterNote(int reducedPitch){
        return scaleDegreeToLetterNote(reducedPitchToScaleDegree(reducedPitch));
    }
    
    /*******************
     * WRITING PITCHES *
     *******************/
    
    /**
     * Detects if a note in a key would require an accidental if the key signature
     * weren't there.      
     * @param reducedPitch, a pitch which must be in the key.  
     * @return -1 if the key signature flatted the note, +1 if it sharped the note, 
     * and 0 otherwise.   
     */
    private int getKeySigSharpsFlats(int reducedPitch){
        int scaleDegree = reducedPitchToScaleDegree(reducedPitch); 
        
        char letterOfPitch = scaleDegreeToLetterNote(scaleDegree);
        
        int reducedPitchOfLetterWithoutKeySig = 
                C_MAJOR.getReducedPitchOfLetterNote(letterOfPitch, 0);
        
        int diff = (reducedPitch - reducedPitchOfLetterWithoutKeySig + SEMITONES_IN_OCTAVE) 
                % SEMITONES_IN_OCTAVE;
        
        if (diff == 11)
            return -1;
        return diff;
    }
    
    /**
     * Computes the way a note should be written according to music conventions
     * @param reducedPitch 
     * @param accidental
     * @return
     */
    public String getWrittenNameOfReducedPitch(int reducedPitch, int accidental){
        int reducedPitchBasePitch = (reducedPitch-accidental+ Key.SEMITONES_IN_OCTAVE) 
                % Key.SEMITONES_IN_OCTAVE;
        
        int sharpsFlats = getKeySigSharpsFlats(reducedPitchBasePitch);
        
        char baseLetterNote = reducedPitchToLetterNote(reducedPitchBasePitch);
        
        String ans = String.valueOf(baseLetterNote);
        
        if (sharpsFlats == 0){
            if (accidental > 0){
                for (int i=0; i<accidental; i++){
                    ans += "+";
                }
            }
            else if (accidental < 0){
                for (int i=0; i<-accidental; i++){
                    ans += "-";
                }
            }
        }
        else if (sharpsFlats == 1){
            if (accidental>0){
                for (int i=0; i<accidental; i++){
                    ans += "+";
                }
            }
            else if (accidental<0){
                ans += "=";
                for (int i=0; i<-accidental-1; i++){
                    ans += "-";
                }
            }
        }
        else if (sharpsFlats == -1){
            if (accidental>0){
                ans += "=";
                for (int i=0; i<accidental-1; i++){
                    ans += "+";
                }
            }
            else if (accidental<0){
                for (int i=0; i<-accidental; i++){
                    ans += "-";
                }
            }
        }
        return ans;
    }
    


    /*******************
     * Object Contract *
     *******************

    /**
     * Two keys are equal iff they have the same key signature
     * and tonality.  
     * @param object another object
     * @return whether this and object are equal.   
     */
    @Override public boolean equals(Object object){
        if (!(object instanceof Key)){return false;}
        Key that = (Key) object;
        return (this.sharps == that.sharps && this.isMajor == that.isMajor);
    }

    @Override public int hashCode(){
        if (isMajor){
            return sharps;
        }
        else{
            return sharps + 7*MAX_SHARPS_FLATS;
        }
    }

    /**
     * @return the name of this key
     */
    @Override public String toString(){
        if (isMajor){
            return tonicAbsoluteName + " Major";
        }
        else{
            return tonicAbsoluteName + " Minor";
        }
    }
    
}
