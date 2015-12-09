package ATMServer.data;

import java.nio.ByteBuffer;

/// <summary>
/// Represents a ten byte instruction for transmission over a network socket
/// </summary>
public class Instruction
{
	byte instruction;
	byte[] data = new byte[7];
	byte code;
	byte identifier;

	/// <summary>
	/// Construct an instruction with only data
	/// </summary>
	public Instruction(byte instruction, long data)
	{
		this.instruction = instruction;
		this.data = longToBytes(data);
		code = 0;
		identifier = 0;
	}

	/// <summary>
	/// Construct a signed instruction with only data
	/// </summary>
	public Instruction(byte instruction, long data, byte identifier)
	{
		this.instruction = instruction;
		this.data = longToBytes(data);
		code = 0;
		this.identifier = identifier;
	}

	/// <summary>
	/// Construct a signed instruction with data and security code
	/// </summary>
	public Instruction(byte instruction, long data, byte code, byte identifier)
	{
		this.instruction = instruction;
		this.data = longToBytes(data);
		this.code = code;
		this.identifier = identifier;
	}

	/**
	 * Construct a signed instruction using raw data and a security code
	 */
	public Instruction(byte instruction, byte[] data, byte code, byte identifier)
	{
		this.instruction = instruction;
		this.data = data;
		this.code = code;
		this.identifier = identifier;
	}

	public byte[] longToBytes(long x) {
		ByteBuffer buffer = ByteBuffer.allocate(8);
		buffer.putLong(x);

		byte[] raw = buffer.array();
		byte[] res = new byte[7];

		System.arraycopy(raw, 0, res, 1, 7);

		return res;
	}

	public long bytesToLong(byte[] bytes) {
		ByteBuffer buffer = ByteBuffer.allocate(8);
		buffer.put((byte) 0);
		buffer.put(bytes);
		buffer.flip();
		return buffer.getLong();
	}
}