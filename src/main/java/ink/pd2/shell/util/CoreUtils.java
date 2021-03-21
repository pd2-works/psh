package ink.pd2.shell.util;

import ink.pd2.shell.Main;
import ink.pd2.shell.buildin.CorePlugin;

import java.io.File;

public final class CoreUtils {
	private CoreUtils() {}
	public final static CoreUtils INS = new CoreUtils();

	public void exit(String reason) {
		Main.exit(1, reason);
	}

	public String getJarPath() {
		return new File(System.getProperty("java.class.path")).getAbsolutePath();
	}
	public String getJarDirectory() {
		return new File(getJarPath()).getParent();
	}

	public int newCommandList() {
		return CorePlugin.newCommandList();
	}
}
