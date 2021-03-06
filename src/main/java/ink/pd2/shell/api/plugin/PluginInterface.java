package ink.pd2.shell.api.plugin;

import ink.pd2.shell.api.Command;
import ink.pd2.shell.api.CommandEvent;
import ink.pd2.shell.buildin.CorePlugin;
import ink.pd2.shell.core.Listener;
import ink.pd2.shell.core.Logger;
import ink.pd2.shell.core.Resources;

import java.util.HashMap;
import java.util.List;

public class PluginInterface {
	private final Plugin plugin;

	//初始化
	protected PluginInterface(Plugin plugin) {
		this.plugin = plugin;
	}

	public final CommandUtils command = new CommandUtils();
	public final ListenerUtils listener = new ListenerUtils();
	public final static ShellUtils shell = new ShellUtils();
	public final static CorePlugin core = new CorePlugin();

	//API方法 TODO 插件API

	//==================
	//| <- 资源管理 ->  |
	//==================

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

	//字符串相关位于Plugin类

	//==================
	//| <- 系统操作 ->  |
	//==================

	//Shell相关
	public static final class ShellUtils {
		public int newCommandList() {
			return CorePlugin.newCommandList();
		}

	}

	//核心相关
	public static final class CoreUtils {

	}

	//日志相关
	public final class LogUtils {
		public void debug(String message) {
			Logger.INS.debug("Plugin:" + plugin.getResourcesGroup(), message);
		}
		public void info(String message) {
			Logger.INS.info("Plugin:" + plugin.getResourcesGroup(), message);
		}
		public void error(String message) {
			Logger.INS.error("Plugin:" + plugin.getResourcesGroup(), message);
		}

		public void writeException(Exception exception) {
			Logger.INS.writeException("Plugin:" + plugin.getResourcesGroup(), exception);
		}
		public void printException(Exception exception) {
			//TODO 输出错误
		}
	}

}
