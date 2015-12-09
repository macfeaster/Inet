package ATMServer.commands;

import ATMServer.util.Logger;
import sun.rmi.runtime.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by mauritz on 12/9/15.
 */
public class Handle implements Runnable
{
	// registerHandler(byte command, Callable x, long input, byte code, byte identifier)
	//{
	//	return x(input, code, identifier);
	//}

	private Socket server;
	private Logger logger = Logger.getInstance();

	public Handle(Socket server)
	{
		this.server = server;
	}

	@Override
	public void run()
	{
		try
		{
			// Set up variables to receive incoming data
			String line;
			BufferedReader in = new BufferedReader(
					new InputStreamReader(server.getInputStream()));

			// Set up a Stream to provide a response to a client
			PrintStream out = new PrintStream(server.getOutputStream());

			// As long as request data is received, assign a new Worker
			// to it, and send back a response
			while ((line = in.readLine()) != null)
			{
				logger.info(line);

				out.println("Received!");
			}
		}
		catch (IOException e)
		{
			// Log server errors
			logger.log("IOException in ServerDo.java: " + e.getMessage());
		}
	}
}
