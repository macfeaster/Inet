package ATMClient;

public class Bootstrap
{
	public static void main(String[] args) throws Exception
	{
		Client.createInstance()
				.initConnection(args)
				.getJSON()
				.parseJSON()
				.getIdentifier()
				.work();
	}
}
