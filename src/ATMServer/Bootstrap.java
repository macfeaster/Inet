package ATMServer;

import ATMServer.engine.Server;

import java.io.IOException;

public class Bootstrap
{
	public static void main(String[] args) throws IOException
	{
		Server
				.createInstance()
				.readData()
				.openConnection()
				.registerHandlers();
	}
}
