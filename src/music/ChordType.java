package music;

/**
 * A class representing all supported chord types
 */
public enum ChordType {
    MAJ, MIN, 
    DOM7, MAJ7, MIN7, DIM7;
    
    /*******************
     * Object Contract *
     *******************/
        
    @Override
    public String toString(){
        if (this.equals(MAJ)){
            return "MAJOR";
        }
        else if (this.equals(MIN)){
            return "MINOR";
        }
        else if (this.equals(DOM7)){
            return "DOMINANT 7TH";
        }
        else if (this.equals(MAJ7)){
            return "MAJOR 7TH";
        }
        else if (this.equals(MIN7)){
            return "MINOR 7TH";
        }
        else if (this.equals(DIM7)){
            return "DIMINISHED 7TH";
        }
        else{
            throw new RuntimeException("Should not get here");
        }
    }
}
