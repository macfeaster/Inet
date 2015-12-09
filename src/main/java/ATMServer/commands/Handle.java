package ATMServer.commands;

import ATMServer.util.Logger;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Arrays;

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
	public void run() {
		try {
			// Set up a Stream to provide a response to a client
			PrintStream out = new PrintStream(server.getOutputStream());
			int res;

			// Receive 1 command byte
			byte[] command = new byte[1];
			res = server.getInputStream().read(command);

			if (res != 1) {
				logger.error("Malformed bytes received: " + res + " bytes - " + Arrays.toString(command));
				return;
			}

			// Receive 7 data bytes
			byte[] data = new byte[7];
			res = server.getInputStream().read(data);

			if (res != 1) {
				logger.error("Malformed bytes received: " + res + " bytes - " + Arrays.toString(data));
				return;
			}

			// Receive 7 data bytes
			byte[] data = new byte[1];
			res = server.getInputStream().read(data);

			if (res != 1) {
				logger.error("Malformed bytes received: " + res + " bytes - " + Arrays.toString(data));
				return;
			}

			byte[] data = new byte[7];

			int res = server.getInputStream().read(command);

			// If the number of bytes received was not exactly ten,
			// we have received malformed input
			if (res != 0)
			{
				logger.error("Malformed bytes received: " + res + " bytes - " + Arrays.toString(command));
				return;
			}

			out.println("Received!");
		}
		catch (IOException e)
		{
			// Log server errors
			logger.log("IOException in Handle.java: " + e.getMessage());
		}
	}
}
