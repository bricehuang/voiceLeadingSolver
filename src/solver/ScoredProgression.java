package solver;

import chord_data.ChordProgressionWithContext;

/**
 * Immmutable class representing a chord progression with a score
 */
class ScoredProgression{
    private final ChordProgressionWithContext progression;
    private final Integer score; 
    
    ScoredProgression(ChordProgressionWithContext progression, Integer score){
        this.progression = progression;
        this.score = score;
    }
    
    /***********
     * Getters *
     ***********/
    
    /**
     * @return chord progression and context 
     */
    public ChordProgressionWithContext getChordProg(){
        return progression;
    }
    
    /**
     * @return score 
     */
    public Integer getScore(){
        return score;
    }
    
    /*******************
     * Object Contract *
     *******************/
    
    @Override
    public boolean equals(Object other){
        if (!(other instanceof ScoredProgression)){return false;}
        ScoredProgression that = (ScoredProgression) other;
        return (
    		this.progression.equals(that.progression) 
    		&& this.score.equals(that.score)
        );
    }
    
    @Override
    public int hashCode(){
        return this.progression.hashCode() + 317*this.score.hashCode();
    }
    
    @Override
    public String toString(){
        return this.progression.toString() + "\nScore: "+ this.score;
    }

}