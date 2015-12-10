package common;

import java.nio.ByteBuffer;

/**
 * Represents a ten byte command for transmission over a network socket
 */
@SuppressWarnings("unused")
public class Instruction
{
	byte command;
	byte[] data = new byte[7];
	byte code;
	byte identifier;

	/**
	 * Construct an response command without data
	 *
	 * @param command           1 byte command
	 */
	public Instruction(byte command)
	{
		this.command = command;
		code = 0;
		identifier = 0;
	}

	/**
	 * Construct an command with only data
	 *
	 * @param command           1 byte command
	 * @param data              7 byte data
	 */
	public Instruction(byte command, long data)
	{
		this.command = command;
		this.data = longToBytes(data);
		code = 0;
		identifier = 0;
	}

	/**
	 * Construct a signed command with only data
	 *
	 * @param command           1 byte command
	 * @param data              7 byte data
	 * @param identifier        1 byte ATM machine identifier
	 */
	public Instruction(byte command, long data, byte identifier)
	{
		this.command = command;
		this.data = longToBytes(data);
		code = 0;
		this.identifier = identifier;
	}

	/**
	 * Construct a signed command using raw data and a security code
	 *
	 * @param command           1 byte command
	 * @param data              7 byte data
	 * @param code              1 byte security code
	 * @param identifier        1 byte ATM machine identifier
	 */
	public Instruction(byte command, long data, byte code, byte identifier)
	{
		this.command = command;
		this.data = longToBytes(data);
		this.code = code;
		this.identifier = identifier;
	}

	/**
	 * Construct a signed command using raw data and a security code
	 */
	public Instruction(byte command, byte[] data, byte code, byte identifier)
	{
		this.command = command;
		this.data = data;
		this.code = code;
		this.identifier = identifier;
	}

	private byte[] longToBytes(long x) {
		ByteBuffer buffer = ByteBuffer.allocate(8);
		buffer.putLong(x);

		byte[] raw = buffer.array();
		byte[] res = new byte[7];

		System.arraycopy(raw, 1, res, 0, 7);

		return res;
	}

	private long bytesToLong(byte[] bytes) {
		ByteBuffer buffer = ByteBuffer.allocate(8);
		buffer.put((byte) 0);
		buffer.put(bytes);
		buffer.flip();
		return buffer.getLong();
	}

	public byte getCode() { return code; }
	public byte getIdentifier() { return identifier; }
	public long getData() { return bytesToLong(data); }
	public byte[] getRawData() { return data; }
	public byte getCommand() { return command; }
}