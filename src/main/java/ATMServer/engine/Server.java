package ATMServer.engine;

import ATMServer.ATM.Bank;
import ATMServer.ATM.Functions;
import ATMServer.util.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Server
{
	private String clientData;
	private Bank bank = new Bank();
	private ServerSocket s;
	private Logger logger = Logger.getInstance();
	Map<Integer, Function<Integer, Integer>> functions = new HashMap<>();

	public static Server createInstance()
	{
		return new Server();
	}

	public Server readData() throws IOException
	{
		Path p = Paths.get("resources/inet.json");

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

	public Server registerHandlers()
	{
		functions = Functions.getFunctions(bank);

		return this;
	}

	public Server handleData() throws IOException
	{
		Socket serverSocket;
		int i = 0;
		int maxConnections = 200;

		// Unless we've reached the max number of simultaneous connections,
		// start a new thread to handle a request
		while (i++ < maxConnections)
		{
			serverSocket = s.accept();

			Handle serverDo = new Handle(serverSocket);

			Thread thread = new Thread(serverDo);
			thread.start();

			logger.info("Thread " + thread.getId() + " assigned to incoming request.");
		}

		return this;
	}
}
