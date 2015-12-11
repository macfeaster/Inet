package ATMClient.data;

/**
 * Created by my tears falling on the keyboard.
 * 
 */

public class Language {

    private String name;
    private String available;

    public Language(String name, String available) {
        this.name = name;
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public String getAvailable() {
        return available;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
