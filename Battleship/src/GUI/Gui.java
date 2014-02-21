package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Game.GameEngine;

/**
 * This class creates the GUI.
 * @author Grupp 1
 *
 */
public class Gui extends JFrame implements ActionListener, Observer {

	private JMenuItem newGame, quit, about, rules, training, easy, normal, hard, insane, playerWins, playerLose, ATOMBOMB;
	//	private JRadioButton carrier, battleship, submarine, cruiser, destroyer;
	private JLabel informatioText;
	private ArrayList<String> letters = new ArrayList<String>();
	private ArrayList<Zone> zoneArray = new ArrayList<Zone>();
	private GameEngine gameEngine;

	/**
	 *  Constructor
	 */
	public Gui(GameEngine gameEngine){
		this.gameEngine = gameEngine;
		makeGUIFrame();
	}

	/**
	 *  This method creates the frame for the GUI.
	 */
	private void makeGUIFrame(){
		setTitle("BattleShip");
		setLayout(new BorderLayout(10, 10));

		/**
		 *  Add the buttons to the left in the frame
		 */
		//		ButtonGroup buttonGroup = new ButtonGroup();
		//
		//		carrier = new JRadioButton("Carrier 5 Rutor");
		//		battleship = new JRadioButton("Battleship 4 Rutor");
		//		submarine = new JRadioButton("Submarine 3 Rutor");
		//		cruiser = new JRadioButton("Cruiser 3 Rutor");
		//		destroyer = new JRadioButton("Destroyer 2 Rutor ");
		//
		//		buttonGroup.add(carrier);
		//		buttonGroup.add(battleship);
		//		buttonGroup.add(submarine);
		//		buttonGroup.add(cruiser);
		//		buttonGroup.add(destroyer);
		//		
		//		JPanel radioButtonPanel = new JPanel(new GridLayout(0, 1));
		//		radioButtonPanel.add(carrier);
		//		radioButtonPanel.add(battleship);
		//		radioButtonPanel.add(submarine);
		//		radioButtonPanel.add(cruiser);
		//		radioButtonPanel.add(destroyer);
		//
		//		add(radioButtonPanel, BorderLayout.WEST);

		/**
		 * Add the color-description to the top of the frame
		 */
		JPanel colorPanel = new JPanel(new GridLayout(1, 0));
		JLabel green = new JLabel("Green - Hit");
		JLabel black = new JLabel("Black - Ship");
		JLabel cerise = new JLabel("Cerise - Sunk");
		JLabel gray = new JLabel("Gray - Miss");

		green.setForeground(Color.GREEN);
		black.setForeground(Color.BLACK);
		cerise.setForeground(new Color(222, 49, 99));
		gray.setForeground(Color.GRAY);

		colorPanel.add(green);
		colorPanel.add(black);
		colorPanel.add(cerise);
		colorPanel.add(gray);

		add(colorPanel, BorderLayout.NORTH);

		/**
		 * Add something to the bottom of the frame
		 */
		informatioText = new JLabel(" ");
		add(informatioText, BorderLayout.SOUTH);

		/**
		 * Add a text to the right of the frame
		 */
		add(new JLabel("   "), BorderLayout.EAST);

		/**
		 * Add the zones in the middle of the frame
		 */

		//		JPanel centerFrame = new JPanel(new BorderLayout(2, 2));
		JPanel leftGamePanel  = new JPanel(new GridLayout(11, 11, 2, 2));
		//		JPanel rightGamePanel = new JPanel(new GridLayout(11, 11, 2, 2));
		add(leftGamePanel, BorderLayout.CENTER);

		JPanel gamePanel = new JPanel(new GridLayout(11, 11, 2, 2));
		add(gamePanel, BorderLayout.CENTER);


		letters.add(0, " ");
		letters.add(1, "A");
		letters.add(2, "B");
		letters.add(3, "C");
		letters.add(4, "D");
		letters.add(5, "E");
		letters.add(6, "F");
		letters.add(7, "G");
		letters.add(8, "H");
		letters.add(9, "I");
		letters.add(10, "J");

		Zone zone = null;
		for(int i = 0; i < 11; i ++){
			for(int j = 0; j < 11; j++){
				String name = letters.get(j);
				zone = new Zone(j, i, name + i);
				gamePanel.add(zone);
				zone.addActionListener(this);
				if((i * j) != 0 ){
					zoneArray.add(zone);
				}
			}
		}

		makeMenuBar();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setSize(400, 400);
		setVisible(true);
	}

	/**
	 *  This method creates the menu bar for the GUI.
	 */
	private void makeMenuBar(){
		// Add the menuBar to the frame
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// Add the menu button on the menuBar
		JMenu menu = new JMenu("Menu");
		menuBar.add(menu);

		// Add the sub-buttons to the menu-button
		newGame = new JMenuItem("New Game");
		menu.add(newGame);
		newGame.addActionListener(this);

		quit = new JMenuItem("Quit");
		menu.add(quit);
		quit.addActionListener(this);

		// Add the help button to the menuBar
		JMenu help = new JMenu("Help");
		menuBar.add(help);

		// Add the sub-buttons to the help-button
		about = new JMenuItem("About");
		help.add(about);
		about.addActionListener(this);

		rules = new JMenuItem("Rules");
		help.add(rules);
		rules.addActionListener(this);

		// Add the difficulty button to the menuBar
		JMenu difficulty = new JMenu("Difficulty");
		menuBar.add(difficulty);

		// Add the sub-buttons to the difficulty-button
		training = new JMenuItem("Training");
		difficulty.add(training);
		training.addActionListener(this);

		easy = new JMenuItem("Easy");
		difficulty.add(easy);
		easy.addActionListener(this);

		normal = new JMenuItem("Normal");
		difficulty.add(normal);
		normal.addActionListener(this);

		hard = new JMenuItem("Hard");
		difficulty.add(hard);
		hard.addActionListener(this);

		insane = new JMenuItem("Insane");
		difficulty.add(insane);
		insane.addActionListener(this);

		// Add the debug button to the menuBar
		JMenu debug = new JMenu("Debug");
		menuBar.add(debug);

		// Add the sub-buttons to the debug-button
		playerWins = new JMenuItem("Player Wins");
		debug.add(playerWins);
		playerWins.addActionListener(this);

		playerLose = new JMenuItem("Player Lose");
		debug.add(playerLose);
		playerLose.addActionListener(this);
		
		ATOMBOMB = new JMenuItem("Döda alla!");
		debug.add(ATOMBOMB);
		ATOMBOMB.addActionListener(this);
	}

	/**
	 * When this method gets called, set the string to the bottom of the frame
	 */
	public void changeInformatioText(String string){
		informatioText.setText(string);
	}

	/**
	 * This method get coordinates of a specific zone, and call a method to change to a color
	 */
	private void updateZone(int x, int y, String state){
		int zoneNumber = ((y - 1) * 10) + x;
		Zone zone = zoneArray.get(zoneNumber - 1);
		if(state.equals("miss")){
			zone.changeColor(Color.GRAY);
		}else if(state.equals("hit")){
			zone.changeColor(Color.GREEN);
		}else if(state.equals("sunk")){
			zone.changeColor(new Color(222, 49, 99));
		}else if(state.equals("ship")){
			zone.changeColor(Color.BLACK);
		}
	}

	/**
	 * This method gets called when a user click on the menu-bar on the button "Rules". 
	 * Then the wiki-page about the game Battleship will open.
	 * @throws URISyntaxException
	 */
	private void openURIForRules() throws URISyntaxException{
		final URI uri = new URI("http://battleship.wikia.com/wiki/Battleship_(game)");
		try {
			Desktop.getDesktop().browse(uri);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method displays a nice message where the player is asked whether
	 * they want to play again
	 * @param winText A string that contains either "won" or "lost". 
	 * @return	True if yes has been answered, false if not
	 */
	public boolean gameOverText(String winText) {
		return (JOptionPane.showConfirmDialog(	null, "You have " + winText + "!\n Would you like to play again?", "GAME OVER",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);
	}

	/**
	 *  This method listen to the players actions.
	 */
	@Override
	public void actionPerformed(ActionEvent e){ 
		if(e.getSource() == playerWins){
			gameEngine.testGameOver(true);
			System.out.println("debug player wins");
		}else if(e.getSource() == playerLose){
			gameEngine.testGameOver(false);
			System.out.println("debug player lose");
		}else if(e.getSource() == ATOMBOMB){
			gameEngine.testNuke();
		}else if(e.getSource() == newGame){
			gameEngine.gameOver();
		}else if(e.getSource() == quit){
			System.exit(0);
		}else if(e.getSource() == about){
			JOptionPane.showMessageDialog(null, "Hej! \nVi ï¿½r 7 coola kids frï¿½n DAT055 och vi gï¿½r ett \"spel\".", "About", JOptionPane.INFORMATION_MESSAGE);
		}else if(e.getSource() == rules){
			try {
				openURIForRules();
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}		
		}else if(e.getSource() == training){
			System.out.println("training");
		}else if(e.getSource() == easy){
			System.out.println("easy");
		}else if(e.getSource() == normal){
			System.out.println("normal");
		}else if(e.getSource() == hard){
			System.out.println("hard");
		}else if(e.getSource() == insane){
			System.out.println("insane");
		}else if(e.getSource() instanceof Zone){

			Zone tempZone = (Zone) e.getSource();
			System.out.println("x:" + tempZone.x + " y:" + tempZone.y + " name:" + tempZone.name);
			int i = ((tempZone.y - 1) * 10 ) + tempZone.x;
			System.out.println("nummer:" + i);
			tempZone.setEnabled(false);
			gameEngine.coordinates(tempZone.x - 1, tempZone.y - 1);
		}
	}

	/**
	 * This method listen to updates
	 */
	@Override
	public void update(Observable observable, Object object) {
		System.out.println("Katten leker pa staden");
		if( observable instanceof Game.ZoneListener && object instanceof Game.ZoneLink){

			Zone zone = zoneArray.get(((Game.ZoneLink.y) * 10) + Game.ZoneLink.x);
			System.out.println(Game.ZoneLink.state);
			if(Game.ZoneLink.state.equals("miss")){
				zone.changeColor(Color.GRAY);
			}else if(Game.ZoneLink.state.equals("hit")){
				zone.changeColor(Color.GREEN);
			}else if(Game.ZoneLink.state.equals("sunk")){
				zone.changeColor(new Color(222, 49, 99));
			}else if(Game.ZoneLink.state.equals("ship")){
				zone.changeColor(Color.BLACK);
			}
		}	
	}
}