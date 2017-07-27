package solver;

import chords.ChordProgressionDeprecated;

/**
 * Immmutable class representing a chord progression with a score
 */
class ChordProgWithScoreDeprecated{
    private final ChordProgressionDeprecated chordProg;
    private final Integer score; 
    
    ChordProgWithScoreDeprecated(ChordProgressionDeprecated chordProg, Integer score){
        this.chordProg = chordProg;
        this.score = score;
    }
    
    /***********
     * Getters *
     ***********/
    
    /**
     * @return chord progression 
     */
    public ChordProgressionDeprecated getChordProg(){
        return chordProg;
    }
    
    public Integer getScore(){
        return score;
    }
    
    /*******************
     * Object Contract *
     *******************/
    
    @Override
    public boolean equals(Object other){
        if (!(other instanceof ChordProgWithScoreDeprecated)){return false;}
        ChordProgWithScoreDeprecated that = (ChordProgWithScoreDeprecated) other;
        return (this.chordProg.equals(that.chordProg) 
                && this.score.equals(that.score));
    }
    
    @Override
    public int hashCode(){
        return this.chordProg.hashCode() + 317*this.score.hashCode();
    }
    
    @Override
    public String toString(){
        return this.chordProg.toString() + "\nScore: "+ this.score;
    }

}