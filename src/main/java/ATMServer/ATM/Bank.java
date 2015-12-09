package ATMServer.ATM;

import ATMServer.data.Account;

import java.util.HashMap;
import java.util.Map;

public class Bank
{
	Map<Long, Account> customers = new HashMap<>();
	Map<Byte, Long> sessions = new HashMap<>();

	public Bank()
	{
		// Create some dummy customers
		customers.put(1111222233334444L, new Account(500));
		customers.put(9999999999999999L, new Account(7000));
	}

	public Map<Long, Account> getCustomers() { return customers; }
	public Map<Byte, Long> getSessions() { return sessions; }
}
