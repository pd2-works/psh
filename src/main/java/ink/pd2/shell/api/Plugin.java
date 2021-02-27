package ink.pd2.shell.api;

import ink.pd2.shell.core.Resources;

import java.util.HashMap;

public abstract class Plugin implements Initializeable {
	private final String resourcesGroup; //资源组
	private final int versionCode; //版本号
	private String name; //插件名
	private String description; //描述
	private String versionName; //版本名

	private final PluginUtils utils; //工具对象

	public Plugin(String resourcesGroup, int versionCode) {
		this(resourcesGroup, versionCode,
				Resources.INS.getString("psh.plugin-no-name"));
	}
	public Plugin(String resourcesGroup, int versionCode, String name) {
		this(resourcesGroup, versionCode, name,
				Resources.INS.getString("psh.plugin-no-description"));
	}
	public Plugin(String resourcesGroup, int versionCode, String name, String description) {
		this.resourcesGroup = resourcesGroup;
		this.name = name;
		this.versionCode = versionCode;
		this.description = description;
		utils = new PluginUtils(this);
	}

	@Override
	public void init() {
		init(getUtils());
	}

	public abstract void init(PluginUtils utils);

	//get & set
	public String getResourcesGroup() {
		return resourcesGroup;
	}
	public int getVersionCode() {
		return versionCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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

	//API: string resources
	public final StringUtils s = new StringUtils();

	public class StringUtils {
		public String get(String key) {
			return Resources.INS.getString(resourcesGroup + '.' + key);
		}

		public void put(String key, String defaultValue) {
			Resources.INS.putString(resourcesGroup + '.' + key, defaultValue);
		}

		public String remove(String key) {
			return Resources.INS.removeString(key);
		}

		@Deprecated
		public HashMap<String, String> getAll() {
			//TODO 获取插件资源组中所有字符串
			return null;
		}
	}

}
