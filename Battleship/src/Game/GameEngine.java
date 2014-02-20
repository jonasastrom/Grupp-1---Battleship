package Game;


import GUI.Gui;
import Game.AI;
import Game.Human;
import Game.Player;

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
	
	
	public GameEngine(ZoneListener listener){
		// Creates a new object of GUI (for creating a frame)
		gui = new Gui(this);
		player = new Human(listener);
		ai = new AI(0, listener);	//0 because we don't need more difficulties for now
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
		gameOver = false;
		winPlayer = false;
		playerLastHit = false;
		aiLastHit = false;
		playerTurn = false; //before the ships are placed the playerturn is false
		//player.placeShips(); milstolpe 2
		ai.placeShips();
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
			if(gui.gameOverText(winText)) {
				winText = null;
				resetGame();
			}
			else
				System.exit(0);
		}
		else
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
	
	/**
	 *	Empties the objects and recreates them for a new game 
	 */
	private void resetGame() {
		gui.removeAll();
		gui.setVisible(false);
		gui = null;
		player = null;
		ai = null;
		System.gc();	// Calls GC here to make sure it does its job
						// This keeps the program from ever taking up too much memory
		gui = new Gui(this);
		player = new Human(listener);
		ai = new AI(0,listener);
		newGame();
	}
	
	public void coordinates (int x, int y ){
		playerTurn = false;			// Player's turn is over
		System.out.println("Coordinates called");
		playerLastHit = ai.bomb(x, y);
		if (!ai.hasShips()) {
			winPlayer = true;
			gameOver = true;
			gameOver();
		}
		int[] aiAttack = ai.attack(aiLastHit);
		aiLastHit = player.bomb(aiAttack[0], aiAttack[1]);
		if (!player.hasShips()) {
			winPlayer = false;
			gameOver = true;
			gameOver();
		}
		
		playerTurn = true;			// Player's turn again
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
