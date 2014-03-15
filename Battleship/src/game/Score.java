package game;

/**
 * This class handles a single score for the game.
 * 
 * @author Group 1 - DAT055 2014
 * @version 3.0
 */
public class Score {
	long points;
	String name;
	
	/**
	 * Initiates this score.
	 * @param points The number of points to set in this score
	 * @param name The name of the scoree
	 */
	public Score(long points, String name) {
		this.points = points;
		this.name = name;
	}
	
	/**
	 * Sets a new scorevalue for this score
	 * @param newScore The new score to set
	 */
	public void setPoints(long newScore) {
		this.points = newScore;
	}
	
	/**
	 * Sets the name of this score
	 * @param newName The name to set here
	 */
	public void setName(String newName) {
		this.name = newName;
	}
	
	/**
	 * Returns the value of this score
	 * @return The score
	 */
	public long getPoints() {
		return this.points;
	}
	
	/**
	 * Returns the name of this scores holder
	 * @return The scorees name
	 */
	public String getName() {
		return this.name;
	}
}
