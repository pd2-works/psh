package ink.pd2.shell.util;

import ink.pd2.shell.api.Initializeable;
import ink.pd2.shell.api.Plugin;
import ink.pd2.shell.api.PluginInitializationException;
import ink.pd2.shell.api.PluginLoadingException;
import ink.pd2.shell.core.Resources;
import ink.pd2.shell.core.i18n.Language;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarFile;

public final class PluginUtils {
	private PluginUtils() {}
	public final static PluginUtils INS = new PluginUtils();

	public Plugin[] load(String folderPath) throws PluginLoadingException {
		try {

			ArrayList<Plugin> plugins = new ArrayList<>();

			File folder = new File(folderPath);
			if (!folder.isDirectory()) throw
					new PluginLoadingException("The argument is NOT a folder path.");
			File[] files = folder.listFiles();
			ArrayList<URL> jars = new ArrayList<>();

			//过滤jar文件
			for (File i : files) if (i.getName().endsWith(".jar")) {
				jars.add(new URL(i.getCanonicalPath()));
			}

			URLClassLoader loader = new URLClassLoader(jars.toArray(new URL[0]));
			//获取jar中的配置文件路径
			Enumeration<URL> configs = loader.getResources("plugin.xml");
			//循环处理配置文件
			while (configs.hasMoreElements()) {
				String path = configs.nextElement().getFile();
				InputStream stream = new FileInputStream(path);
				PluginInfo[] info = parseXML(stream); //解析插件配置信息
				stream.close();
				//循环加载配置
				for (PluginInfo i : info) {
					String mainClass = i.mainClass;
					if (mainClass.equals("null") || mainClass.startsWith("ink.pd2.shell")) continue;
					Plugin plugin = (Plugin) loader.loadClass(mainClass).newInstance();
					plugins.add(plugin);
					//TODO 其他特性处理(如版本验证)
				}
			}

			return plugins.toArray(new Plugin[0]);

		} catch (Exception e) {
			throw new PluginLoadingException(
					"An exception has been thrown while loading plugins", e);
		}

	}

	public void loadLanguage(Language object) {
		//TODO 加载语言
	}

	public void initObject(Initializeable object) throws PluginInitializationException {
		Resources.groups.add(object.getResourcesGroup());
		object.init();
		object.initLanguage(object.getI18nFiles());
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

	private PluginInfo[] parseXML(InputStream stream) throws DocumentException {
		XMLUtils utils = XMLUtils.INS;
		Element root = utils.readFile(stream).getRootElement();
		Element[] children = utils.getAllChildElements(root, "Plugin");

		ArrayList<PluginInfo> infos = new ArrayList<>();
		for (Element i : children) {
			String mainClass = i.attributeValue("mainClass", "null");
			infos.add(new PluginInfo(mainClass));
		}
		return infos.toArray(new PluginInfo[0]);
	}

	public static class PluginInfo {
		public final String mainClass;

		private PluginInfo(String mainClass) {
			this.mainClass = mainClass;
		}
	}

}
