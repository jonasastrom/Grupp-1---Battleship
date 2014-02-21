package Game;

import java.util.ArrayList;

public class HighScore {
	ArrayList<Score> highScore;
	
	public HighScore() {
		highScore = new ArrayList<Score>();
		highScore.add(0,new Score(10000, "Gurka"));
		highScore.add(1,new Score(9000, "Katamaran"));
		highScore.add(2,new Score(8000, "Apa"));
		highScore.add(3,new Score(7000, "Katt"));
		highScore.add(4,new Score(6000, "Ratatosk"));
		highScore.add(5,new Score(5000, "Foo"));
		highScore.add(6,new Score(4000, "Knugen"));
		highScore.add(7,new Score(3000, "Gunhild"));
		highScore.add(8,new Score(2000, "PowerKing"));
		highScore.add(9,new Score(-89, "Sverker"));
	}
	 /**
	  * Adds a score to the high score list if it's greater than a score
	  * currently on the list
	  * @param score The score of the current player
	  * @param name The name of the current player
	  * @return	True if the score was placed in the list
	  */
	public boolean addScore(long score, String name) {
		for(Score currentScore : highScore) {
			if(score > currentScore.getPoints()) {
				highScore.add(highScore.indexOf(currentScore), new Score(score, name));
				highScore.remove(10);
				return true;
			}
		}
		return false;
	}
}
