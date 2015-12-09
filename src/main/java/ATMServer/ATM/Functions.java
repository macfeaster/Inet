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

		functions.put((byte) 1, instr -> {
			Account acc = bank.getCustomer(instr.getData());

			if (acc.validateCode(instr.getCode())) {
				// Open session and return success Instruction
				bank.getSessions().put(instr.getIdentifier(), instr.getData());
				return new Instruction((byte) 100);
			} else return new Instruction((byte) 82);
		});

		return functions;
	}
}
