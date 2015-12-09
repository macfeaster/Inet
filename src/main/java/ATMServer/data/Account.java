package ATMServer.data;

public class Account
{
	private long creditCard;
	private byte securityCode;
	private int balance;

	public Account(long creditCard, byte securityCode, int balance)
	{
		this.creditCard = creditCard;
		this.securityCode = securityCode;
		this.balance = balance;
	}

	public boolean validateCode(int creditCard, byte securityCode)
	{
		return (this.creditCard == creditCard && this.securityCode == securityCode);
	}


}
