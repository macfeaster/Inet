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
    public static HashMap<String, HashMap<String, Command>> commands(String file) {

        // Map containing all the root command maps for each language
        HashMap<String, HashMap<String, Command>> rootMap = new HashMap<>();

       // commands contains each command as JSON object
        JSONArray commands = new JSONObject(file).getJSONArray("commands");

      // for each command in array
        for (Object c : commands) {

            // Construct a command JSON object
            JSONObject command = (JSONObject) c;  // cast to JSONObject
            int id;

           // Parse all language options
            while (command.keys().hasNext()) {

                // Retrieve the key
                String langKey = command.keys().next();

                // Special parse case for "id"
                if (langKey.equals("id")) {
                    continue;
                }

                // If there is no root command map for language key
                // (for example "en-US", create it
                if (!rootMap.containsKey(langKey)) {
                    rootMap.put(langKey, new HashMap<>());
                }

                // Get object with keys (for example "name": "login")
                JSONObject keys = command.getJSONObject(langKey);

                // Put sad bundle of instructions in sad HashMap
                rootMap.get(langKey).
                        put(keys.getString("name"),
                                new Command(
                                        keys.getInt("id"),
                                        keys.getString("name"),
                                        keys.getString("help"),
                                        keys.getString("data"),
                                        keys.getString("code")));

            }
        }
        return rootMap;
    }
}
