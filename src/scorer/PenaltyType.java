package scorer;

/**
 * A class representing all possible penalties
 */
public enum PenaltyType {
    MOVEMENT,
    
    DOUBLING, VOICE_OVERLAP,
    PARALLEL, DIRECT, MELODIC_INTERVAL, VOICE_CROSSING,
    DOM_SEVEN_RES, DIM_SEVEN_RES;
    
    @Override
    public String toString(){
        if (this.equals(MOVEMENT)){
            return "Movement Penalty";
        }
        else if (this.equals(DOUBLING)){
            return "Doubling Penalty";
        }
        else if (this.equals(VOICE_OVERLAP)){
            return "Voice Overlap Penalty";
        }
        else if (this.equals(PARALLEL)){
            return "Parallel Interval Penalty";
        }
        else if (this.equals(DIRECT)){
            return "Direct Interval Penalty";
        }
        else if (this.equals(MELODIC_INTERVAL)){
            return "Melodic Interval Penalty";
        }
        else if (this.equals(VOICE_CROSSING)){
            return "Voice Crossing Penalty";
        }
        else if (this.equals(DOM_SEVEN_RES)){
            return "Bad Dominant Seven Resolution Penalty"; 
        }
        else if (this.equals(DIM_SEVEN_RES)){
            return "Bad Diminished Seven Resolution Penalty";
        }
        else {
            throw new RuntimeException("Should not get here.");
        }
    }
}
