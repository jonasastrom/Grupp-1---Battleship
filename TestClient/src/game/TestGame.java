package game;

import java.util.ArrayList;

import highscore.Score;
import highscore.SocketConnector;

public class TestGame
{
	public enum ScoreStatus {ADDED, LOW, ERROR}
	
	private SocketConnector socketConnector;
	
	public TestGame()
	{
		System.out.println("Welcome to TestGame!");
		
		System.out.println("\nCreating SocketConnector...");
		socketConnector = new SocketConnector();
		
		ArrayList<Score> highScores;
		
		System.out.println("\nCalling loadHighScores...");
		highScores = socketConnector.loadHighScores();
		
		if (highScores == null) {
			System.out.println("\nUnable to load high scores!");
			System.out.println("Exiting system...");
			System.exit(0);
		} else {
			System.out.println("\nPrinting high score list:");
			System.out.println(highScores.toString());
		}
		
		ScoreStatus scoreStatus;
		
		System.out.println("\nCalling sendHighScore with Bob...");
		scoreStatus = socketConnector.sendHighScore(new Score(1000,"Bob"));
		if (scoreStatus == ScoreStatus.ADDED)
			System.out.println("The score was added to the top list.");
		else if (scoreStatus == ScoreStatus.LOW)
			System.out.println("The score was too low to make the top list.");
		else if (scoreStatus == ScoreStatus.ERROR)
			System.out.println("Unable to send high score!");
		
		System.out.println("\nCalling sendHighScore with Alice...");
		scoreStatus = socketConnector.sendHighScore(new Score(2000,"Alice"));
		if (scoreStatus == ScoreStatus.ADDED)
			System.out.println("The score was added to the top list.");
		else if (scoreStatus == ScoreStatus.LOW)
			System.out.println("The score was too low to make the top list.");
		else if (scoreStatus == ScoreStatus.ERROR)
			System.out.println("Unable to send high score!");
		
		System.out.println("\nCalling sendHighScore with John...");
		scoreStatus = socketConnector.sendHighScore(new Score(3000,"John"));
		if (scoreStatus == ScoreStatus.ADDED)
			System.out.println("The score was added to the top list.");
		else if (scoreStatus == ScoreStatus.LOW)
			System.out.println("The score was too low to make the top list.");
		else if (scoreStatus == ScoreStatus.ERROR)
			System.out.println("Unable to send high score!");
		
		System.out.println("\nCalling loadHighScores again...");
		highScores = socketConnector.loadHighScores();
		
		if (highScores == null) {
			System.out.println("\nUnable to load high scores!");
			System.out.println("Exiting system...");
			System.exit(0);
		} else {
			System.out.println("\nPrinting high score list:");
			System.out.println(highScores.toString());
		}
		
		System.out.println("\nExiting system...");
		System.exit(0);
	}
}
