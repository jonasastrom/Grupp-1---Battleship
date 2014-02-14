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
		boolean gameOver = false;
		
		while(!gameOver){
			if(playerTurn){
				player.shoot();
				playerTurn = false;
			}
			else{
				//ai.shoot();
				playerTurn = true;
			}
		}

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
