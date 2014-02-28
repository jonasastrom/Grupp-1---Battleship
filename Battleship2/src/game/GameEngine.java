package game;

import java.util.ArrayList;

import gui.Gui;
import highscore.Score;
import highscore.ScoreCalculator;
import highscore.SocketConnector;

/**
 * TODO class description
 * 
 * @author Group 1 - DAT055 2014
 * @version 3.0
 */
public class GameEngine
{
	public enum BombStatus {MISS, HIT, SUNK}
	public enum ScoreStatus {ADDED, LOW, ERROR}
	public enum Difficulty {TRAINING, EASY, NORMAL, HARD, INSANE}

	private static Gui gui;

	private AI ai;
	private Human human;
	private ZoneListener listener;
	private Difficulty difficulty;
	private BombStatus aiLastBomb;
	private BombStatus humanLastBomb;
	private ScoreCalculator scoreCalculator;
	private SocketConnector socketConnector;
	private boolean humanWin = false;
	private boolean gameOver = false;
	private boolean bombsAway = false;
	private boolean humanTurn = false;

	/**
	 * TODO constructor description
	 */
	public GameEngine()
	{
		init();
	}

	/**
	 * TODO method description
	 * @param args Not used
	 */
	public static void main(String[] args)
	{
		new GameEngine();
	}

	/**
	 * TODO method description
	 * @return
	 */
	public boolean isBombsAway()
	{
		return bombsAway;
	}

	/**
	 * TODO method description
	 * @return
	 */
	public boolean isHumanTurn()
	{
		return humanTurn;
	}

	/**
	 * TODO method description
	 * @return
	 */
	public static Gui getGui()
	{
		return gui;
	}

	/**
	 * Check the difficulty of the current game.
	 * @return TRAINING, EASY, NORMAL, HARD or INSANE
	 */
	public Difficulty getDifficulty()
	{
		return difficulty;
	}

	/**
	 * TODO method description
	 */
	public void init()
	{
		scoreCalculator = new ScoreCalculator();
		socketConnector = new SocketConnector();

		listener = new ZoneListener();
		human = new Human(listener);
		gui = new Gui(this,human);

		difficulty = gui.selectDifficulty();

		ai = new AI(difficulty,human.getBattlefield(),listener);

		listener.addObserver(gui);

		Thread thread = new Thread()
		{
			public void run()
			{
				newGame();

				while (true) {
					try { Thread.sleep(100); }
					catch (InterruptedException e) {}

					if (bombsAway && !humanTurn) {

						// Simulate thinking
						try { Thread.sleep(500); }
						catch (InterruptedException e) {}

						aiTurn();
					}
				}
			}
		};
		thread.start();
	}

	/**
	 * Start the game by telling the players
	 * to place their ships and tell the GUI
	 * to enable the battlefield.
	 */
	public void newGame()
	{
		ai.placeShips();

		//human.placeShips();
		human.placeShipsDebug();

		// TODO remove?
		try { Thread.sleep(100); }
		catch (InterruptedException e) {}

		bombsAway = true;
		humanTurn = true;

		gui.enableBattlefield();
	}

	/**
	 * TODO method description
	 */
	public void gameOver()
	{
		if (gameOver) {

			boolean highScoreOpen = false;
			String winText;

			if (humanWin) winText = "won";
			else {
				// Show hidden AI ships
				ai.getBattlefield().showShips(); 
				winText = "lost";
			}

			int points = scoreCalculator.calculate(
					difficulty, ai.getFleet(), human.getFleet());

			if (gui.prompt("Game Over", "You have "+winText +"! You scored "+
					points+"\nWould you like to submit your score?")) {

				// Get the player name
				String name = gui.enterName();

				if (name != null) {

					ScoreStatus scoreStatus;
					scoreStatus = socketConnector.sendHighScore(
							new Score(points,name));

					String scoreText = "";
					if (scoreStatus == ScoreStatus.ADDED)
						scoreText = "Yay! You got a new high score!";
					else if (scoreStatus == ScoreStatus.LOW)
						scoreText = "Sorry but your score was too low...";
					else if (scoreStatus == ScoreStatus.ERROR)
						gui.error("Unable to contact the server!");

					if (scoreStatus != ScoreStatus.ERROR) {
						if (gui.prompt("High Score", scoreText+
								"\nWould you like to view the high scores?")) {
							gui.viewHighScores();
							highScoreOpen = true;
						}
					}
				}
			}

			if (!highScoreOpen) {
				if (gui.prompt("Game Over", "Would you like to play again?")) {
					resetGame();
				}
				else System.exit(0);
			}

		} else {
			// If the game is not over but the menu option
			// for a new game has been chosen
			resetGame();
		}
	}

	/**
	 * Empties the object references, cleans them
	 * away and recreates them for a new game.
	 */
	private void resetGame()
	{
		gameOver = false;
		humanWin = false;
		bombsAway = false;
		humanTurn = false;

		aiLastBomb = BombStatus.MISS;
		humanLastBomb = BombStatus.MISS;

		scoreCalculator = null;
		socketConnector = null;

		listener.deleteObservers();
		gui.setVisible(false);
		gui.removeAll();
		gui = null;

		// TODO remove?
		try { Thread.sleep(50); }
		catch (InterruptedException e) {}

		ai = null;
		human = null;
		listener = null;

		// Call the to make sure it does it's job to keep
		// the program from ever taking up too much memory
		System.gc();

		try { Thread.sleep(150); }
		catch (InterruptedException e) {}

		init();
	}

	/**
	 * This is called by the GUI once a square
	 * has been pressed. It takes the coordinates
	 * of that square and sends them to the AI to
	 * be bombed.
	 * @param x The x-coordinate of the zone
	 * @param y The y-coordinate of the zone
	 */
	public void coordinates (int x, int y)
	{
		humanLastBomb = ai.bomb(x,y);

		String ship = "ship";
		if (ai.getBattlefield().hasShip(x,y))
			ship = ai.getBattlefield().getShip(x,y).getName();

		if (humanLastBomb == BombStatus.SUNK)
			gui.changeInformationText("You sunk Admiral Akbar's "+ship+"!");
		else if (humanLastBomb == BombStatus.HIT)
			gui.changeInformationText("You hit!");
		else if (humanLastBomb == BombStatus.MISS)
			gui.changeInformationText("You missed!");

		if (!ai.hasShips()) {
			humanWin = true;
			gameOver = true;
			gameOver();
		}

		// Human's turn is over
		humanTurn = false;
	}

	/**
	 * TODO method description
	 */
	private void aiTurn()
	{
		// AI should only attack if it's not training mode
		if (difficulty != Difficulty.TRAINING) {

			int[] aiAttack = ai.attack(aiLastBomb);

			aiLastBomb = human.bomb(aiAttack[0],aiAttack[1]);

			String ship = "ship";
			if (human.getBattlefield().hasShip(aiAttack[0],aiAttack[1]))
				ship = human.getBattlefield().getShip(aiAttack[0],aiAttack[1]).getName();

			if (aiLastBomb == BombStatus.SUNK)
				gui.changeInformationText("Admiral Akbar sunk your "+ship+"!");
			else if (aiLastBomb == BombStatus.HIT)
				gui.changeInformationText("Admiral Akbar hit your "+ship+"!");
			else if (aiLastBomb == BombStatus.MISS)
				gui.changeInformationText("Admiral Akbar missed!");

			if (!human.hasShips()) {
				humanWin = false;
				gameOver = true;
				gameOver();
			}
		}

		// AI is done, player's turn now
		humanTurn = true;
	}

	/**
	 * TODO method description
	 * @return
	 */
	public ArrayList<Score> getHighScores()
	{
		ArrayList<Score> highScores = socketConnector.loadHighScores();

		if (highScores == null) {
			gui.error("Unable to contact the server!");
			return null;
		} else {
			return highScores;
		}
	}
}