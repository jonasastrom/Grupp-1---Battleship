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

	private ArrayList<Score> highScore;
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
				highScore = (ArrayList<Score>)input.readObject();
				input.close();
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
		} else {
			highScore = new ArrayList<Score>();
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
			output.writeObject(highScore);
			output.close();
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * Get the high score list.
	 * @return The list
	 */
	public ArrayList<Score> getHighScore()
	{
		return highScore;
	}

	/**
	 * Update the high score list with a new
	 * score if it is good enough.
	 * @param score The score
	 */
	public void update(Score score)
	{
		if (highScore.isEmpty()) {
			highScore.add(score);
			saveHighScores();
			return;
		}

		int lastScore = highScore.size()-1;
		boolean listIsFull = TOP_LIST_SIZE == lastScore+1;

		int newPoints = score.getPoints();
		int lowestPoints = 0;

		if (listIsFull)
			lowestPoints = highScore.get(lastScore).getPoints();

		if (!listIsFull || newPoints > lowestPoints) {
			if (listIsFull) highScore.remove(lastScore);

			for (int i = 0; i < highScore.size(); i++) {
				if (newPoints > highScore.get(i).getPoints()) {
					highScore.add(i,score);
					saveHighScores();
					return;
				}
			}
			highScore.add(score);
			saveHighScores();
			return;
		}
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
		for (int i = 0; i < highScore.size(); i++) {
			rank = i + 1;
			if (rank < 10)
				System.out.println("rank=0"+rank+" "+highScore.get(i));
			else
				System.out.println("rank="+rank+" "+highScore.get(i));
		}
	}
}
