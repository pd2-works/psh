package ink.pd2.shell.api;

import ink.pd2.shell.Main;
import ink.pd2.shell.core.Resources;

public abstract class Plugin {
    private final String resourcesGroup; //资源组
    private final String name; //插件名
    private final int versionCode; //版本号
    private String description; //描述
    private String versionName; //版本名

    private final PluginUtils utils;

    public Plugin(String resourcesGroup, String name, int versionCode) {
        this(resourcesGroup, name, versionCode,
                Resources.INS.getString("psh.plugin-no-description"));
    }
    public Plugin(String resourcesGroup, String name, int versionCode, String description) {
        this.resourcesGroup = resourcesGroup;
        this.name = name;
        this.versionCode = versionCode;
        this.description = description;
        utils = new PluginUtils(this);
    }

    public abstract void init(PluginUtils utils);

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

    public PluginUtils getUtils() {
        return utils;
    }
}
