package ATMClient;

import ATMClient.data.Command;
import ATMClient.data.Language;
import common.InstructionParser;
import common.Writer;
import common.Instruction;
import common.Logger;

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

		BufferedReader r = new BufferedReader(new InputStreamReader(in));
		rawJSON = r.readLine();

		return this;
	}

	public Client parseJSON() {

		logger.debug("Received JSON of length " + rawJSON.length());

		commands = Parser.commands(rawJSON);
		logger.debug("Finished parsing commands JSON");

		responses = Parser.responses(rawJSON);
		logger.debug("Finished parsing responses JSON");

		languages = Parser.languages(rawJSON);
		logger.debug("Finished parsing languages JSON");

		return this;
	}

	private void printAvailableCommands() {

		System.out.println(languages.get(selectedLanguage).getAvailable());

		for(String c : commands.get(selectedLanguage).keySet()) {
			System.out.println("(" + c + ") " + commands.get(selectedLanguage).get(c).getHelp());
		}
		System.out.println();

	}

	private void printAvailableLanguages() {

		System.out.println("Available languages");

		for (String l : languages.keySet()) {
			System.out.println("(" + l + ") " + languages.get(l));
		}
		System.out.println();
	}

    private void selectLanguage() {

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

            Command command = commands.get(selectedLanguage).getOrDefault(input, null);

            if (command != null) {
                byte cmd = (byte) command.getId();
                byte[] data = new byte[7];
                byte code = (byte) 0;
                byte identifier = (byte) 0;

                if (command.getData() != null) {
                    System.out.println(command.getData());

                    ByteBuffer buffer = ByteBuffer.allocate(8);
                    buffer.putLong(scanner.nextLong());

                    byte[] raw = buffer.array();
                    data = new byte[7];

                    System.arraycopy(raw, 1, data, 0, 7);

                }

                if (command.getCode() != null) {
                    System.out.println(command.getCode());
                    code = (byte) scanner.nextInt();
                }

				out.write(cmd);
				out.write(data);
				out.write(code);
				out.write(identifier);

				Instruction instruction = InstructionParser.parseInstruction(in);



            }

        }
        return this;
    }

}
