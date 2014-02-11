package Game;
import javax.swing.JOptionPane;

import GUI.Gui;

public class GameEngine {
	Gui gui;
	Player player;
	AI ai;
	Boolean winPlayer;
	
	public static void main(String[] args) {
		// Creates a new object of GUI (for creating a frame)
		Gui gui = new GUI.Gui();
		Player player = new Player();
		AI ai = new AI();
		

		//TODO:
		
		// GameEngine kontrollerar players hasShip och anropar gameOver n�r en
		// av spelarna inte har n�gra skepp kvar

		// Keeps track of turns of the players

	}

	// keeps the game running
	public void run() {
		boolean playerTurn = false;
		
		
		
		
	}

	// starts the game
	public void newGame() {
	}

	// ends the game and pops a prompt to the player
	private void gameOver() {
		
		String winText;
		if(winPlayer)
			winText = "You have won!\n";
		else
			winText = "You have lost!\n";
		if(JOptionPane.showConfirmDialog(null, winText + "Do you want to play again?", "GAME OVER", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			player = null;
			ai = null;
			gui = null;
			winText = null;
			System.gc();
			newGame();
		}
		else
			System.exit(0);
	}

}
