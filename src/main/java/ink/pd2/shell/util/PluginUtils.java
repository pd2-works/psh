package ink.pd2.shell.util;

import ink.pd2.shell.api.plugin.Initializeable;
import ink.pd2.shell.api.plugin.Plugin;
import ink.pd2.shell.core.Resources;
import ink.pd2.shell.core.i18n.Language;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public final class PluginUtils {
	public final static PluginUtils INS = new PluginUtils();

	private final List<Plugin> plugins = new ArrayList<>();

	public void load(String folderPath) {
		//TODO 加载插件
	}

	public void loadLanguage(Language object) {
		//TODO 加载语言
	}

	public void init(Initializeable object) {
		Resources.groups.add(object.getResourcesGroup());
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

	private String readConfigFile(JarFile file) throws IOException {
		//初始化文件读取流
		JarEntry entry = file.getJarEntry("plugin.psh");
		InputStreamReader r = new InputStreamReader(
				file.getInputStream(entry), StandardCharsets.UTF_8);

		//读取文件内容
		StringBuilder s = new StringBuilder();
		int tmp;
		while((tmp = r.read()) != -1){
			s.append((char) tmp);
		}

		r.close();
		return s.toString();
	}
}
