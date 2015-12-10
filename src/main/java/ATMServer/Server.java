package ATMServer;

import common.Writer;
import ATMServer.ATM.Bank;
import ATMServer.ATM.Functions;
import common.Instruction;
import ATMServer.util.InstructionParser;
import common.Logger;

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
	Map<Byte, Function<Instruction, Instruction>> functions = new HashMap<>();

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
		logger.info("Server now listening on port 2178");

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

			logger.debug("Received incoming data, parsing instruction.");
			Instruction instruction = InstructionParser.parseInstruction(socket.getInputStream());
			logger.debug("Parsed instruction: " + instruction);

			if (functions.containsKey(instruction.getCommand())) {
				Function<Instruction, Instruction> func = functions.get(instruction.getCommand());

				Instruction response = func.apply(instruction);
				logger.debug("Sending response " + response);
			} else {

				switch (instruction.getCommand()) {
					case 0:
						logger.debug("Boot data requested");
						socket.getOutputStream().write(clientData.getBytes());
						logger.debug("Finished pushing JSON data to client");
						break;

					default:
						logger.error("Unknown function requested: " + instruction.getCode());
						Writer.write(new Instruction((byte) 80), socket.getOutputStream());
				}

			}

			socket.close();
		}

		return this;
	}
}
