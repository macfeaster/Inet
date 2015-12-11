package ATMClient;

import ATMClient.data.Command;
import ATMClient.data.Language;
import common.Instruction;
import common.InstructionParser;
import common.Logger;
import common.Writer;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Scanner;

public class Client
{
	InputStream in;
	OutputStream out;
	String rawJSON;
	Logger logger = Logger.getInstance();
	Map<String, Map<String, Command>> commands;
	Map<String, Map<Integer, String>> responses;
	Map<String, Language> languages;
	String selectedLanguage;
    Scanner scanner;
	byte id;

	public static Client createInstance() {
		return new Client();
	}

	public Client initConnection(String[] args) throws Exception {

		Socket s = new Socket(args[0], 2178);

		out = s.getOutputStream();
		in = s.getInputStream();

        scanner = new Scanner(System.in);

		return this;
	}

	public Client getJSON() throws IOException {

		// Send boot command
		Instruction boot = new Instruction((byte) 0);
		Writer.write(boot, out);

		// Get JSON response
		BufferedReader r = new BufferedReader(new InputStreamReader(in));
		rawJSON = r.readLine();

		return this;
	}

	public Client parseJSON() {

		commands = Parser.commands(rawJSON);
		responses = Parser.responses(rawJSON);
		languages = Parser.languages(rawJSON);

		return this;
	}

	public Client getIdentifier() throws IOException {

		Writer.write(new Instruction((byte) 6), out);

		Instruction identifier = InstructionParser.parseInstruction(in);

		this.id = (byte) identifier.getData();

		logger.debug("Set ID to " + this.id);

		return this;
	}

	private void printAvailableCommands() {

		// System.out.println(languages.get(selectedLanguage).getAvailable());

		logger.debug(commands.get(selectedLanguage).keySet().size() + " commands");

		commands.get(selectedLanguage)
				.keySet()
				.forEach(c -> System.out.println("(" + c + ") " + commands.get(selectedLanguage).get(c).getHelp()));
	}

	private void printAvailableLanguages() {

		System.out.println("Available languages");

		languages.keySet().forEach(l -> System.out.println("(" + l + ") " + languages.get(l)));
	}

    private void selectLanguage() {

	    System.out.print("> ");
        String input = scanner.nextLine();

        if (languages.containsKey(input)) selectedLanguage = input;
        else selectedLanguage = "sk-SU"; // set default language
    }

    public Client work() throws Exception {

        boolean activeSession = true;

        // select language
        printAvailableLanguages();
        selectLanguage();

        while (activeSession) {

            printAvailableCommands();

            // take input from user
            String input = scanner.nextLine();

	        System.out.println("LOL FOUND NEW LINE");
	        System.out.println(input.length());

            Command command = commands.get(selectedLanguage).getOrDefault(input, null);

            if (command != null) {
                byte cmd = (byte) command.getId();
                byte[] data = new byte[7];
                byte code = (byte) 0;
                byte identifier = this.id;

                if (command.getData() != null && !command.getData().equals("false")) {
                    System.out.print(command.getData() + "> ");

                    ByteBuffer buffer = ByteBuffer.allocate(8);
                    buffer.putLong(Long.decode(scanner.nextLine()));

                    byte[] raw = buffer.array();
                    data = new byte[7];

                    System.arraycopy(raw, 1, data, 0, 7);

                }

                if (command.getCode() != null && !command.getCode().equals("false")) {
	                System.out.print(command.getCode() + "> ");
                    code = scanner.nextByte();
	                scanner.nextLine();
                }

				out.write(cmd);
				out.write(data);
				out.write(code);
				out.write(identifier);

				Instruction instruction = InstructionParser.parseInstruction(in);

	            String responseString = responses.get(selectedLanguage).get((int) instruction.getCommand());

	            if (responseString == null) {
		            logger.error("Server sent invalid response.");
		            System.exit(-1);
	            }

	            System.out.println(responseString);

	            System.out.printf(responseString + "\n",
			            instruction.getData());
            }

        }
        return this;
    }

}
