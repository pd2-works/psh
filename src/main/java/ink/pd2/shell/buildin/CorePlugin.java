package ink.pd2.shell.buildin;

import ink.pd2.shell.api.Command;
import ink.pd2.shell.api.CommandParameter;
import ink.pd2.shell.api.plugin.PluginInterface;
import ink.pd2.shell.core.Resources;
import ink.pd2.shell.core.Shell;
import ink.pd2.shell.core.CommandEnteredListener;
import ink.pd2.shell.api.plugin.Plugin;

import java.util.*;

public class CorePlugin extends Plugin {
	private static HashMap<String, ArrayList<Command>> commandList;

	public static int newCommandList() {
		Collection<Command> commands = Resources.INS.getCommandMap().values();
		for (Command c : commands) {
			String name = c.getName();
			ArrayList<Command> list =
					commandList.computeIfAbsent(name, k -> new ArrayList<>(1));
			list.add(c);
		}
		return commands.size();
	}

	public CorePlugin() {
		super("psh", 13,
				Resources.INS.getString("psh.name"),
				Resources.INS.getString("psh.description"));
		setVersionName(Resources.INS.getString("psh.version"));
	}

	@Override
	public void init(PluginInterface api) {
		api.listener.add(new CommandEnteredListener() {
			@Override
			public int getPriority() {
				return Shell.DEFAULT_PRIORITY;
			}
			@Override
			public Boolean event(Shell shell, String command) {
				CommandParameter parameter = new CommandParameter(command);

				//TODO 解析指令
				return true;
			}
		});


	}
}
