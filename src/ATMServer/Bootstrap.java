package ATMServer;

import ATMServer.engine.Server;

public class Bootstrap
{
	public static void main(String[] args)
	{
		Server
				.createInstance()
				.readData()
				.parseData()
				.openConnection()
				.registerHandlers();
	}
}
