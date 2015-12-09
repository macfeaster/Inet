package ATMClient.data;

public class Command {

    private int id;
    private String name;
    private String help;
    private String data;
    private String code;


    public Command(int id, String name, String help, String data, String code) {
        this.id = id;
        this.name = name;
        this.help = help;
        this.data = data;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHelp() {
        return help;
    }

    public String getData() {
        return data;
    }

    public String getCode() {
        return code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
