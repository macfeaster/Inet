package ATMServer.ATM;

import ATMServer.data.Account;
import ATMServer.data.Instruction;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Creates a map with all functions
 */
public class Functions
{
	public static Map<Byte, Function<Instruction, Instruction>> getFunctions(Bank bank)
	{
		Map<Byte, Function<Instruction, Instruction>> functions = new HashMap<>();

		/**
		 * Handle incoming instructions with command ID #1 (login)
		 */
		functions.put((byte) 1, instr -> {
			Account acc = bank.getCustomer(instr.getData());

			if (acc.validateCode(instr.getCode())) {
				// Open session and return success Instruction
				bank.getSessions().put(instr.getIdentifier(), instr.getData());

				// Send a valid session response code (100 welcome)
				return new Instruction((byte) 100);
			} else
				// If validation failed, send an invalid security response (82 invalid cc)
				return new Instruction((byte) 82);
		});

		/**
		 * Handle #2 (logout)
		 */
		functions.put((byte) 2, instruction -> {
			// If there is a session open to log out
			if (bank.getSessions().containsKey(instruction.getIdentifier())) {
				// End the session
				bank.getSessions().remove(instruction.getIdentifier());

				// Return
				return new Instruction((byte) 100);
			} else
				// If the session does not exist, send error response (80 invalid cmd)
				return new Instruction((byte) 80);
		});

		/**
		 * Handle #3 (balance)
		 */
		functions.put((byte) 3, instruction -> {
			Account acc = bank.getLoggedInAccount(instruction.getIdentifier());

			if (acc == null)
				return new Instruction((byte) 80);

			return new Instruction((byte) 101, acc.getBalance());
		});

		return functions;
	}
}
