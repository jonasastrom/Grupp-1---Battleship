package game;

import java.util.ArrayList;

/**
 * class HighScore
 * 
 * @author Group 1 - DAT055 2014
 * @version 3.0
 */
public class HighScore {
	ArrayList<Score> highScore;

	/**
	 * Initialises the highscore list with standard names.
	 */
	public HighScore() {
		highScore = new ArrayList<Score>();
		highScore.add(0,new Score(0, "Batman"));		// These are only the
		highScore.add(1,new Score(0, "Superman"));	// the standard Best Players
		highScore.add(2,new Score(0, "Bar"));
		highScore.add(3,new Score(0, "GIT"));
		highScore.add(4,new Score(0, "Ratatosk"));
		highScore.add(5,new Score(0, "Foo"));
		highScore.add(6,new Score(0, "Knugen"));
		highScore.add(7,new Score(0, "Gunhild"));
		highScore.add(8,new Score(0, "Powerking"));
		highScore.add(9,new Score(0, "Resverk"));
	}

	/**
	 * This compares the current high score list to anew high score list and
	 * updated the current one with new scores if they're higher
	 * 
	 * @param newHighScore The high score list to check against
	 * @return True if the highscore was updated, false otherwise.
	 */
	public boolean compHighScore(HighScore newHighScore){
		boolean changed = false;	// Nothing has happened, therefore nothing is changed
		for(Score newScore : newHighScore.getHighScoreList()) {		// Go through every single score in the external high score list
			if(addScore(newScore.getPoints(), newScore.getName()))	// Treat these scores as competing scores, which automatically checks if they're better and puts them into the right place
				changed = true;		// This only sets changed to true because if changed = addScore()
		}							// then it could go from true to false which is very wrong
		return changed;
	}

	/**
	 * Adds a score to the high score list if it's greater than a score
	 * currently on the list
	 * @param points The score of the current player
	 * @param name The name of the current player
	 * @return	True if the score was placed in the list
	 */
	public boolean addScore(long points, String name) {
		if(name == null || name.isEmpty())
			return false;
		for(Score currentScore : highScore) {
			if(points == currentScore.getPoints() && name.equals(currentScore.getName())){
				return false;
			}
		}
		for(Score currentScore : highScore) {	// Check the high score list from the top
			if(points > currentScore.getPoints()) {	// If the score is higher than the list's score,
				highScore.add(highScore.indexOf(currentScore), new Score(points, name));	// then it should be inserted at the list's score's index, pushing all other scores down one step
				highScore.remove(10); // This list keeps ten high scores, the 11th gets removed
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the highscores.
	 * @return The highscorelist
	 */
	public ArrayList<Score> getHighScoreList() {
		return this.highScore;
	}
}
