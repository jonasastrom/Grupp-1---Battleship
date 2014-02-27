package highscore;

import javax.swing.JOptionPane;

/**
 * This class initiates the server
 * and provides a simple GUI.
 * 
 * @author Group 1 - DAT055 2014
 * @version 3.0
 */
public class Main
{
	private static final int SERVER_PORT = 6000;
	
	public static void main(String[] args)
	{
		new Server(SERVER_PORT);

		int selectedOption = 1;

		Object[] options = {"Stop Server"};
		selectedOption = JOptionPane.showOptionDialog(
				null,
				"\nThe High Score Server is running\n"+
				"Listening on port "+SERVER_PORT+"\n\n",
				"High Score Server",
				JOptionPane.PLAIN_MESSAGE,
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				options[0]);

		if (selectedOption < 1) System.exit(0);
	}
}
