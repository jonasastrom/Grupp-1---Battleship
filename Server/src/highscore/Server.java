package highscore;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * The server is the primary class
 * of this program.
 * 
 * @author Group 1 - DAT055 2014
 * @version 3.0
 */
public class Server
{
	private static final int TOP_LIST_SIZE = 10;
	private static final String FILE_PATH = "highscore.dat";

	private ArrayList<Score> highScores;
	private ServerSocket serverSocket;

	/**
	 * Construct a high score server.
	 * @param serverPort The listening port
	 */
	public Server(int serverPort)
	{
		loadHighScores();
		try { serverSocket = new ServerSocket(serverPort); }
		catch (IOException e) { e.printStackTrace(); }
		new SocketReceiver(this,serverSocket);

		// Debug
		//addScores(); printScores();
	}

	/**
	 * Load the previously saved high scores, create
	 * a new high score file if none is found.
	 */
	@SuppressWarnings("unchecked")
	private void loadHighScores()
	{
		if (new File(FILE_PATH).isFile()) {
			try {
				ObjectInputStream input = new ObjectInputStream(
						new FileInputStream(FILE_PATH));
				highScores = (ArrayList<Score>)input.readObject();
				input.close();
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
		} else {
			highScores = new ArrayList<Score>();
			saveHighScores();
		}
	}

	/**
	 * Save the high scores to a binary file.
	 */
	private void saveHighScores()
	{
		try {
			ObjectOutputStream output = new ObjectOutputStream(
					new FileOutputStream(FILE_PATH));
			output.writeObject(highScores);
			output.close();
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * Get a list with the high scores.
	 * @return The list
	 */
	public ArrayList<Score> getHighScores()
	{
		return highScores;
	}

	/**
	 * Update the high score list with a new
	 * score if it is good enough.
	 * @param score The score
	 * @return true if the score was added,
	 *         false otherwise
	 */
	public boolean update(Score score)
	{
		if (highScores.isEmpty()) {
			highScores.add(score);
			saveHighScores();
			return true;
		}

		int lastScore = highScores.size()-1;
		boolean listIsFull = TOP_LIST_SIZE == lastScore+1;

		int newPoints = score.getPoints();
		int lowestPoints = 0;

		if (listIsFull)
			lowestPoints = highScores.get(lastScore).getPoints();

		if (!listIsFull || newPoints > lowestPoints) {
			if (listIsFull) highScores.remove(lastScore);

			for (int i = 0; i < highScores.size(); i++) {
				if (newPoints > highScores.get(i).getPoints()) {
					highScores.add(i,score);
					saveHighScores();
					return true;
				}
			}
			highScores.add(score);
			saveHighScores();
			return true;
		}
		
		// Return false because the score
		// didn't make the top list
		return false;
	}

	/**
	 * Debug: add a few scores
	 */
	@SuppressWarnings("unused")
	private void addScores()
	{
		update(new Score(1000,"Bob"));
		update(new Score(2000,"Alice"));
		update(new Score(3000,"John"));
	}

	/**
	 * Debug: print all scores
	 */
	@SuppressWarnings("unused")
	private void printScores()
	{
		int rank;
		for (int i = 0; i < highScores.size(); i++) {
			rank = i + 1;
			if (rank < 10)
				System.out.println("rank=0"+rank+" "+highScores.get(i));
			else
				System.out.println("rank="+rank+" "+highScores.get(i));
		}
	}
}
