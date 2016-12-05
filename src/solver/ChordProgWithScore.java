package solver;

import chords.ChordProgression;

/**
 * Immmutable class representing a chord progression with a score
 */
class ChordProgWithScore{
    private final ChordProgression chordProg;
    private final Integer score; 
    
    ChordProgWithScore(ChordProgression chordProg, Integer score){
        this.chordProg = chordProg;
        this.score = score;
    }
    
    /***********
     * Getters *
     ***********/
    
    /**
     * @return chord progression 
     */
    public ChordProgression getChordProg(){
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
        if (!(other instanceof ChordProgWithScore)){return false;}
        ChordProgWithScore that = (ChordProgWithScore) other;
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