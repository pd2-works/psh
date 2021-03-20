package ink.pd2.shell.api;

import ink.pd2.shell.core.Resources;
import ink.pd2.shell.core.i18n.Language;
import ink.pd2.shell.util.PluginUtils;

import java.io.File;
import java.util.HashMap;

public abstract class Plugin implements Initializeable {
	private final String resourcesId; //资源组
	private final int versionCode; //版本号
	private String name; //插件名
	private String description; //描述
	private String versionName; //版本名

	private final PluginInterface api; //工具对象

	public Plugin(String resourcesId, int versionCode) {
		this(resourcesId, versionCode, null, null);
	}
	public Plugin(String resourcesId, int versionCode, String name) {
		this(resourcesId, versionCode, name, null);
	}
	public Plugin(String resourcesId, int versionCode, String name, String description) {
		this.resourcesId = resourcesId;
		this.name = name;
		this.versionCode = versionCode;
		this.description = description;
		api = new PluginInterface(this);
	}

	@Override
	public void init() throws InitializationException {
		try {
			init(getApi());
		} catch (Exception e) {
			throw new InitializationException(
					"An exception has been thrown while the plugin '"
							+ getResourcesId() + "' is initializing.", e);
		}
	}

	@Override
	public void initLanguage(File[] files) throws InitializationException {
		try {
			for (File file : files) {
				PluginUtils.INS.loadLanguage(new Language(file));
			}
		} catch (Exception e) {
			throw new InitializationException(
					"An exception has been thrown while the plugin '"
							+ getResourcesId() + "' is initializing.", e);
		}
	}

	public abstract void init(PluginInterface api) throws Exception;

	//get & set
	public String getResourcesId() {
		return resourcesId;
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
			return Resources.INS.getString(resourcesId + '.' + key);
		}

		public void put(String key, String defaultValue) {
			Resources.INS.putString(resourcesId + '.' + key, defaultValue);
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
