package Game;
import javax.swing.JOptionPane;

import GUI.Gui;

@SuppressWarnings("unused")		// För att gula streck är irriterande
public class GameEngine {
	private Gui gui;
	private Player player;
	private AI ai;
	private Boolean winPlayer;
	private Boolean gameOver;
	
	public GameEngine(){
		// Creates a new object of GUI (for creating a frame)
		gui = new GUI.Gui();
		player = new Player();
		ai = new AI(0);	//0 för att milstolpe 1 inte har något val av svårighet
	}
	
	/**
	 * This is a main method.
	 * It methods mains.
	 * @param args Gör absolut ingenting i detta program
	 */
	public static void main(String[] args) {
		GameEngine gamE = new GameEngine();
		
		gamE.run();
	}
	
	public enum Status{
		MISS,HIT,SUNK,SHIP
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
				//ai.attack();
				
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
		player.placeShips();
		ai.placeShips();
	}

	/**
	 *  Ends the game, pops a prompt to the player, and cleans variables
	 *  between games. This also starts a new game if the player has prompted it
	 *  whether through the prompt or from being called before the game is over.
	 */
	private void gameOver() {
		
		String winText;
		if(gameOver){	
			if(winPlayer)
				winText = "You have won!\n";
			else
				winText = "You have lost!\n";
			if(JOptionPane.showConfirmDialog(null, winText + "Do you want to play again?", "GAME OVER", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				winText = null;
				resetGame();
			}
			else
				System.exit(0);
		}
		resetGame();	// Om Spelet inte är vunnet/förlorat, men New Game har valts som alternativ i menyraden skall spelet baraåterställas
	}
	private void resetGame() {
		player = null;
		ai = null;
		gui = null;		// Kallar GC här för att den ska göra sitt jobb lite tidigare
		System.gc();	// Så kan programmet omöjligtvis ta upp för mycket minne
		gui = new GUI.Gui();
		player = new Player();
		ai = new AI(0);
		run();
	}
}
