package ink.pd2.shell.plugin;

public abstract class Plugin {
    private final String resourcesGroup; //资源组
    private final String name; //插件名
    private final int versionCode; //版本号
    private String description; //描述
    private String versionName; //版本名

    public Plugin(String group, String name, int versionCode) {
        this.resourcesGroup = group;
        this.name = name;
        description = R.getString("psh.plugin-no-description");
        this.versionCode = versionCode;
    }
    public Plugin(String group, String name, int versionCode, String description) {
        this.resourcesGroup = group;
        this.name = name;
        this.versionCode = versionCode;
        this.description = description;
    }

    public abstract void init();

    //get & set
    public String getResourcesGroup() {
        return resourcesGroup;
    }
    public String getName() {
        return name;
    }
    public int getVersionCode() {
        return versionCode;
    }
    public String getVersionName() {
        return versionName;
    }
    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

}
