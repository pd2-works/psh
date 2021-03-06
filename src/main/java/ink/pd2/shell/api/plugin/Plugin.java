package ink.pd2.shell.api.plugin;

import ink.pd2.shell.core.Resources;
import ink.pd2.shell.core.i18n.Language;
import ink.pd2.shell.util.PluginUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public abstract class Plugin implements Initializeable {
	private final String resourcesGroup; //资源组
	private final int versionCode; //版本号
	private String name; //插件名
	private String description; //描述
	private String versionName; //版本名

	private final PluginInterface api; //工具对象

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
		api = new PluginInterface(this);
	}

	@Override
	public void init() {
		init(getApi());
	}

	@Override
	public void initLanguage(File[] files) {
		for (File file : files) {
			try {
				PluginUtils.INS.loadLanguage(new Language(file));
			} catch (IOException e) {
				throw new PluginInitializationException(
						"An exception has been thrown while the plugin '"
								+ getResourcesGroup() + "' is initializing.");
			}
		}
	}

	public abstract void init(PluginInterface api);

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

	public PluginInterface getApi() {
		return api;
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
