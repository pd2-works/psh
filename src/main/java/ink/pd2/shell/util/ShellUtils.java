package ink.pd2.shell.util;

import ink.pd2.shell.buildin.CorePlugin;

public final class ShellUtils {
	private ShellUtils() {}
	public final static ShellUtils INS = new ShellUtils();

	public int newCommandList() {
		return CorePlugin.newCommandList();
	}

}
