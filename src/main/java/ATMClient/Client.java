package ATMClient;

import ATMClient.data.Command;
import ATMClient.data.Language;
import common.Writer;
import common.Instruction;
import common.Logger;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Client
{
	InputStream in;
	OutputStream out;
	String rawJSON;
	Logger logger = Logger.getInstance();

	public static Client createInstance() {
		return new Client();
	}

	public Client initConnection(String[] args) throws Exception {

		Socket s = new Socket(args[0], 2178);

		out = s.getOutputStream();
		in = s.getInputStream();

		return this;
	}

	public Client getJSON() throws IOException {

		// Send boot command
		Instruction boot = new Instruction((byte) 0);
		Writer.write(boot, out);

		BufferedReader r = new BufferedReader(new InputStreamReader(in));
		rawJSON = r.readLine();

		return this;
	}

	public Client parseJSON() {

		logger.debug("Received JSON of length " + rawJSON.length());

		Map<String, Language> languages = Parser.languages(rawJSON);
		Map<String, Map<String, Command>> commands = Parser.commands(rawJSON);
		Map<String, Map<Integer, String>> responses = Parser.responses(rawJSON);

		logger.debug("Finished parsing JSON");

		return this;
	}
}
