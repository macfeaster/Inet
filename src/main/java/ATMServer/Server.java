package ATMServer;

import ATMServer.ATM.Bank;
import ATMServer.ATM.Functions;
import ATMServer.data.Instruction;
import ATMServer.util.InstructionParser;
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
	Map<Byte, Function<Long, Long>> functions = new HashMap<>();

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
		Socket socket;
		int i = 0;
		int maxConnections = 200;

		// Unless we've reached the max number of simultaneous connections,
		// start a new thread to handle a request
		while (i++ < maxConnections)
		{
			// Accept new data on the socket
			socket = s.accept();

			logger.info("Received incoming data, parsing instruction.");
			Instruction instruction = InstructionParser.parseInstruction(socket.getInputStream());


		}

		return this;
	}
}
