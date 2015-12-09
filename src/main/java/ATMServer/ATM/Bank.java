package ATMServer.ATM;

import java.util.HashMap;
import java.util.Map;

public class Bank
{
	Map<Long, Byte> customers = new HashMap<>();
	Map<Byte, Long> sessions = new HashMap<>();

	public Bank()
	{
		// Create some dummy customers

	}
}
