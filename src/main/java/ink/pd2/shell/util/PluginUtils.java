package ink.pd2.shell.util;

import ink.pd2.shell.api.plugin.Initializeable;
import ink.pd2.shell.api.plugin.Plugin;
import ink.pd2.shell.api.plugin.PluginLoadingException;
import ink.pd2.shell.core.Resources;
import ink.pd2.shell.core.i18n.Language;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public final class PluginUtils {
	private PluginUtils() {}
	public final static PluginUtils INS = new PluginUtils();

	public Plugin[] load(String folderPath) throws PluginLoadingException {
		ArrayList<Plugin> plugins = new ArrayList<>();

		File folder = new File(folderPath);
		if (!folder.isDirectory()) throw
				new PluginLoadingException("The argument is NOT a folder path.");
		File[] files = folder.listFiles();
		ArrayList<URL> jars = new ArrayList<>();
		try {

			for (File i : files) if (i.getName().endsWith(".jar")) {
				jars.add(new URL(i.getCanonicalPath()));
			}
			if (jars.size() == 0) throw
					new PluginLoadingException("Empty plugin folder.");

			URLClassLoader loader = new URLClassLoader(jars.toArray(new URL[0]));
			Enumeration<URL> configs = loader.getResources("plugin.xml");
			while (configs.hasMoreElements()) {
				String path = configs.nextElement().getFile();
				InputStream stream = new FileInputStream(path);
				PluginInfo[] infos = parseXML(stream);
				for (PluginInfo info : infos) {
					String mainClass = info.mainClass;
					if (mainClass.equals("null") || mainClass.startsWith("ink.pd2.shell")) continue;
					Plugin plugin = (Plugin) loader.loadClass(mainClass).newInstance();
					plugins.add(plugin);
					//TODO 其他处理
				}
			}

		} catch (Exception e) {
			throw new PluginLoadingException(
					"An exception has been thrown while loading plugins", e);
		}

		return plugins.toArray(new Plugin[0]);
	}

	public void loadLanguage(Language object) {
		//TODO 加载语言
	}

	public void initObject(Initializeable object) {
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
