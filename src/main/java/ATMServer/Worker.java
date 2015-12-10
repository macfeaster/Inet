package ATMServer;

import common.Instruction;
import common.InstructionParser;
import common.Logger;
import common.Writer;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.function.Function;

public class Worker implements Runnable
{
	Logger logger = Logger.getInstance();
	Map<Byte, Function<Instruction, Instruction>> functions;
	Socket socket;
	String clientData;

	public Worker(Socket socket, Map<Byte, Function<Instruction, Instruction>> functions, String clientData)
	{
		this.functions = functions;
		this.socket = socket;
		this.clientData = clientData;
	}

	@Override
	public void run()
	{
		try
		{
			byte[] command = new byte[1];

			logger.debug("Client connection opened, waiting for data");

			while (true)
			{
				logger.debug("Waiting for data stream from client...");
				socket.getInputStream().read(command);
				logger.debug("Received incoming data, parsing instruction.");
				Instruction instruction = InstructionParser.parseInstruction(command[0], socket.getInputStream());
				logger.debug("Parsed instruction: " + instruction);

				if (functions.containsKey(instruction.getCommand()))
				{
					Function<Instruction, Instruction> func = functions.get(instruction.getCommand());

					Instruction response = func.apply(instruction);
					logger.debug("Sending response " + response);
				} else
				{
					switch (instruction.getCommand())
					{
						case 0:
							logger.debug("Boot data requested");
							socket.getOutputStream().write(clientData.getBytes());
							socket.getOutputStream().write((int) '\n');
							logger.debug("Finished pushing JSON data to client");
							break;

						default:
							logger.error("Unknown function requested: " + instruction.getCode());
							Writer.write(new Instruction((byte) 80), socket.getOutputStream());
					}
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}