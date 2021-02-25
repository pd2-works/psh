package ink.pd2.shell.api;

import ink.pd2.shell.core.Resources;

public class PluginUtils {
	private final Plugin plugin;

	//初始化
	private PluginUtils() {
		plugin = null;
	}

	//初始化
	protected PluginUtils(Plugin plugin) {
		this.plugin = plugin;
	}

	//TODO 插件API
	//API方法
	public void addCommand(Command c) {
		assert plugin != null;
//		Resources.INS.putCommand(plugin.getResourcesGroup(), c); TODO
	}
	public void removeCommand(Command c) {

	}

}
