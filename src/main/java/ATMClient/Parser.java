package ATMClient;

import ATMClient.data.Command;
import ATMClient.data.Language;
import common.Logger;
import org.json.*;
import java.util.HashMap;
import java.util.Map;

public class Parser {

	static Logger logger = Logger.getInstance();

    /**
     * Parses JSON file into Map containing all available commands
     *
     * @param file JSON text file
     * @return Map with key language and value Map
     * with key name and val command
     */
    public static Map<String, Map<String, Command>> commands(String file) {

        // Map containing all the root command maps for each language
        Map<String, Map<String, Command>> rootMap = new HashMap<>();

        // The command object contains each command as JSON object
        JSONArray commands = new JSONObject(file).getJSONArray("commands");

	    // For each command in array
        for (Object c : commands) {

            // Construct a command JSON object
            JSONObject command = (JSONObject) c;  // cast to JSONObject
	        logger.debug(command);
            int id = 0;

	        for (String langKey : command.keySet())
	        {
		        // If language map has not yet been instantiated, do so
		        if (langKey.equals("id"))
		        {
			        logger.debug("Found command with ID " + command.getInt(langKey));
			        id = command.getInt(langKey);
			        continue;
		        }

		        JSONObject langVal = command.getJSONObject(langKey);
		        logger.debug("LangVal: " + langVal);

		        logger.debug(langVal);
		        logger.debug(langVal.get("name"));

		        // If language map has not yet been instantiated, do so
		        if (!rootMap.containsKey(langKey))
			        rootMap.put(langKey, new HashMap<>());

		        // Put sad bundle of instructions in sad HashMap
			        rootMap.get(langKey).
					        put(langVal.getString("name"),
							        new Command(
									        id,
									        langVal.getString("name"),
									        langVal.getString("help"),
									        langVal.optString("data", null),
									        langVal.optString("code", null)));
	        }
        }
        return rootMap;
    }

    public static Map<String, Map<Integer, String>> responses(String file) {

        // Map containing all the root response maps for each language
        Map<String, Map<Integer, String>> map = new HashMap<>();

        // responses contains each response object
        JSONArray responses = new JSONObject(file).getJSONArray("responses");

        for(Object r : responses) {
            JSONObject response = (JSONObject) r;
            logger.debug(response);

            for (String langKey : response.keySet()) {

                int id = 0;
                // Special parse case for "id"
                if (langKey.equals("id")) {
                    logger.debug("Found command with ID " + response.getInt(langKey));
                    id = response.getInt(langKey);
                    continue;
                }

                // If language map has not yet been instantiated, do so
                if (!map.containsKey(langKey)) {
                    map.put(langKey, new HashMap<>());
                }

                // Put response in map in map
                map.get(langKey).put(id, response.getString(langKey));
            }
        }
        return map;
    }

    public static Map<String, Language> languages(String file) {
        Map<String, Language> map = new HashMap<>();

        // langs is JSONObject containing each lang as object
        JSONObject langs = new JSONObject(file).getJSONObject("languages");

        while (langs.keys().hasNext()) {

            // Retrieve the langKey
            String langKey = langs.keys().next();

            // Fetch language object
            //
            // Gretchen, stop trying to make fetch happen!
            // It's not going to happen!
            //
            JSONObject lang = langs.getJSONObject(langKey);

            // Put lang in map
            map.put(langKey, new Language(lang.getString("name"), lang.getString("available")));
        }

        return map;
    }
}
