package ATMClient;

import ATMClient.data.Command;
import org.json.*;
import java.util.HashMap;
import java.util.Map;

public class Parser {

    /**
     * Parses JSON file into Map containing all available commands
     *
     * @param file JSON text file
     * @return Map with key language and value Map
     * with key name and val command
     */
    public static Map<String, Map<String, Command>> commands(String file) {

        // Map containing all the root command maps for each language
        Map<String, HashMap<String, Command>> rootMap = new HashMap<>();

        // arr contains each command as JSON object
        JSONArray commands = new JSONObject(file).getJSONArray("commands");

        // for each command in array
        for (Object c : commands) {

            // Construct a command JSON object
            JSONObject command = (JSONObject) c;  // cast to JSONObject
            int id;

            // Parse all command options (name, help, ...)
            while (command.keys().hasNext()) {

                // Retrieve the key
                String key = command.keys().next();

                // Special parse case for "id"
                if (key.equals("id")) {
                    id = command.getInt(key);
                    continue;
                }

                // Get the JSON object containing all language keys
                JSONObject langs = command.getJSONObject(key);

                while (langs.keys().hasNext()) {
                    String langKey = langs.keys().next();

                    // If there is no root command map for language key
                    // (for example "en-US", create it
                    if (!rootMap.containsKey(langKey))
                        rootMap.put(langKey, new HashMap<>());

                    if (rootMap.get(langKey).containsKey(key))
                    rootMap.get(langKey).put(command, )
                }
            }

            rootMap.put()

            }
        return null;
    }
}
