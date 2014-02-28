package highscore;

import game.GameEngine.Difficulty;

import game.Fleet;
import game.Ship;

import java.util.ArrayList;

public class ScoreCalculator
{
	public ScoreCalculator() {}
	
	public int calculate(Difficulty difficulty, Fleet aiFleet, Fleet humanFleet)
	{
		ArrayList<Ship> aiShips = aiFleet.getShips();
		ArrayList<Ship> humanShips = humanFleet.getShips();
		
		return 3000;
	}
}