package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import game.Fleet;
import game.GameEngine;
import game.Human;
import game.Ship;
import game.ZoneLink;
import game.ZoneListener;
import highscore.Score;

/**
 * This class creates the GUI.
 * 
 * @author Group 1 - DAT055 2014
 * @version 2.0
 */
public class Gui extends JFrame implements ActionListener, Observer {

	private static final long serialVersionUID = -508235750073237690L;
	private JMenuItem newGame, quit, highScore, about, rules, training, easy, normal, hard, insane;
	private JRadioButton carrier, battleship, submarine, cruiser, destroyer;
	private JLabel informatioText;
	private ArrayList<String> letters = new ArrayList<String>();
	private ArrayList<Zone> leftZoneArray = new ArrayList<Zone>();
	private ArrayList<Zone> rightZoneArray = new ArrayList<Zone>();
	private ArrayList<Ship> shipArray = new ArrayList<Ship>();
	private ArrayList<JRadioButton> jRadioButtonArray = new ArrayList<JRadioButton>();
	private ArrayList<String> zoneName = new ArrayList<String>();
	private GameEngine gameEngine;
	private Human human;
	private ButtonGroup buttonGroup;
	private Fleet fleet;
	private String ship;
	private int sizeOnShip = 0;
	private int[] x;
	private int[] y;

	/**
	 * Constructor for creating the Gui.
	 * @param gameEngine
	 * @param human
	 */
	public Gui(GameEngine gameEngine, Human human){
		this.gameEngine = gameEngine;
		this.human = human;
		this.fleet = human.getFleet();
		shipArray = fleet.getShips();
		makeGUIFrame();
	}

	/**
	 *  This method creates the frame for the GUI.
	 */
	private void makeGUIFrame(){
		setTitle("Battleship");
		setLayout(new BorderLayout(10, 10));

		/**
		 *  Add the buttons to the right in the frame, add them in a group of button, and add actionListeners
		 */
		buttonGroup = new ButtonGroup();

		carrier = new JRadioButton("Carrier 5 Squares");
		battleship = new JRadioButton("Battleship 4 Squares");
		submarine = new JRadioButton("Submarine 3 Squares");
		cruiser = new JRadioButton("Cruiser 3 Squares");
		destroyer = new JRadioButton("Destroyer 2 Squares ");

		carrier.addActionListener(this);
		battleship.addActionListener(this);
		submarine.addActionListener(this);
		cruiser.addActionListener(this);
		destroyer.addActionListener(this);

		buttonGroup.add(carrier);
		buttonGroup.add(battleship);
		buttonGroup.add(submarine);
		buttonGroup.add(cruiser);
		buttonGroup.add(destroyer);

		JPanel radioButtonPanel = new JPanel(new GridLayout(0, 1));

		radioButtonPanel.add(carrier);
		radioButtonPanel.add(battleship);
		radioButtonPanel.add(submarine);
		radioButtonPanel.add(cruiser);
		radioButtonPanel.add(destroyer);

		jRadioButtonArray.add(carrier);
		jRadioButtonArray.add(battleship);
		jRadioButtonArray.add(submarine);
		jRadioButtonArray.add(cruiser);
		jRadioButtonArray.add(destroyer);

		add(radioButtonPanel, BorderLayout.EAST);

		/**
		 * Add the color-description to the top of the frame
		 */
		JPanel colorPanel = new JPanel(new GridLayout(1, 0));
		JLabel green = new JLabel("Green - Hit");
		JLabel black = new JLabel("Black - Ship");
		JLabel cerise = new JLabel("Cerise - Sunk");
		JLabel gray = new JLabel("Gray - Miss");
		JLabel blue = new JLabel("Blue - Sea");

		green.setForeground(Color.GREEN);
		black.setForeground(Color.BLACK);
		cerise.setForeground(new Color(222, 49, 99));
		gray.setForeground(Color.GRAY);
		blue.setForeground(Color.BLUE);

		colorPanel.add(green);
		colorPanel.add(black);
		colorPanel.add(cerise);
		colorPanel.add(gray);
		colorPanel.add(blue);

		add(colorPanel, BorderLayout.NORTH);

		/**
		 * Add a empty JLabel to the bottom of the frame, the text of the label can be change 
		 * by calling a method changeInformationText with a String as parameter
		 */
		informatioText = new JLabel("Place your ship on the right side. Bomb on the left side");
		add(informatioText, BorderLayout.SOUTH);

		/**
		 * Add a empty text-label to the left of the frame
		 */
		add(new JLabel("   "), BorderLayout.WEST);

		/**
		 * Add the zones in the middle of the frame. Create two JPanel and add them to a third one and add that one to the frame.
		 */
		JPanel centerFrame = new JPanel(new GridLayout(1, 2, 50, 0));
		JPanel leftGamePanel  = new JPanel(new GridLayout(11, 11, 2, 2));
		JPanel rightGamePanel = new JPanel(new GridLayout(11, 11, 2, 2));
		centerFrame.add(leftGamePanel);
		centerFrame.add(rightGamePanel);
		add(centerFrame, BorderLayout.CENTER);

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

		/**
		 * Add all the zones to the left frame, and then to the right frame.
		 * Add actionListeners to every zones, and setEnable false for the left frames zones.
		 * Add all the left zone in one array, and the right ones to another array.
		 */
		Zone zone = null;
		for(int i = 0; i < 11; i++){
			for(int j = 0; j < 11; j++){
				String name = letters.get(j);
				zone = new Zone(j, i, name + i);
				leftGamePanel.add(zone);
				zone.addActionListener(this);
				zone.setEnabled(false);
				if((i * j) != 0 ){
					leftZoneArray.add(zone);
				}
			}
		}

		zone = null;
		for(int k = 0; k < 11; k++){
			for(int l = 0; l < 11; l++){
				String name = letters.get(l);
				zone = new Zone(l, k, name + k);
				rightGamePanel.add(zone);
				zone.addActionListener(this);
				if((k * l) != 0 ){
					rightZoneArray.add(zone);
				}
			}
		}

		makeMenuBar();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setSize(850, 400);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 *  This method creates the menu bar for the GUI.
	 */
	private void makeMenuBar(){
		/**
		 *  Add the menuBar to the frame
		 */
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		/**
		 *  Add the menu button on the menuBar
		 */
		JMenu menu = new JMenu("Menu");
		menuBar.add(menu);

		/**
		 *  Add the sub-buttons to the menu-button
		 */
		newGame = new JMenuItem("New Game");
		menu.add(newGame);
		newGame.addActionListener(this);

		highScore = new JMenuItem("Highscore");
		menu.add(highScore);
		highScore.addActionListener(this);

		quit = new JMenuItem("Quit");
		menu.add(quit);
		quit.addActionListener(this);

		/**
		 * Add the help button to the menuBar
		 */
		JMenu help = new JMenu("Help");
		menuBar.add(help);

		/**
		 *  Add the sub-buttons to the help-button
		 */
		about = new JMenuItem("About");
		help.add(about);
		about.addActionListener(this);

		rules = new JMenuItem("Rules");
		help.add(rules);
		rules.addActionListener(this);

		/**
		 *  Add the difficulty button to the menuBar
		 */
		JMenu difficulty = new JMenu("Difficulty");
		menuBar.add(difficulty);

		/**
		 *  Add the sub-buttons to the difficulty-button
		 */
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
	}

	/**
	 * Change battlefield so the left one becomes click-able and the right one dont get click-able
	 */
	private void changeBattlefield(){
		for(int i = 0; i < 100; i++){
			leftZoneArray.get(i).setEnabled(true);
			rightZoneArray.get(i).setEnabled(false);
		}
	}

	/**
	 * Show an input dialog where the user can select a difficulty.
	 * @return difficulty as an int, difficulty can be 0 - 4
	 */
	public int selectDifficultyWIndow(){
		Object[] options = {"Training", "Easy", "Normal", "Hard", "Insane"};
		int difficulty = 0;
		difficulty = JOptionPane.showOptionDialog(null, "Select which difficulty you want:", "Difficulty",
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, options, options[0]);
		return difficulty;
	}

	/**
	 * When this method gets called, set the string (parameter) to the bottom of the frame.
	 */
	public void changeInformationText(String string){
		informatioText.setText(string);
	}

	/**
	 * This method gets called when a user click on the menu-bar on the button "Rules". 
	 * Then the wiki-page about the game Battleship will open.
	 * @throws URISyntaxException
	 */
	private void openURIForRules(){
		try {
			final URI uri = new URI("http://battleship.wikia.com/wiki/Battleship_(game)");
			Desktop.getDesktop().browse(uri);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
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
		return (JOptionPane.showConfirmDialog(null, "You have " + winText + "!\n Would you like to play again?", "GAME OVER",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);
	}

	/**
	 * This method asks for the users name, and return it as a string.
	 * @return the players input, his name, as a string
	 */
	public String enterName(){
		String name = JOptionPane.showInputDialog("Enter your name, sir");
		return name;
	}

	/**
	 * This method create a frame with the high score
	 * @param list
	 */
	public void showHighscore(ArrayList<Score> list){
		JFrame frame = new JFrame("Highscore");
		frame.setLayout(new GridLayout(11, 2, 50, 0));

		int size = 30;
		JLabel nameLabel = new JLabel("Name:");
		JLabel pointLabel = new JLabel("Points:");

		nameLabel.setFont(new Font("Serif", Font.PLAIN, size));
		pointLabel.setFont(new Font("Serif", Font.PLAIN, size));
		pointLabel.setHorizontalAlignment(JLabel.RIGHT);

		frame.add(nameLabel);
		frame.add(pointLabel);	

		/**
		 * Iterate through the list, and add it to the frame, with a text-size that get smaller for every loop
		 */
		for(Score score : list){
			JLabel name = new JLabel(score.getName());
			name.setFont(new Font("Serif", Font.PLAIN, size));

			JLabel points = new JLabel(score.getPoints() + "");
			points.setHorizontalAlignment(JLabel.RIGHT);
			points.setFont(new Font("Serif", Font.PLAIN, size));

			frame.add(name);
			frame.add(points);

			size = size - 2;
		}

		frame.pack();
		frame.setSize(500, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/**
	 * This method listen to the users actions.
	 */
	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == newGame){
			gameEngine.gameOver();
		}else if(e.getSource() == quit){
			System.exit(0);
		}else if(e.getSource() == highScore){
			gameEngine.printHighScore();
		}else if(e.getSource() == about){
			JOptionPane.showMessageDialog(null, "Hej! \nVi är 7 coola kids från DAT055 och vi gör ett spel " +
					"som utspelar sig år 1944 i Stilla Havet.", "About", JOptionPane.INFORMATION_MESSAGE);
		}else if(e.getSource() == rules){
			openURIForRules();
		}else if(e.getSource() == training){
			gameEngine.setDifficulty(0);
		}else if(e.getSource() == easy){
			gameEngine.setDifficulty(1);
		}else if(e.getSource() == normal){
			gameEngine.setDifficulty(2);
		}else if(e.getSource() == hard){
			gameEngine.setDifficulty(3);
		}else if(e.getSource() == insane){
			gameEngine.setDifficulty(4);
		}else if(e.getSource() instanceof Zone){
			/**
			 * This is when the user has click on a zone
			 */
			Zone tempZone = (Zone) e.getSource();
			/**
			 * This if-statement is when it is the players turn
			 */
			if( GameEngine.isPlayerTurn() == true){
				tempZone.setEnabled(false);
				gameEngine.coordinates(tempZone.x - 1, tempZone.y - 1);
			}else if( sizeOnShip != 0){
				/**
				 * When the user choose to select his battleships, this if-statement will get true.
				 */
				tempZone.changeColor(Color.BLACK);
				tempZone.setEnabled(false);
				zoneName.add(tempZone.name);
				sizeOnShip = sizeOnShip - 1;
				x[sizeOnShip] = tempZone.x - 1;
				y[sizeOnShip] = tempZone.y - 1;

				/**
				 * sizeOnShip is 0 when the user has selected all his spot for the battlefield,
				 * then call human.placeShip with the selected zones. If all ships are done, start the game.
				 */
				if(sizeOnShip == 0){
					buttonGroup.clearSelection();
					human.placeShip(ship, x, y);

					for(int i = 0; i < shipArray.size(); i++){
						Ship ship = shipArray.get(i);
						if(ship.isPlaced() == false){
							jRadioButtonArray.get(i).setEnabled(true);
						}
					}

					if(fleet.isPlaced()){
						changeBattlefield();
						gameEngine.setPlayerTurn();
					}

					sizeOnShip = 0;
					x = null;
					y = null;
				}
			}
			/**
			 * Here are the if-statements when the user click on battleship button to the right.
			 * This methods disable all buttons, and keep the pressed one selected.
			 * Save the selected ships size, and name, and initialize the int-arrays x and y to the size of the ship.
			 */
		}else if(e.getSource() == carrier){
			ship = "carrier";
			sizeOnShip = 5;
			x = new int[sizeOnShip];
			y = new int[sizeOnShip];

			carrier.setEnabled(false);
			battleship.setEnabled(false);
			submarine.setEnabled(false);
			cruiser.setEnabled(false);
			destroyer.setEnabled(false);
		}else if(e.getSource() == battleship){
			ship= "battleship";
			sizeOnShip = 4;
			x = new int[sizeOnShip];
			y = new int[sizeOnShip];

			carrier.setEnabled(false);
			battleship.setEnabled(false);
			submarine.setEnabled(false);
			cruiser.setEnabled(false);
			destroyer.setEnabled(false);
		}else if(e.getSource() == submarine){
			ship = "submarine";
			sizeOnShip = 3;
			x = new int[sizeOnShip];
			y = new int[sizeOnShip];

			carrier.setEnabled(false);
			battleship.setEnabled(false);
			submarine.setEnabled(false);
			cruiser.setEnabled(false);
			destroyer.setEnabled(false);
		}else if(e.getSource() == cruiser){
			ship = "cruiser";
			sizeOnShip = 3;
			x = new int[sizeOnShip];
			y = new int[sizeOnShip];

			carrier.setEnabled(false);
			battleship.setEnabled(false);
			submarine.setEnabled(false);
			cruiser.setEnabled(false);
			destroyer.setEnabled(false);
		}else if(e.getSource() == destroyer){
			ship = "destroyer";
			sizeOnShip = 2;
			x = new int[sizeOnShip];
			y = new int[sizeOnShip];

			carrier.setEnabled(false);
			battleship.setEnabled(false);
			submarine.setEnabled(false);
			cruiser.setEnabled(false);
			destroyer.setEnabled(false);
		}
	}

	/**
	 * This method listen to updates from class ZoneListener
	 */
	@Override
	public void update(Observable observable, Object object) {
		/**
		 * This if-statement check if the class that send gui the update was ZoneListener, and the object was a ZoneLink
		 */
		if( observable instanceof ZoneListener && object instanceof ZoneLink){
			Zone zone = null;
			/**
			 * Check if it is the left or right battlefield that should be modified
			 */
			if(ZoneLink.leftOrRight.equals("right")){
				zone = rightZoneArray.get(((ZoneLink.y) * 10) + ZoneLink.x);
			}else if(ZoneLink.leftOrRight.equals("left")){
				zone = leftZoneArray.get(((ZoneLink.y) * 10) + ZoneLink.x);
			}
			/**
			 * Change the color of the zone to gray, green, pink, black or blue, depends on the input
			 */
			try{
				if(ZoneLink.state.equals("miss")){
					zone.changeColor(Color.GRAY);
				}else if(ZoneLink.state.equals("hit")){
					zone.changeColor(Color.GREEN);
				}else if(ZoneLink.state.equals("sunk")){
					zone.changeColor(new Color(222, 49, 99));
				}else if(ZoneLink.state.equals("ship")){
					zone.changeColor(Color.BLACK);
				}else if(game.ZoneLink.state.equals("sea")){
					zone.changeColor(Color.BLUE);
					zone.setEnabled(true);
				}
			}catch (NullPointerException e){
				e.printStackTrace();
			}
		}	
	}
}