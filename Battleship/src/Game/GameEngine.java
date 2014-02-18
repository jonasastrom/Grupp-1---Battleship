package Game;
import javax.swing.JOptionPane;

import GUI.Gui;

@SuppressWarnings("unused")		// Because yellow lines are annoying
public class GameEngine {
	private static Gui gui;
	private Player player;
	private AI ai;
	private boolean winPlayer;
	private boolean gameOver;
	private boolean playerTurn;
	
	public enum ZoneState{MISS,HIT,SUNK,SHIP}
	
	public GameEngine(){
		// Creates a new object of GUI (for creating a frame)
		gui = new GUI.Gui(this);
		player = new Player();
		ai = new AI(0);	//0 because we don't need more difficulties for now
	}
	
	/**
	 * This is a main method.
	 * It methods mains.
	 * @param args Does absolutely nothing in this program
	 */
	public static void main(String[] args) {
		GameEngine gamE = new GameEngine();
		
		gamE.run();
	}
	
	public static Gui getGui(){
		return gui;
	}
	
	/**
	 * The main game loop
	 * It sets the player's turn and lets players have their turns in
	 * destroying each other's fleets
	 */
	public void run() {
		boolean playerTurn = true;
		
		newGame();

		while(!gameOver){
			if(playerTurn){
				//player.attack();
				if(!ai.hasShips()){
					winPlayer = true;
					gameOver = true;
					break;
				}
				playerTurn = false;
			}
			else{
			//	if(ai.isShipsPlaced()){    milstolpe 2
			//		int[] coords = ai.attack(lastHit); milstolpe 2
			//		lastHit = player.bomb(coords[0],coords[1]); //bomb the players field,milstolpe 2
			//	}
				if(!player.hasShips()){
					winPlayer = false;
					gameOver = true;
					break;
				}
			}
		}
		gameOver();

	}

	/**
	 *  Sets a new game up by saying that the game is not over
	 *  and making the players place their ships.
	 */
	public void newGame() {
		gameOver = false;
		winPlayer = false;
		playerTurn = true;
		//player.placeShips(); milstolpe 2
		//ai.placeShips();
	}

	/**
	 *  Ends the game, pops a prompt to the player, and cleans variables
	 *  between games. This also starts a new game if the player has prompted it
	 *  whether through the prompt or from being called before the game is over.
	 */
	public void gameOver() {
		
		String winText;
		if(gameOver){	
			if(winPlayer)
				winText = "won";
			else
				winText = "lost";
			if(gui.gameOverText(winText)) {
				winText = null;
				resetGame();
			}
			else
				System.exit(0);
		}
		resetGame();	// If the game is not over but the menu option for a New Game has been chosen, then the win/lose messages won't be displayed.
	}
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
	
	private void resetGame() {
		player = null;
		ai = null;
		gui = null;		// Calls GC here to make sure it does its job
		System.gc();	// This keeps the program from ever taking up too much memory
		gui = new GUI.Gui(this);
		player = new Player();
		ai = new AI(0);
		run();
	}
	
	public boolean isPlayerTurn(){
		return playerTurn;
	}
}
