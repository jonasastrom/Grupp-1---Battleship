import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class handles the reception of client
 * connection attempts. For each new connection
 * a new thread is created to handle the client. 
 * 
 * @author Group 1 - DAT055 2014
 * @version 3.0
 */
public class SocketReceiver extends Thread
{
	private Server server;
	private ServerSocket serverSocket; 

	/**
	 * Construct a socket receiver.
	 * @param server The server
	 * @param serverSocket The server socket
	 */
	public SocketReceiver(Server server, ServerSocket serverSocket)
	{
		this.server = server;
		this.serverSocket = serverSocket;
		start();
	}

	public void run()
	{
		while (true) {
			try {
				Socket clientSocket = serverSocket.accept();
				new SocketHandler(server,clientSocket);
			} catch (IOException e) { e.printStackTrace(); }
		}
	}
}
