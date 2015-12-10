package common;

import java.io.IOException;
import java.io.OutputStream;

public class Writer
{
	public static void write(Instruction instruction, OutputStream out) throws IOException
	{
		out.write(instruction.getCommand());
		out.write(instruction.getRawData());
		out.write(instruction.getCode());
		out.write(instruction.getIdentifier());
	}
}
