package ATMClient;

public class Bootstrap
{
	public static void main(String[] args)
	{
		Client.createInstance()
				.initConnection()
				.getJSON()
				.parseJSON();
	}
}
