package ATMServer.util;

import ATMServer.data.Instruction;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Control code to receive exactly ten bytes of data and parse it into an instruction
 */
public class InstructionParser
{
	/**
	 * Parse a command from the Socket
	 */
	public static Instruction parseInstruction(InputStream stream) throws IOException {
		int res;

		// Receive 1 command byte
		// If the number of bytes received was not exactly one,
		// we have received malformed input
		byte[] command = new byte[1];
		res = stream.read(command);
		if (res != 1)
			throw new IOException("Malformed bytes received: " + res + " bytes - " + Arrays.toString(command));

		// Receive 7 data bytes
		byte[] data = new byte[7];
		res = stream.read(data);
		if (res != 7)
			throw new IOException("Malformed bytes received: " + res + " bytes - " + Arrays.toString(data));

		// Receive 1 code byte
		byte[] code = new byte[1];
		res = stream.read(code);
		if (res != 1)
			throw new IOException("Malformed bytes received: " + res + " bytes - " + Arrays.toString(code));

		// Receive 1 identifier byte
		byte[] identifier = new byte[1];
		res = stream.read(identifier);
		if (res != 1)
			throw new IOException("Malformed bytes received: " + res + " bytes - " + Arrays.toString(identifier));

		return new Instruction(command[0], data, code[0], identifier[0]);
	}
}
