package ink.pd2.psh.core;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarResourcesItem extends ResourceItem<URL> {
	private final URL jarUrl;
	private final URLClassLoader loader;

	protected JarResourcesItem(String jarPath) throws MalformedURLException {
		jarUrl = new File(jarPath).toURI().toURL();
		loader = new URLClassLoader(new URL[]{jarUrl});
	}

	@Override
	public URL get(int cid, String name) {
		if (isNoReadPermission(cid)) return null;
		return loader.findResource(name);
	}

	@Override
	public String[] getKeys(int cid) {
		if (isNoReadPermission(cid)) return null;
		try {
			JarFile file = new JarFile(jarUrl.getFile());
			Enumeration<JarEntry> entries = file.entries();
			ArrayList<String> filenames = new ArrayList<>();
			while (entries.hasMoreElements()) {
				filenames.add(entries.nextElement().getName());
			}
			return filenames.toArray(new String[0]);
		} catch (IOException e) {
			return null;
		}
	}

	@Override @Deprecated
	public boolean containsKey(int cid, String name) {
		return false;
	}

	@Override @Deprecated
	public boolean containsValue(int cid, URL url) {
		return false;
	}

	@Override
	public String getValueAsString(int cid, String name) {
		URL url = get(cid, name);
		return (url == null)? null : url.getPath();
	}
}
