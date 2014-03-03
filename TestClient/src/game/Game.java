package game;

// Custom imports
import highscore.Score;
import highscore.SocketConnector;

// GUI imports
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

// Miscellaneous imports
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * This is the primary class of the TestClient.
 * It provides a simple GUI and methods to
 * test the SocketConnector with the remote
 * high score server.
 * 
 * @author Group 1 - DAT055 2014
 * @version 3.0
 */
public class Game
{
	// Used by SocketConnector
	public enum ScoreStatus {ADDED, LOW, ERROR}

	// Used to draw the high score list
	private static final int TOP_LIST_SIZE = 10;

	// Used in the GUI
	private static final String PROGRAM_NAME = "TestClient";

	// Server connection
	private SocketConnector socketConnector;

	// GUI components
	private JTextField scoreTextfield;
	private JTextField nameTextfield;
	private JPanel listPanelItems;
	private JLabel statusLabel;

	/**
	 * Construct a game.
	 */
	public Game()
	{
		socketConnector = new SocketConnector();
		createGUI();

		// Print the server address
		String serverAddress = socketConnector.getServerAddress();
		if (serverAddress == null) printStatus("Hostname resolution failed!");
		else printStatus("The server is currently located at "+serverAddress);
	}

	/**
	 * Start the program by creating a new game.
	 * @param args Not used
	 */
	public static void main(String[] args)
	{
		new Game();
	}

	/**
	 * Print a status message to the GUI.
	 * @param info The text to be displayed
	 */
	private void printStatus(String info)
	{
		statusLabel.setText(info);
	}

	/**
	 * Show a message dialog with the provided
	 * information string.
	 * @param info The text to be displayed
	 */
	private void showMessage(String info)
	{
		JOptionPane.showMessageDialog(
				null, info, PROGRAM_NAME, JOptionPane.PLAIN_MESSAGE);
	}

	/**
	 * Show an error message dialog with the
	 * provided information string.
	 * @param info The text to be displayed
	 */
	private void showError(String info)
	{
		JOptionPane.showMessageDialog(
				null, info, "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Submit a score with information from the
	 * input fields (called by the submit button).
	 */
	private void submit()
	{
		String name = nameTextfield.getText();
		int nameLength = name.length();
		if (nameLength < 1 || nameLength > 15) {
			showError("The name must be between 1 and 15 characters.");
			nameTextfield.setText("");
			nameTextfield.requestFocusInWindow();
			return;
		}

		int points = 0;
		try {
			points = Integer.parseInt(scoreTextfield.getText());
		} catch (Exception e) {
			showError("The score must only contain numbers.");
			scoreTextfield.setText("");
			scoreTextfield.requestFocusInWindow();
			return;
		}
		if (points < 0 || points > 1000000000) {
			showError("The score must be a number between 0 and 1 000 000 000");
			scoreTextfield.setText("");
			scoreTextfield.requestFocusInWindow();
			return;
		}

		ScoreStatus scoreStatus = socketConnector.sendHighScore(
				new Score(points,name));

		if (scoreStatus == ScoreStatus.ERROR) {
			printStatus("Unable to contact the server!");
			showError("Unable to contact the server!");
			return;
		} else if (scoreStatus == ScoreStatus.ADDED) {
			showMessage("The score was added to the top list!");
		} else if (scoreStatus == ScoreStatus.LOW) {
			showMessage("The score was too low to make the top list.");
		}

		nameTextfield.setText("");
		scoreTextfield.setText("");
		printStatus("Successfully submitted score to the server.");
	}

	/**
	 * Update the list with high scores in
	 * the GUI (called by the update button).
	 */
	private void update()
	{
		ArrayList<Score> list = socketConnector.loadHighScores();

		if (list == null) {
			printStatus("Unable to contact the server!");
			showError("Unable to contact the server!");

		} else {
			listPanelItems.setVisible(false);
			listPanelItems.removeAll();

			int scoreCount = 0;

			for (int i = 0; i < list.size(); i++) {

				int rank = i + 1;
				String rankFormatted;
				if (rank < 10) rankFormatted = "    "+Integer.toString(rank);
				else rankFormatted = "   "+Integer.toString(rank);

				Score score = list.get(i);
				String name = score.getName();
				int points = score.getPoints();

				String pointsFormatted = NumberFormat.getNumberInstance(
						Locale.FRANCE).format(points);

				listPanelItems.add(new JLabel(rankFormatted,JLabel.LEFT));
				listPanelItems.add(new JLabel(name,JLabel.CENTER));
				listPanelItems.add(new JLabel(pointsFormatted,JLabel.RIGHT));

				scoreCount++;
			}

			while (scoreCount != TOP_LIST_SIZE) {

				int rank = scoreCount + 1;
				String rankFormatted;
				if (rank < 10) rankFormatted = "    "+Integer.toString(rank);
				else rankFormatted = "   "+Integer.toString(rank);

				listPanelItems.add(new JLabel(rankFormatted,JLabel.LEFT));
				listPanelItems.add(new JLabel(" ",JLabel.CENTER));
				listPanelItems.add(new JLabel(" ",JLabel.RIGHT));

				scoreCount++;
			}

			listPanelItems.setVisible(true);
			printStatus("Successfully retrieved high scores from the server.");
		}
	}

	/**
	 * Create the whole GUI for this program.
	 */
	private void createGUI()
	{
		// Padding
		Border outerPadding = BorderFactory.createEmptyBorder(15,15,15,15);
		Border innerPadding = BorderFactory.createEmptyBorder(10,10,10,10);

		// FRAME <start>
		JFrame frame = new JFrame(PROGRAM_NAME);

		//// CONTENT <start>
		JPanel contentPanel = new JPanel(new BorderLayout(15,15));
		contentPanel.setBorder(outerPadding);
		frame.setContentPane(contentPanel);

		//////// SUBMIT BOX <start>
		JPanel submitPanelBorder = new JPanel(new BorderLayout());
		submitPanelBorder.setBorder(new TitledBorder("Submit Score"));

		JPanel submitPanelPadding = new JPanel(new BorderLayout());
		submitPanelPadding.setBorder(innerPadding);
		submitPanelBorder.add(submitPanelPadding);

		JPanel submitPanelHolder = new JPanel(new BorderLayout(10,10));
		submitPanelPadding.add(submitPanelHolder,BorderLayout.LINE_START);

		//////////// NAME INPUT
		JPanel namePanel = new JPanel(new BorderLayout(5,5));
		JLabel nameLabel = new JLabel("Name");
		nameTextfield = new JTextField(10);
		namePanel.add(nameLabel,BorderLayout.LINE_START);
		namePanel.add(nameTextfield,BorderLayout.CENTER);

		//////////// SCORE INPUT
		JPanel scorePanel = new JPanel(new BorderLayout(5,5));
		JLabel scoreLabel = new JLabel("Score");
		scoreTextfield = new JTextField(7);
		scorePanel.add(scoreLabel,BorderLayout.LINE_START);
		scorePanel.add(scoreTextfield,BorderLayout.CENTER);

		//////////// SUBMIT BUTTON
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { submit(); }});

		//////// SUBMIT BOX <end>
		submitPanelHolder.add(namePanel,BorderLayout.LINE_START);
		submitPanelHolder.add(scorePanel,BorderLayout.CENTER);
		submitPanelHolder.add(submitButton,BorderLayout.LINE_END);

		//////// LIST BOX <start>
		JPanel listPanelBorder = new JPanel(new BorderLayout());
		listPanelBorder.setBorder(new TitledBorder("Global High Scores"));

		JPanel listPanelPadding = new JPanel(new BorderLayout(0,15));
		listPanelPadding.setBorder(innerPadding);
		listPanelBorder.add(listPanelPadding);

		JPanel listPanelHolder1 = new JPanel(new BorderLayout());
		JPanel listPanelHolder2 = new JPanel(new BorderLayout());
		listPanelPadding.add(listPanelHolder1,BorderLayout.PAGE_START);
		listPanelPadding.add(listPanelHolder2,BorderLayout.PAGE_END);

		//////////// LIST CONTENT <start>
		JPanel listPanelContent = new JPanel(new BorderLayout(10,10));

		//////////////// LIST LABELS
		JPanel listPanelLabels = new JPanel(new BorderLayout());
		listPanelLabels.add(new JLabel("RANK",JLabel.LEFT),BorderLayout.LINE_START);
		listPanelLabels.add(new JLabel("NAME",JLabel.CENTER),BorderLayout.CENTER);
		listPanelLabels.add(new JLabel("SCORE",JLabel.RIGHT),BorderLayout.LINE_END);

		//////////////// LIST ITEMS
		listPanelItems = new JPanel(new GridLayout(TOP_LIST_SIZE,3,10,0));

		for (int i = 0; i < TOP_LIST_SIZE; i++) {
			int rank = i + 1;
			String rankFormatted;
			if (rank < 10) rankFormatted = "    "+Integer.toString(rank);
			else rankFormatted = "   "+Integer.toString(rank);

			listPanelItems.add(new JLabel(rankFormatted,JLabel.LEFT));
			listPanelItems.add(new JLabel(" ",JLabel.CENTER));
			listPanelItems.add(new JLabel(" ",JLabel.RIGHT));
		}

		//////////// LIST CONTENT <end>
		listPanelContent.add(listPanelLabels,BorderLayout.PAGE_START);
		listPanelContent.add(listPanelItems,BorderLayout.CENTER);

		//////////// UPDATE BUTTON
		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){ update(); }});

		//////// LIST BOX <end>
		listPanelHolder1.add(listPanelContent,BorderLayout.CENTER);
		listPanelHolder2.add(updateButton,BorderLayout.LINE_START);

		//////// STATUS BOX
		JPanel statusPanelBorder = new JPanel(new BorderLayout());
		statusPanelBorder.setBorder(new TitledBorder("Connection Status"));

		JPanel statusPanelPadding = new JPanel(new BorderLayout());
		statusPanelPadding.setBorder(innerPadding);
		statusPanelBorder.add(statusPanelPadding);

		statusLabel = new JLabel();
		statusPanelPadding.add(statusLabel);

		//// CONTENT <end>
		contentPanel.add(submitPanelBorder,BorderLayout.PAGE_START);
		contentPanel.add(listPanelBorder,BorderLayout.CENTER);
		contentPanel.add(statusPanelBorder,BorderLayout.PAGE_END);

		// FRAME <end>
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
