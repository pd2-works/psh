package ink.pd2.shell.api;

import ink.pd2.shell.core.Listener;
import ink.pd2.shell.core.Logger;
import ink.pd2.shell.core.Resources;
import ink.pd2.shell.util.ConsoleUtils;
import ink.pd2.shell.util.CoreUtils;
import ink.pd2.shell.util.ShellUtils;

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
	public final LogUtils log = new LogUtils();
	public final ShellUtils shell = ShellUtils.INS;
	public final CoreUtils core = CoreUtils.INS;
	public final ConsoleUtils console = ConsoleUtils.INS;

	//API方法 TODO 插件API

	//==================
	//| <- 资源管理 ->  |
	//==================

	//指令相关
	public final class CommandUtils {
		public void add(Command... command) {
			Resources.INS.putCommand(command);
		}

		public Command add(String name, CommandEvent event) {
			Command command = new Command(plugin.getResourcesId(), name, event);
			Resources.INS.putCommand(command);
			return command;
		}

		public Command remove(String commandName) {
			HashMap<String, Command> map = Resources.INS.getCommandMap();
			return map.remove(plugin.getResourcesId() + ':' + commandName);
		}

		public Command remove(Command c) {
			return Resources.INS.removeCommand(
					plugin.getResourcesId() + '.' + c.getName());
		}

		public Command get(String key) {
			return Resources.INS.getCommandMap().get(key);
		}
	}

	//监听器相关
	public final class ListenerUtils {
		public void registerType(String type) {
			Resources.INS.registerListenerType(plugin.getResourcesId(), type);
		}

		public List<Listener> unregisterType(String type) {
			return Resources.INS.unregisterListenerType(type);
		}

		public void add(String id, Listener listener) {
			Resources.INS.addListener(id, listener);
		}
		public void add(Listener listener) {
			Resources.INS.addListener("psh", listener);
		}

		public boolean remove(String id, Listener listener) {
			return Resources.INS.removeListener(id, listener);
		}
		public boolean remove(Listener listener) {
			return Resources.INS.removeListener("psh", listener);
		}
	}

	//字符串相关位于Plugin类

	//==================
	//| <- 系统操作 ->  |
	//==================

	//日志相关
	public final class LogUtils {
		public void debug(String message) {
			Logger.INS.debug("Plugin:" + plugin.getResourcesId(), message);
		}
		public void info(String message) {
			Logger.INS.info("Plugin:" + plugin.getResourcesId(), message);
		}
		public void error(String message) {
			Logger.INS.error("Plugin:" + plugin.getResourcesId(), message);
		}

		public void writeException(Exception exception) {
			Logger.INS.writeException("Plugin:" + plugin.getResourcesId(), exception);
		}
		public void printException(Exception exception) {
			//TODO 输出错误
		}
	}

}
