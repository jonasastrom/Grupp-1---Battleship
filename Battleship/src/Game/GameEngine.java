package Game;
import javax.swing.JOptionPane;

import GUI.Gui;

@SuppressWarnings("unused")		// För att gula streck är irriterande
public class GameEngine {
	Gui gui;
	Player player;
	AI ai;
	Boolean winPlayer;
	Boolean gameOver;
	
	public static void main(String[] args) {
		// Creates a new object of GUI (for creating a frame)
		Gui gui = new GUI.Gui();
		Player player = new Player();
		AI ai = new AI(0);	//0 för att milstolpe 1 inte har något val av svårighet
		

		//TODO:
		
		// GameEngine kontrollerar players hasShip och anropar gameOver n�r en
		// av spelarna inte har n�gra skepp kvar

		// Keeps track of turns of the players

	}

	// keeps the game running
	public void run() {
		boolean playerTurn = true;
		
		newGame();

		while(!gameOver){
			if(playerTurn){
				//player.attack();
				playerTurn = false;
			}
			else{
				//ai.attack();
				playerTurn = true;
			}
		}

	}

	/**
	 *  Sets a new game up by saying that the game is not over
	 *  and making the players place their ships.
	 */
	public void newGame() {
		gameOver = false;
		player.placeShips(5);
		ai.placeShips(5);
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
		gui = null;
		System.gc();
		run();
	}
}
