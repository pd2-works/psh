package ink.pd2.shell.plugin;

public abstract class Plugin {
    private final String name;
    private final String description;
    private final int versionCode;
    private String versionName;

    public Plugin(String name) {
        this.name = name;
        description = R.getString("psh.plugin-no-description");
        versionCode = 1;
    }
    public Plugin(String name, String description) {
        this.name = name;
        this.description = description;
        versionCode = 1;
    }
    public Plugin(String name, int versionCode) {
        this.name = name;
        description = R.getString("psh.plugin-no-description");
        this.versionCode = versionCode;
    }
    public Plugin(String name, int versionCode, String description) {
        this.name = name;
        this.versionCode = versionCode;
        this.description = description;
    }

    public abstract void init();

    //get & set
    public String getName() {
        return name;
    }
    public int getVersionCode() {
        return versionCode;
    }
    public String getVersionName() {
        return versionName;
    }
    public String getDescription() {
        return description;
    }

}
