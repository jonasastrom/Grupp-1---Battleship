package Game;
import GUI.Gui;

public class GameEngine {
	
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
	}

	// starts the game
	public void newGame() {
	}

	// ends the game and pops a prompt to the player
	private void gameOver() {
		
	}

}
