package ink.pd2.shell.plugin;

public class Plugin {
    private final String name;
    private final String description;

    public Plugin(String name) {
        this.name = name;
        description = R.getString("psh.plugin-no-description");
    }
    public Plugin(String name, String description) {
        this.name = name;
        this.description = description;
    }

    //get & set
    public String getName() {
        return name;
    }

}
