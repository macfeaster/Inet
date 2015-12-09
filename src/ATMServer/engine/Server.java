package ATMServer.engine;

import ATMServer.commands.Handle;
import ATMServer.util.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Server
{
	private String clientData;
	private ServerSocket s;
	private Logger logger = Logger.getInstance();

	public static Server createInstance()
	{
		return new Server();
	}

	public Server readData() throws IOException
	{
		Path p = Paths.get("inet.json");

		clientData = Files
				.readAllLines(p)
				.stream()
				.reduce((t, u) -> t + u)
				.get();

		return this;
	}

	public Server openConnection() throws IOException
	{
		s = new ServerSocket(2178);

		return this;
	}

	public Server registerHandlers() throws IOException
	{
		Socket server;
		int i = 0;
		int maxConnections = 200;

		// Unless we've reached the max number of simultaneous connections,
		// start a new thread to handle a request
		while (i++ < maxConnections)
		{
			server = s.accept();

			Handle serverDo = new Handle(server);

			Thread thread = new Thread(serverDo);
			thread.start();

			logger.info("Thread " + thread.getId() + " assigned to incoming request.");
		}

		return this;
	}
}
