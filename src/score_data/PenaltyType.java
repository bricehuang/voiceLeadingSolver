package score_data;

/**
 * A class representing all penalties that can be assigned to 
 * chords or transitions
 */
public interface PenaltyType {

	/**
	 * @return the score-value of this penalty
	 */
    public int value();

}
