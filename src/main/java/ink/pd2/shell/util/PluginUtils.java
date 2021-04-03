package ink.pd2.shell.util;

import ink.pd2.shell.api.Initializeable;
import ink.pd2.shell.api.Plugin;
import ink.pd2.shell.api.InitializationException;
import ink.pd2.shell.api.PluginLoadingException;
import ink.pd2.shell.core.Logger;
import ink.pd2.shell.core.Resources;
import ink.pd2.shell.core.i18n.Language;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarFile;

public final class PluginUtils {
	//常量
	public static final int EXTENSION_PLATFORM = 0x01;

	private PluginUtils() {}
	public final static PluginUtils INS = new PluginUtils();

	public Plugin[] load(String folderPath) throws PluginLoadingException {
		try {

			ArrayList<Plugin> plugins = new ArrayList<>();

			File folder = new File(folderPath);
			if (!folder.isDirectory()) throw
					new PluginLoadingException('\'' + folder.getAbsolutePath() + "' is NOT a directory.");
			File[] files = folder.listFiles();
			ArrayList<URL> jars = new ArrayList<>();

			//过滤jar文件
			for (File i : files) if (i.getName().endsWith(".jar")) {
				jars.add(new URL(i.getCanonicalPath()));
			}

			URLClassLoader loader = new URLClassLoader(jars.toArray(new URL[0]));
			//获取jar中的配置文件路径
			Enumeration<URL> configs = loader.getResources("manifest.xml");
			//循环处理配置文件
			while (configs.hasMoreElements()) {
				String path = configs.nextElement().getFile();
				InputStream stream = new FileInputStream(path);
				Manifest info = parseXML(stream); //解析插件配置信息
				stream.close();
				//循环加载配置
				for (Manifest.PluginInfo i : info.plugins) {
					String mainClass = i.mainClass;
					if (mainClass.equals("null") || mainClass.startsWith("ink.pd2.shell")) continue;
					Plugin plugin = (Plugin) loader.loadClass(mainClass).newInstance();
					plugins.add(plugin);
					Logger.INS.debug("Plugin.Load",
							"A new plugin instance of '" + plugin.getResourcesId() + "' was created");
				}

			}

			return plugins.toArray(new Plugin[0]);

		} catch (Exception e) {
			throw new PluginLoadingException(
					"An exception was thrown while loading plugins", e);
		}

	}

	public void loadLanguage(Language object) {
		//TODO 加载语言
	}

	public void initObject(Initializeable object) throws InitializationException {
		Resources.id.add(object.getResourcesId());
		object.init();
	}

	public Plugin loadJar(JarFile file) {
		//TODO 加载单个文件
		return null;
	}
	public Plugin reloadJar(JarFile file) {
		//TODO 热重载
		return null;
	}

//	private String readConfigFile(JarFile file, String name) throws IOException {
//		//初始化文件读取流
//		JarEntry entry = file.getJarEntry(name);
//		InputStreamReader r = new InputStreamReader(
//				file.getInputStream(entry), StandardCharsets.UTF_8);
//
//		//读取文件内容
//		StringBuilder s = new StringBuilder();
//		int tmp;
//		while((tmp = r.read()) != -1){
//			s.append((char) tmp);
//		}
//
//		r.close();
//		return s.toString();
//	}

	private Manifest parseXML(InputStream stream) throws DocumentException {
		XMLUtils utils = XMLUtils.INS;
		Element root = utils.readFile(stream).getRootElement();

		//TODO 加载功能

		//加载插件
		Element[] plugins = utils.getAllChildElements(root, "plugin");
		ArrayList<Manifest.PluginInfo> pluginInfo = new ArrayList<>();
		for (Element i : plugins) {
			String mainClass = i.attributeValue("mainClass", "null");
			String name = i.attributeValue("name", "Unnamed");
			int versionCode = Integer.parseInt(
					i.attributeValue("versionCode", "1"));
			pluginInfo.add(new Manifest.PluginInfo(mainClass, name, versionCode));
		}

		//加载扩展
		Element[] extensions = utils.getAllChildElements(root, "extension");
		ArrayList<Manifest.ExtensionInfo> extensionInfo = new ArrayList<>();
		for (Element i : extensions) {
			String mainClass = i.attributeValue("mainClass", "null");
			String name = i.attributeValue("name", "Unnamed");
			extensionInfo.add(new Manifest.ExtensionInfo(mainClass, name));
		}

		//TODO 加载功能

		return new Manifest(
				pluginInfo.toArray(new Manifest.PluginInfo[0]),
				extensionInfo.toArray(new Manifest.ExtensionInfo[0]),
				null
		);
	}

	//清单文件数据
	public static class Manifest {
		public final PluginInfo[] plugins;
		public final ExtensionInfo[] extensions;
		public final FunctionInfo[] functions;

		public Manifest(PluginInfo[] plugins, ExtensionInfo[] extensions, FunctionInfo[] functions) {
			this.plugins = plugins;
			this.extensions = extensions;
			this.functions = functions;
		}

		//扩展信息
		public static class ExtensionInfo {
			public final String mainClass;
			public final String name;
			private ExtensionInfo(String mainClass, String name) {
				this.mainClass = mainClass;
				this.name = name;
			}
		}

		//插件信息
		public static class PluginInfo extends ExtensionInfo {
			public final int versionCode;
			private PluginInfo(String mainClass, String name, int versionCode) {
				super(mainClass, name);
				this.versionCode = versionCode;
			}
		}

		//启用功能信息
		public static class FunctionInfo {
			public String name;
			public LinkedHashMap<String, String> parameters;

			public FunctionInfo(String name, LinkedHashMap<String, String> parameters) {
				this.name = name;
				this.parameters = parameters;
			}
		}

	}

}
