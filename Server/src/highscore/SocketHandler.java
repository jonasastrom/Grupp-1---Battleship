package highscore;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class manages the communication with
 * a single client. The communication is
 * carried out via object streams.
 * 
 * @author Group 1 - DAT055 2014
 * @version 3.0
 */
public class SocketHandler extends Thread
{
	private Server server;
	private Socket clientSocket;
	private ObjectInputStream input;
	private ObjectOutputStream output;

	/**
	 * Construct a socket handler.
	 * @param server The server
	 * @param clientSocket The client socket
	 */
	public SocketHandler(Server server, Socket clientSocket)
	{
		this.server = server;
		this.clientSocket = clientSocket;
		start();
	}

	public void run()
	{
		try {
			// The order of input and output must
			// match the client's output and input
			input = new ObjectInputStream(clientSocket.getInputStream());
			output = new ObjectOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) { e.printStackTrace(); }

		handleRequests();
	}

	/**
	 * Handle requests from the client.
	 */
	private void handleRequests()
	{
		Object request = null;

		while (true) {
			try {
				request = input.readObject();
			} catch (Exception e1) {
				try {
					input.close();
					clientSocket.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				return;
			}

			// The client sends a score object if it
			// wants to add the score to the list
			if (request instanceof Score) {
				boolean added = server.update((Score)request);
				try {
					output.writeObject(added);
				} catch (IOException e) { e.printStackTrace(); }

			} else if (request instanceof String) {

				// The client sends a "load" command if
				// it wants to get the high score list
				if (request.equals("load")) {
					try {
						output.writeObject(server.getHighScores());
					} catch (IOException e) { e.printStackTrace(); }
				}
			}
		}
	}
}
