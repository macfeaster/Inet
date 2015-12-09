package ATMServer;

import java.io.IOException;

public class Bootstrap
{
	public static void main(String[] args) throws IOException
	{
		Server.createInstance()
				.readData()
				.registerHandlers()
				.openConnection()
				.handleData();
	}
}
