package ink.pd2.psh.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Path {
	private final String current;
	private final boolean isDirectory;
	private final boolean isUnixPath;

	public Path(String path) {
		path = path.replaceAll("\\\\", "/");
		if (path.startsWith("~")) {
			String home = System.getProperty("user.home");
			if (path.length() == 1) path = home;
			else {
				String[] tmp = path.substring(1).split("/", 2);
				tmp[0] = home;
				//TODO 获取其他用户主目录
				path = (tmp.length == 2) ? tmp[0] + '/' + tmp[1] : tmp[0];
			}
		}
		File file = new File(path);
		current = file.getAbsolutePath();
		isDirectory = file.isDirectory();
		char i0 = current.charAt(0);
		isUnixPath = (i0 == '/');
	}

	public Path parent() {
		return new Path(parentStr());
	}
	public String parentStr() {
		int index = current.lastIndexOf('/');
		if (index == 0 && isUnixPath || index == -1) return current;
		return current.substring(0, index - 1);
	}

	public String current() {
		return current;
	}

	public Path child(String name) throws FileNotFoundException {
		return new Path(childStr(name));
	}
	public String childStr(String name) throws FileNotFoundException {
		String path = current + '/' + name;
		File file = new File(path);
		if (!(isDirectory && file.exists())) throw new FileNotFoundException();
		return path;
	}

	public Path relative(String path) throws FileNotFoundException {
		return new Path(relativeStr(path));
	}
	public String relativeStr(String path) throws FileNotFoundException {
		int index = 0;
		while (true) {
			char c = path.charAt(index);
			//TODO 循环解析字符串
			switch (c) {
				case '.':
				case ':':
			}
		}
	}

	@Override
	public String toString() {
		try {
			return canonicalize(current);
		} catch (IOException ignore) {
			return "";
		}
	}

	public static String canonicalize(String path) throws IOException {
		return new File(path).getCanonicalPath();
	}
}
