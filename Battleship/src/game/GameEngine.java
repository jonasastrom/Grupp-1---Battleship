package game;

import gui.Gui;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * class GameEngine
 * 
 * @author Group 1 - DAT055 2014
 * @version 2.0
 */
@SuppressWarnings("unused")		// Because yellow lines are annoying
public class GameEngine {
	private static Gui gui;
	private Human player;
	private AI ai;
	private boolean winPlayer;
	private boolean gameOver;
	private static boolean playerTurn;
	private LastShot playerLastShot;
	private LastShot aiLastShot;
	private static ZoneListener listener;
	private HighScore highScore;
	private int difficulty;
	private String playerName;
	private long points;
	private boolean difficultyChanged = false;
	
	public enum LastShot {MISS, HIT, SUNK}
	
	public GameEngine(ZoneListener listener){
		// Creates a new object of GUI (for creating a frame)
		difficulty = 0;	// Training mode gets to be the default difficulty
		player = new Human(listener);
		gui = new Gui(this, player); //so gui can place the boats
		difficulty = gui.selectDifficultyWIndow();	//This actually lets you select a difficulty,when that's implemented
		ai = new AI(difficulty, player.getBattlefield(), listener); //ai can use a isSunk bool to keep track if the fleet got sunk
		highScore = new HighScore();
		inputPlayerName();
	}
	
	/**
	 * This is a main method.
	 * It methods mains.
	 * @param args Does absolutely nothing in this program
	 */
	public static void main(String[] args) {
		listener = new ZoneListener();
		GameEngine gamE = new GameEngine(listener);
		gamE.newGame();
		listener.addObserver(gui);
	}
	
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
	 *  Sets a new game up by saying that the game is not over
	 *  and making the players place their ships.
	 */
	public void newGame() {
		playerTurn = false; // before the ships are placed it's not the player's
							// turn
		gameOver = false;
		winPlayer = false;
		playerLastShot = LastShot.MISS;
		aiLastShot = LastShot.MISS;
		if(difficulty != 0)
			player.placeShips(); 
		ai.placeShips();
		try {Thread.sleep(20);} catch (InterruptedException e) {}
			// This gives the GUI enough time to update, since it is apparently on a different thread(??)
	}
	
	

	/**
	 *  Ends the game, pops a prompt to the player, and cleans variables
	 *  between games. This also starts a new game if the player has prompted it
	 *  whether through the prompt or from being called before the game is over.
	 *  Prompt.
	 */
	public void gameOver() {

		String winText;
		boolean newHighScore;
		
		newHighScore = highScore.addScore(points, playerName);
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
	 * Test method. Not going to be accessible by a normal player
	 * This pops up a a game over screen where the player has won or lost
	 * @param gameOverState True means the player has won, false that it hasn't
	 */
	public void testGameOver(boolean gameOverState) {
		if(!gameOverState) {
			gameOver = true;
			winPlayer = false;
			gameOver();
		}
		else if(gameOverState) {
			gameOver = true;
			winPlayer = true;
			gameOver();
		}
	}
	
	public void testCalculator() {
		points = ScoreCalculator.testCalculator();
		highScore.addScore(points, "PROMLG");
		highScore.addScore(5001, "Stefan");
		highScore.addScore(-199, "Jimmie");
		gui.showHighscore(highScore.getHighScoreList());
	}
	
	/**
	 * Test method. Not going to be accessible by normal players
	 * This bombs every single square on the battlefield
	 */
	public void testNuke() {
		System.out.println("Nuclear launch detected");
		for(int x = 0 ; x < 10 ; x++) {
			for(int y = 0 ; y < 10 ; y++) {
				ai.bomb(x, y);
				try {Thread.sleep(10);} catch (InterruptedException e) {} // Gui gets woozy when things get bombed too fast
			}
		}
		System.out.println("Splash");
		gui.changeInformationText("CHEATER!!!!!11!!1!!!");
	}
	
	/**
	 *	Empties the objects, cleans them away and recreates them for a new game 
	 */
	private void resetGame() {
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
		int difficulty = 0;
		
		listener = new ZoneListener();
		player = new Human(listener);
		gui = new Gui(this,player);
		listener.addObserver(gui);
		inputPlayerName();
		if(difficultyChanged = false)
			difficulty = gui.selectDifficultyWIndow();
		ai = new AI(difficulty, player.getBattlefield(), listener);
		newGame();
	}
	
	/**
	 * This is called by the gui once a square has been pressed
	 * It takes the coordinates of that square and sends them to the ai to be bombed
	 * It then lets the ai have its turn
	 * @param x The x coordinate of the column to be bombed
	 * @param y The y coordinate of the row to be bombed
	 */
	public void coordinates (int x, int y ){
		playerTurn = false;			// Player's turn is over
		System.out.println("Coordinates called");
		playerLastShot = ai.bomb(x, y);
		if(playerLastShot == LastShot.SUNK){gui.changeInformationText("You Sunk a Ship!");}
		else if(playerLastShot == LastShot.HIT){gui.changeInformationText("You Hit!");}
		else if(playerLastShot == LastShot.MISS){gui.changeInformationText("You Missed!");}
		
		if (!ai.hasShips()) {
			winPlayer = true;
			gameOver = true;
			gameOver();
		}
	/*	int[] aiAttack = ai.attack(aiLastHit);
	 	if(aiAttack 
		aiLastHit = player.bomb(aiAttack[0], aiAttack[1]);
		if(aiLastShot == LastShot.SUNK){gui.changeInformationText("Admiral Akbar Sunk One of Your Ships!");}
		else if(aiLastShot == LastShot.HIT){gui.changeInformationText("Admiral Akbar Hit!");}
		else if(aiLastShot == LastShot.MISS){gui.changeInformationText("Admiral Akbar Missed!");}
		if (!player.hasShips()) {
			winPlayer = false;
			gameOver = true;
			gameOver();
		}
		*/
		setPlayerTurn(); // is needed because AI's turn is over, it's time to attack AI again.
	}
	
	/**
	 * This fetches the high score from a server, updates the local one,
	 * then uploads the new high score to the server.
	 * If there is no high score on the server, creates one.
	 * @param server The URL to the wanted server
	 */
	public void updateHighScore(String server) throws MalformedURLException, IOException {
		boolean changed = false;
		HighScore newHighScore = null;
		URL url = new URL(server);
		URLConnection connection = url.openConnection();
		
		DataInputStream inputStream = new DataInputStream(connection.getInputStream());
		ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
		try {newHighScore = (HighScore) objectInputStream.readObject();} catch (ClassNotFoundException e) {}
		if(newHighScore != null)
			changed = highScore.compHighScore(newHighScore);
		inputStream.close();
		if(changed){
			DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
			objectOutputStream.writeObject(highScore);
			outputStream.close();
		}
		
	}
	
	/**
	 * This should make the gui print the high score list,
	 * if it exists and isn't empty
	 */
	public void printHighScore() {
		if(highScore != null && !highScore.getHighScoreList().isEmpty())
			gui.showHighscore(highScore.getHighScoreList());
	}
	
	/**
	 * This pops up a text field via gui that allows the player to input its name
	 */
	private void inputPlayerName() {
		//playerName = gui.inputPlayerNameWindow();
	}
	
	/**
	 * This lets the player change difficulty in the middle of a game.
	 * It also starts a new game with that difficulty.
	 * @param newDifficulty The new difficulty that has been chosen
	 */
	public void setDifficulty(int newDifficulty) {
		this.difficulty = newDifficulty;
		difficultyChanged = true;
		resetGame();
		
	}
	
	public int getDifficulty() {
		return difficulty;
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
