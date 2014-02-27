package highscore;

import game.TestGame.ScoreStatus;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * This class manages the communication with
 * the server. The communication is
 * carried out via object streams.
 * 
 * @author Group 1 - DAT055 2014
 * @version 3.0
 */
public class SocketConnector
{
	private static final int SERVER_PORT = 6000;
	private static final int CONNECTION_ATTEMPTS = 3;
	private static final String SERVER_NAME = "highscore.vtx.se";

	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private boolean connected = false;

	public SocketConnector() {}

	/**
	 * Connect to the high score server and open
	 * input and output streams. Set connected
	 * to true if the connection succeeded.
	 */
	private void connect()
	{
		InetAddress serverIP = null;

		// Try to get the IP address of the server
		try {
			serverIP = InetAddress.getByName(SERVER_NAME);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return;
		}

		// Try to connect to the server
		try {
			socket = new Socket(serverIP,SERVER_PORT);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		// Try to get input and output streams
		try {
			output = new ObjectOutputStream(socket.getOutputStream());
			input = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		// The connection succeeded
		connected = true;
	}

	/**
	 * Disconnect from the high score server
	 * and set connected to false.
	 */
	private void disconnect()
	{
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		connected = false;
	}

	/**
	 * Load the high score list from the server. 
	 * @return The high score list or
	 *         null if there was an error
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Score> loadHighScores()
	{
		// Try to connect..
		for (int i = 0; i < CONNECTION_ATTEMPTS; i++) {
			connect();
			if (connected) break;
		}

		// Return null if the connection failed
		if (!connected) return null;

		ArrayList<Score> retrieved = null;

		// Try to send the "load" command and
		// retrieve the high score list
		try {
			output.writeObject("load");
			try {
				retrieved = (ArrayList<Score>)input.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				disconnect();
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			disconnect();
			return null;
		}

		// Disconnect from the server
		disconnect();

		// Return the high score list
		return retrieved;
	}

	/**
	 * Send a score to the server.
	 * @param score The score to send
	 * @return ADDED if the score was added to the top list
	 *         LOW if the score was too low for the top list
	 *         ERROR if there there was an error
	 */
	public ScoreStatus sendHighScore(Score score)
	{
		// Try to connect..
		for (int i = 0; i < CONNECTION_ATTEMPTS; i++) {
			connect();
			if (connected) break;
		}

		// Return ERROR if the connection failed
		if (!connected) return ScoreStatus.ERROR;

		boolean added = false;

		// Try to send the score and
		// retrieve the update status
		try {
			output.writeObject(score);
			try {
				added = (boolean)input.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				disconnect();
				return ScoreStatus.ERROR;
			}
		} catch (IOException e) {
			e.printStackTrace();
			disconnect();
			return ScoreStatus.ERROR;
		}

		// Disconnect from the server
		disconnect();

		// Return ADDED because the score
		// was added to the top list
		if (added) return ScoreStatus.ADDED;

		// Return BAD because the score
		// was too low for the top list
		else return ScoreStatus.LOW;
	}
}
