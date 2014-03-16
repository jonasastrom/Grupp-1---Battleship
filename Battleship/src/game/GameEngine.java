package game;

import gui.Gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * class GameEngine
 * Starts the game and handles the scripted events.
 * 
 * @author Group 1 - DAT055 2014
 * @version 2.0
 */
public class GameEngine{
	private Human player;
	private AI ai;
	private static Gui gui;
	private int difficulty;
	private long points;
	private String playerName;
	private boolean winPlayer;
	private boolean gameOver;
	private boolean difficultyChanged;
	private static boolean playerTurn;
	private LastShot playerLastShot;
	private LastShot aiLastShot;
	private ZoneListener listener;
	private HighScore highScore;
	public enum LastShot {MISS, HIT, SUNK}

	/**
	 * Constructor of the class - Starts the initiation
	 */
	public GameEngine(){
		highScore = new HighScore();
		difficultyChanged = false;
		init(); 
	}

	/**
	 * The main method - Starts the game.
	 * @param args Does absolutely nothing in this program
	 */
	public static void main(String[] args) {
		new GameEngine();
	}

	/**
	 *  Sets up a new game.
	 */
	public void newGame() {
		playerTurn = false; // before the ships are placed it's not the player's
		// turn
		gameOver = false;
		winPlayer = false;
		playerLastShot = LastShot.MISS;
		aiLastShot = LastShot.MISS;
		player.placeShips();
		try {Thread.sleep(20);} catch (InterruptedException e) {}
		ai.placeShips();
		try {Thread.sleep(20);} catch (InterruptedException e) {}
		// This gives the GUI enough time to update
	}

	/**
	 * Initiates new objects.
	 */
	public void init(){
	
		points = 0;
		listener = new ZoneListener();
		player = new Human(listener);
		gui = new Gui(this, player); //so gui can place the boats
		inputPlayerName();
		if(!difficultyChanged)
			difficulty = gui.selectDifficultyWIndow();	//This actually lets you select a difficulty
		difficultyChanged = false;
		ai = new AI(difficulty, player.getBattlefield(), listener);
		listener.addObserver(gui);
		Thread thread = new Thread(){
			public void run(){ 
				newGame();
			}
		};
		thread.start();
	}

	/**
	 *  Ends the game, the player can either quit or start a new game.
	 */
	public void gameOver() {
	
		String winText;
		highScore.addScore(points, playerName);
		if (gameOver) {
			if (winPlayer)
				winText = "won";
			else {
				ai.getBattlefield().showShips(); // AI's ships will be visible 
				winText = "lost";
			}
			if (gui.gameOverText(winText)) { // gameOverText returns a boolean
				// depending on which button got
				// pressed
				winText = null; // If yes, then the game is reset and remade
				resetGame();
			} else
				System.exit(0); // Else the game exits
		} else
			resetGame(); // If the game is not over but the menu option for a
		// New Game has been chosen, then the win/lose
		// messages won't be displayed.
	}

	/**
	 *	Empties the objects, cleans them away and recreates them for a new game 
	 */
	private void resetGame() {
		listener.deleteObservers();
		gui.removeAll();
		gui.setVisible(false);
		gui = null;
		try {Thread.sleep(50);} catch (InterruptedException e1) {}
		player = null;
		ai = null;
		listener = null;

		System.gc();	// Calls GC here to make sure it does its job
		// This keeps the program from ever taking up too much memory
		// I do this because I think the GC is lazy
		try {Thread.sleep(150);} catch (InterruptedException e2) {}
		init();
	}

	/**
	 * Called when a square has been pressed
	 * It takes the coordinates of that square and sends them to the ai to be bombed
	 * @param x The x coordinate of the column to be bombed
	 * @param y The y coordinate of the row to be bombed
	 */
	public void coordinates (int x, int y ){
		playerTurn = false;			// Player's turn is over
		playerLastShot = ai.bomb(x, y);
		points = points + ScoreCalculator.updateScore(playerLastShot);
		if(playerLastShot == LastShot.SUNK){gui.changeInformationText("You Sunk a Ship!");}
		else if(playerLastShot == LastShot.HIT){gui.changeInformationText("You Hit!");}
		else if(playerLastShot == LastShot.MISS){gui.changeInformationText("You Missed!");}
		if (!ai.hasShips()) {
			winPlayer = true;
			gameOver = true;
			gameOver();
		}
	
		if(difficulty != 0){		//Ai should only attack if it's not training mode
			int[] aiAttack = ai.attack(aiLastShot);
			aiLastShot = player.bomb(aiAttack[0], aiAttack[1]);
			if(aiLastShot == LastShot.SUNK){gui.changeInformationText("Admiral Akbar Sunk One of Your Ships!");}
			else if(aiLastShot == LastShot.HIT){gui.changeInformationText("Admiral Akbar Hit!");}
			else if(aiLastShot == LastShot.MISS){gui.changeInformationText("Admiral Akbar Missed!");}
			if (!player.hasShips()) {
				winPlayer = false;
				gameOver = true;
				gameOver();
			}
		}
		setPlayerTurn(); // Ai done, player's turn now
	}

	/**
	 * Gets the gui object
	 * @return the object of gui
	 */
	public static Gui getGui(){
		return gui;
	}

	/**
	 * Called by gui to say the whole fleet is now placed by the human player
	 * The player can start attacking the enemy
	 */
	public void setPlayerTurn(){
		playerTurn = true;
	}


	/**
	 * This updates the current high score list
	 * and makes the gui print it
	 */
	public void printHighScore() {
		updateHighScore();
		if(highScore != null && !highScore.getHighScoreList().isEmpty())
			gui.showHighscore(highScore.getHighScoreList());
	}

	/**
	 * This reads the high scores from a file, adds it to the current
	 * high scores and if there's been a change, then writes 
	 */
	public void updateHighScore(){
		try {
			BufferedReader in = new BufferedReader(new FileReader("highscore.txt"));
			String string = "";
			String[] splitString = null;
			if(( string = in.readLine()) != null){
				splitString = string.split(" ");
				for(int i = 0; i < splitString.length;){
					highScore.addScore(Long.parseLong(splitString[i++]), splitString[i++]);
				}
			}
			in.close();
			writeHighScore();
		} 	
		catch (FileNotFoundException e){e.printStackTrace();}
		catch (IOException e){e.printStackTrace();}
		catch (NullPointerException e){e.printStackTrace();}
	}

	/**
	 * This method writes the highscore to a text-file
	 */
	public void writeHighScore(){
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("highscore.txt"));
			ArrayList<Score> list = highScore.getHighScoreList();
			Score score = null;
			for(int i = 0; i < 10; i++){
				score = list.get(i);
				out.write(score.getPoints() + " " + score.getName() + " ");
			}
			out.close();
		} 
		catch (IOException e){e.printStackTrace();}		
	}

	/**
	 * This pops up a text field via gui that allows the player to input its name
	 * If the name contains spaces, they're removed.
	 */
	private void inputPlayerName() {
		String string = gui.enterName();
		if(string == null){
			string = "";
		}
		String[] splitString = string.split(" ");
		playerName = "";
		for( String split : splitString){
			playerName = playerName + split;
		}
	}

	/**
	 * Gets the difficulty for the current game.
	 * @return 0 if training, 1 if easy, 2 if normal, 3 if hard, 4 if impossible
	 */
	public int getDifficulty() {
		return difficulty;
	}

	/**
	 * This lets the player change difficulty in the middle of a game.
	 * It also starts a new game with that difficulty.
	 * @param int newDifficulty The new difficulty that has been chosen
	 * 							0 is training, 1 is training, 2 is normal, 3 is hard, 4 is impossible
	 */
	public void setDifficulty(int newDifficulty) {
		this.difficulty = newDifficulty;
		difficultyChanged = true;
		resetGame();
	}
	
	/**
	 * Returns if it's the players turn
	 * @return returns true if it's the players turn
	 * 			else false
	 */
	public static boolean isPlayerTurn(){
		return playerTurn;
	}
}
