package common;

import java.util.Date;

public class Logger
{
	private static volatile Logger instance = null;
	public enum Type { INFO, ERROR, LOG, DEBUG }

	/**
	 * Singleton getInstance() method
	 * @return	Singleton instance
	 */
	public static Logger getInstance()
	{
		if (instance == null)
			synchronized (Logger.class)
			{
				if (instance == null)
					instance = new Logger();
			}

		return instance;
	}

	public Logger log(String message, Type type)
	{
		System.out.printf("[%s] %s: %s \n",
				type,
				new Date().toString(),
				message);

		return instance;
	}

	public Logger debug(Object message) {
		debug(message.toString());
		return this;
	}

	public Logger log(String message)
	{
		log(message, Type.LOG);
		return instance;
	}

	public Logger info(String message)
	{
		log(message, Type.INFO);
		return instance;
	}

	public Logger error(String message)
	{
		log(message, Type.ERROR);
		return instance;
	}

	public Logger debug(String message)
	{
		log(message, Type.DEBUG);
		return instance;
	}
}
