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
 * 
 * @author Group 1 - DAT055 2014
 * @version 2.0
 */
public class GameEngine{
	private static Gui gui;
	private Human player;
	private AI ai;
	private boolean winPlayer;
	private boolean gameOver;
	private static boolean playerTurn;
	private LastShot playerLastShot;
	private LastShot aiLastShot;
	private ZoneListener listener;
	private HighScore highScore;
	private int difficulty;
	private String playerName;
	private long points;
	private boolean difficultyChanged;

	public enum LastShot {MISS, HIT, SUNK}

	public GameEngine(){
		highScore = new HighScore();
		difficultyChanged = false;
		init(); 
	}

	/**
	 * This is a main method.
	 * It methods mains.
	 * @param args Does absolutely nothing in this program
	 */
	public static void main(String[] args) {
		new GameEngine();
	}

	public static Gui getGui(){
		return gui;
	}

	public void init(){

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
			public void run(){ //kill the thread?
				newGame();
			}
		}; 
		thread.start();
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
		player.placeShips();
		try {Thread.sleep(20);} catch (InterruptedException e) {}
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
		boolean newHighScore = false;
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
		highScore.addScore(points, playerName);
		highScore.addScore(5001, "Stefan");
		highScore.addScore(-199, "Jimmie");
		gui.showHighscore(highScore.getHighScoreList());
		System.out.println("Testing server thingy");
		testHighScoreServer();
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
	 * This is called by the gui once a square has been pressed
	 * It takes the coordinates of that square and sends them to the ai to be bombed
	 * It then lets the ai have its turn
	 * @param x The x coordinate of the column to be bombed
	 * @param y The y coordinate of the row to be bombed
	 */
	public void coordinates (int x, int y ){
		playerTurn = false;			// Player's turn is over
		playerLastShot = ai.bomb(x, y);
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
	 * test
	 */
	public void testHighScoreServer() {
		updateHighScore();
	}

	/**
	 * 
	 */
	public void updateHighScore(){
		try {
			BufferedReader in = new BufferedReader(new FileReader("highscore.txt"));
			boolean changed = false;
			String string = "";
			String[] splitString = null;
			if(( string = in.readLine()) != null){
				splitString = string.split(" ");
				for(int i = 0; i < splitString.length;){
					if(highScore.addScore(Long.parseLong(splitString[i++]), splitString[i++])){
						changed = true;
					}
				}
			}
			in.close();
			if(changed == true){
				writeHighScore();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e){
			e.printStackTrace();
		}
	}

	/**
	 * This method writes the highscore to a text-file
	 */
	public void writeHighScore(){
		System.out.println("write något");

		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("highscore.txt"));
			ArrayList<Score> list = highScore.getHighScoreList();
			Score score = null;
			for(int i = 0; i < 10; i++){
				score = list.get(i);
				out.write(score.getPoints() + " " + score.getName() + " ");
			}
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	/**
	 * This should make the gui print the high score list,
	 * if it exists and isn't empty
	 */
	public void printHighScore() {
		updateHighScore();
		if(highScore != null && !highScore.getHighScoreList().isEmpty())
			gui.showHighscore(highScore.getHighScoreList());
	}

	/**
	 * This pops up a text field via gui that allows the player to input its name
	 */
	private void inputPlayerName() {
		String string = gui.enterName();
		String[] splitString = string.split(" ");
		playerName = "";
		for( String split : splitString){
			playerName = playerName + split;
		}
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
