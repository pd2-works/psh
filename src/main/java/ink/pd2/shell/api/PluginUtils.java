package ink.pd2.shell.api;

import ink.pd2.shell.buildin.CorePlugin;
import ink.pd2.shell.core.Listener;
import ink.pd2.shell.core.Resources;

import java.util.HashMap;
import java.util.List;

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
	public final static ShellUtils shell = new ShellUtils();

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

		public Command remove(Command c) {
			return Resources.INS.removeCommand(
					plugin.getResourcesGroup() + '.' + c.getName());
		}
	}

	//监听器相关
	public class ListenerUtils {
		public void registerType(String type) {
			Resources.INS.registerListenerType(plugin.getResourcesGroup(), type);
		}

		public List<Listener> unregisterType(String type) {
			return Resources.INS.unregisterListenerType(type);
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

	//Shell相关
	public static final class ShellUtils {
		public int newCommandList() {
			return CorePlugin.newCommandList();
		}

	}

	//字符串相关位于Plugin类

}
