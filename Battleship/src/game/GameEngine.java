package game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import gui.Gui;

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
	private boolean playerLastHit;
	private boolean aiLastHit;
	private static ZoneListener listener;
	private HighScore highScore;
	
	
	public GameEngine(ZoneListener listener){
		// Creates a new object of GUI (for creating a frame)
		int difficulty = 0;	//0 because we don't need more difficulties for now
		//difficulty = gui.selectDifficultyWindow()	//This actually lets you select a difficulty,when that's implemented
		gui = new Gui(this);
		player = new Human(listener);
		ai = new AI(difficulty, player.getBattlefield(), listener);
		highScore = new HighScore();
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
	 *  Sets a new game up by saying that the game is not over
	 *  and making the players place their ships.
	 */
	public void newGame() {
		playerTurn = false; // before the ships are placed it's not the player's
							// turn
		gameOver = false;
		winPlayer = false;
		playerLastHit = false;
		aiLastHit = false;
		// player.placeShips(); milstolpe 2
		ai.placeShips();
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		playerTurn = true; 
	}

	/**
	 *  Ends the game, pops a prompt to the player, and cleans variables
	 *  between games. This also starts a new game if the player has prompted it
	 *  whether through the prompt or from being called before the game is over.
	 *  Prompt.
	 */
	public void gameOver() {
		
		String winText;
		if(gameOver){	
			if(winPlayer)
				winText = "won";
			else
				winText = "lost";
			if(gui.gameOverText(winText)) {	// gameOverText returns a boolean depending on which button got pressed
				winText = null;				// If yes, then the game is reset and remade
				resetGame();
			}
			else
				System.exit(0);				// Else the game exits
		}
		else
			resetGame();	// If the game is not over but the menu option for a New Game has been chosen, then the win/lose messages won't be displayed.
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
		gui = new Gui(this);
		listener = new ZoneListener();
		listener.addObserver(gui);
		player = new Human(listener);
		// difficulty = gui.selectDifficultyWindow()
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
		playerLastHit = ai.bomb(x, y);
		if(playerLastHit){gui.changeInformationText("You Hit!");}
		else{gui.changeInformationText("You Missed!");}
		if (!ai.hasShips()) {
			winPlayer = true;
			gameOver = true;
			gameOver();
		}
	/*	int[] aiAttack = ai.attack(aiLastHit);
		aiLastHit = player.bomb(aiAttack[0], aiAttack[1]);
		if(aiLastHit){gui.changeInformationText("Admiral Akbar Hit!");}
		else{gui.changeInformationText("Admiral Akbar Missed!");}
		if (!player.hasShips()) {
			winPlayer = false;
			gameOver = true;
			gameOver();
		}
		*/
		playerTurn = true;			// Player's turn again
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
			highScore.compHighScore(newHighScore);
		inputStream.close();
		//DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
		//ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
		
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
