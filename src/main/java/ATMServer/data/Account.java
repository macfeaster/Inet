package ATMServer.data;

import java.util.LinkedList;

/**
 * Represents an Account in the Bank
 */
public class Account
{
	private LinkedList<Integer> securityCode;
	private int balance;

	public Account(int balance)
	{
		this.securityCode = new LinkedList<>();
		this.balance = balance;

		securityCode.add(1);
		securityCode.add(3);
		securityCode.add(5);
		securityCode.add(7);
	}

	/**
	 * Checks whether the supplied security code is next in the list.
	 * If it is, the code is popped so the next code becomes the valid one.
	 *
	 * @param securityCode  Security code to try
	 */
	public boolean validateCode(byte securityCode)
	{
		if (this.securityCode.peek() == securityCode)
		{
			this.securityCode.pop();
			return true;
		}
		else return false;
	}

	/**
	 * Makes a withdrawal from the user's account. Returns -1 if the withdrawal fails.
	 *
	 * @param amount        Amount to withdraw
	 * @return              -1 if the user has insufficient funds, new balance otherwise
	 */
	public int withdraw(int amount) {
		if (balance - amount >= 0)
		{
			balance = balance - amount;
			return balance;
		}
		else return -1;
	}

	/**
	 * Deposit the supplied amount.
	 *
	 * @param amount        Amount to deposit
	 * @return              New account balance
	 */
	public int deposit(int amount) {
		balance += amount;
		return balance;
	}
}
