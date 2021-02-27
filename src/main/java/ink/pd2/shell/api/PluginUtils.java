package ink.pd2.shell.api;

import ink.pd2.shell.core.Listener;
import ink.pd2.shell.core.Resources;

import java.util.HashMap;

public class PluginUtils {
	private final Plugin plugin;

	//初始化
	protected PluginUtils(Plugin plugin) {
		this.plugin = plugin;
	}

	//API方法 TODO 插件API

	//==================
	//| <- 资源管理 ->  |
	//==================

	public final CommandUtils command = new CommandUtils();
	public final ListenerUtils listener = new ListenerUtils();

	//指令相关
	public class CommandUtils {
		public void add(Command c) {
			Resources.INS.putCommand(c);
		}

		public Command add(String name, CommandEvent event) {
			return new Command(plugin.getResourcesGroup(), name, event);
		}

		public Command remove(String commandName) {
			HashMap<String, Command> map = Resources.INS.getCommandMap();
			return map.remove(plugin.getResourcesGroup() + ':' + commandName);
		}

		public void remove(Command c) {
			Resources.INS.removeCommand(
					plugin.getResourcesGroup() + '.' + c.getName());
		}
	}

	//监听器相关
	public class ListenerUtils {
		public void registerType(String type) {
			Resources.INS.registerListenerType(plugin.getResourcesGroup(), type);
		}

		public boolean unregisterType(String type) {
			return Resources.INS.unregisterListenerType(type) != null;
		}

		public void add(String group, Listener listener) {
			Resources.INS.addListener(group, listener);
		}
		public void add(Listener listener) {
			Resources.INS.addListener("psh", listener);
		}

		public boolean remove(String group, Listener listener) {
			return Resources.INS.removeListener(group, listener);
		}
		public boolean remove(Listener listener) {
			return Resources.INS.removeListener("psh", listener);
		}
	}

	//字符串相关位于 Plugin 类

}
