package ink.pd2.shell.buildin;

import ink.pd2.shell.Main;
import ink.pd2.shell.api.*;
import ink.pd2.shell.core.Logger;
import ink.pd2.shell.core.Resources;
import ink.pd2.shell.core.Shell;
import ink.pd2.shell.core.CommandEnteredListener;
import ink.pd2.shell.util.ConsoleUtils;
import ink.pd2.shell.util.PluginUtils;

import java.io.File;
import java.util.*;

public class CorePlugin extends Plugin {
	private static HashMap<String, ArrayList<Command>> commandList;

	public static int newCommandList() {
		commandList = new HashMap<>();

		Collection<Command> commands = Resources.INS.getCommandMap().values();
		for (Command c : commands) {
//			String name = c.getName();
			ArrayList<Command> list =
					commandList.computeIfAbsent(c.getName(), k -> new ArrayList<>(1));
			list.add(c);
		}
		return commands.size();
	}

	public CorePlugin() {
		super("psh", 115,
				Resources.INS.getString("psh.name"),
				Resources.INS.getString("psh.description"));
		setVersionName(Resources.INS.getString("psh.version"));
	}

	@Override
	public void init(PluginInterface api) {
		String directory = api.core.getJarDirectory();
		api.listener.add((CommandEnteredListener) this::runCommandEvent);

		api.command.add("test.args", (shell, parameter) -> {
			String[] s = parameter.getArguments();
			for (int i = 0; i < s.length; i++) {
				Main.println(i + " [" + s[i] + "]");
			}
		});

		//TODO Other functions

		//加载插件
		loadPlugins(directory + File.separator + "plugins");

		newCommandList();
	}

	private void loadPlugins(String path) {
		try {
			Plugin[] plugins = PluginUtils.INS.load(path);
			for (Plugin i : plugins) {
				PluginUtils.INS.initObject(i);
			}
		} catch (InitializationException |PluginLoadingException e) {
			Logger.INS.writeException("Plugin.Init", e);
			Logger.INS.error("Plugin.Init", "Plugin initialization FAILED.");
			getApi().core.exit("Plugin initialization FAILED.");
		}
	}

	private void printCommands(List<Command> commands) {

	}

	private Boolean runCommandEvent(Shell shell, String command) {
		if (command == null) return true;
		if (command.startsWith("exit")) {
			return false;
		}
		CommandParameter parameter = new CommandParameter(command);
		String c = parameter.getCommandName();
		if (c != null) {
			String[] s = c.split(":", 2);
			if (s.length == 1) {
				ArrayList<Command> commands = commandList.get(s[0]);
				if (commands == null) {
					shell.println("&color:red.null[Command not found.]&");
				} else {
					int l = commands.size();
					if (l == 1) {
						commands.get(0).onExecute(shell, parameter);
					} else {
						shell.println("There are more than one plugin providing command '"
								+ s[0] + "', which is your meaning?");
						for (Command i : commands) {
							shell.println(l + ") " + i.getGroup() + ':' + i.getName());
						}
						int choice = ConsoleUtils.INS.choice(
								shell.input, null, 1, l) - 1;
						commands.get(choice).onExecute(shell, parameter);
					}
				}
			} else {
				if (s[1].isEmpty()) {
					s[1] = ConsoleUtils.INS.inputNewLine(shell.input);
				}
				ArrayList<Command> commands = commandList.get(s[1]);
				boolean groupNotFound = true;
				if (commands == null) {
					shell.println("&color:red.null[Command not found.]&");
					if (Resources.id.contains(s[0])) groupNotFound = false;
				} else for (Command i : commands) {
					if (i.getGroup().equals(s[0])) {
						i.onExecute(shell, parameter);
						groupNotFound = false;
						break;
					}
				}
				if (groupNotFound) shell.println("&color:red.null[Group not found.]&");
			}
		}
		//TODO 监听器
		return true;
	}

}
