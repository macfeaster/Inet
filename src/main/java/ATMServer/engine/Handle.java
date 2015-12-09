package ATMServer.engine;

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

	public void run() {
		try {
			// Set up a Stream to provide a response to a client
			PrintStream out = new PrintStream(server.getOutputStream());
			int res;

			// Receive 1 command byte
			// If the number of bytes received was not exactly one,
			// we have received malformed input
			byte[] command = new byte[1];
			res = server.getInputStream().read(command);
			if (res != 1) {
				logger.error("Malformed bytes received: " + res + " bytes - " + Arrays.toString(command));
				return;
			}

			// Receive 7 data bytes
			byte[] data = new byte[7];
			res = server.getInputStream().read(data);
			if (res != 7) {
				logger.error("Malformed bytes received: " + res + " bytes - " + Arrays.toString(data));
				return;
			}

			// Receive 1 code byte
			byte[] code = new byte[1];
			res = server.getInputStream().read(code);
			if (res != 1) {
				logger.error("Malformed bytes received: " + res + " bytes - " + Arrays.toString(code));
				return;
			}

			// Receive 1 identifier byte
			byte[] identifier = new byte[1];
			res = server.getInputStream().read(identifier);
			if (res != 1) {
				logger.error("Malformed bytes received: " + res + " bytes - " + Arrays.toString(identifier));
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
