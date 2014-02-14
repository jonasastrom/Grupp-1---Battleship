package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 * This class creates the GUI.
 * @author Grupp 1
 *
 */
public class Gui extends JFrame implements ActionListener {

	private JMenuItem newGame, quit, about, rules, training, easy, normal, hard, insane;
	private ArrayList<String> letters = new ArrayList<String>();
	private ArrayList<Zone> zoneArray = new ArrayList<Zone>();

	/**
	 *  Constructor
	 */
	public Gui(){
		makeGUIFrame();
	}

	/**
	 *  This method creates the frame for the GUI.
	 */
	private void makeGUIFrame(){
		setTitle("BattleShip");

		setLayout(new BorderLayout(10, 10));
		add(new JLabel("höger"), BorderLayout.EAST);
		add(new JLabel("vänster"), BorderLayout.WEST);
		add(new JLabel("upp"), BorderLayout.NORTH);
		add(new JLabel("ner"), BorderLayout.SOUTH);

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
				zoneArray.add(zone);
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
	}

	/**
	 * 
	 */
	public void updateZone(int x, int y, int color){
		int zoneNumber = ((y - 1) * 10) + x;
		System.out.println("nummer:" + zoneNumber);
		Zone zone = zoneArray.get(zoneNumber - 1);
		
	}


	/**
	 *  This method listen to the players actions.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == newGame){
			System.out.println("newGame");
		}else if(e.getSource() == quit){
			System.exit(0);
		}else if(e.getSource() == about){
			System.out.println("about");
		}else if(e.getSource() == rules){
			System.out.println("rules");
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
			Zone temp = (Zone) e.getSource();
			System.out.println("x:" + temp.x + " y:" + temp.y + " name:" + temp.name);
			int i = ((temp.y - 1) * 10 ) + temp.x;
			System.out.println("nummer:" + i);
			temp.changeColor(0);
			temp.setEnabled(false);
		}
	}
}