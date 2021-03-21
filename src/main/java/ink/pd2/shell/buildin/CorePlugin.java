package ink.pd2.shell.buildin;

import ink.pd2.shell.Main;
import ink.pd2.shell.api.*;
import ink.pd2.shell.core.Logger;
import ink.pd2.shell.core.Resources;
import ink.pd2.shell.core.Shell;
import ink.pd2.shell.core.CommandEnteredListener;
import ink.pd2.shell.io.Output;
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

		//TODO Other functions

		//加载插件
//		loadPlugins(directory + File.separator + "plugins");

		//加载测试功能
		loadTestingFunctions();

		newCommandList();
	}

	private void loadPlugins(String path) {
		try {
			Plugin[] plugins = PluginUtils.INS.load(path);
			for (Plugin i : plugins) {
				PluginUtils.INS.initObject(i);
			}
		} catch (InitializationException|PluginLoadingException e) {
			Logger.INS.writeException("Plugin.Init", e);
			Logger.INS.error("Plugin.Init", "Plugin initialization FAILED.");
			getApi().core.exit("Plugin initialization FAILED.");
		}
	}

	private void loadTestingFunctions() {
		Resources.id.add("psh-lab");

		Command c1 = new Command("psh", "test.args", (shell, parameter) -> {
			String[] s = parameter.getArguments();
			for (int i = 0; i < s.length; i++) {
				Main.println(i + " [" + s[i] + "]");
			}
		});
		Command c2 = new Command("psh", "test.repeat",
				(shell, parameter) -> shell.println("You chose the command from 'psh'."));
		Command c3 = new Command("psh-lab", "test.repeat",
				(shell, parameter) -> shell.println("You chose the command from 'psh-lab'."));
		getApi().command.add(c1, c2, c3);
	}

	private void printCommands(Output output, List<Command> commands) {
		for (int i = 0; i < commands.size(); i++) {
			output.println(i + 1 + ") " + commands.get(i).getFullName());
		}
	}

	private Boolean runCommandEvent(Shell shell, String command) {
		if (command == null ||
				command.isEmpty() ||
				(command = checkInput(command, shell)) == null ||
				command.startsWith(":")
		) return true;

		Parameter parameter = new Parameter(command);
		String commandName = parameter.getCommandName();

		//若指令名是exit则直接退出
		if (commandName.equals("exit")) return false;

		/* |<- 解析指令 ->| */

		Command temp = getApi().command.get(commandName);
		//若无该对象则检查指令列表
		if (temp == null) {
			String[] split = commandName.split(":", 2);
			if (split.length == 1) {
				//若无分隔符则检查同名指令
				ArrayList<Command> s = commandList.get(split[0]);
				if (s != null) {
					int l = s.size();
					if (l == 1) {
						//若只有一个指令则直接选择该指令
						temp = s.get(0);
					} else {
						//否则让用户选择
						shell.println("There are more than 1 plugins can provide this command, which do you mean?");
						printCommands(shell.output, s);
						int choice = getApi().console.choice(shell.input,
								shell.getVariableMark(), 1, l);
						temp = s.get(choice - 1);
					}
					parameter.setCommandName(temp.getFullName());
				}
			} else if (!Resources.id.contains(split[0])) {
				//若有分隔符则指令与资源组中一定至少有一个出了问题
				shell.println("&color:red.null[Resource ID '" + split[0] + "' not found]&");
			}
		}

		if (temp == null) {
			shell.println("&color:red.null[Command '" + commandName + "' not found]&");
		} else {
			temp.onExecute(shell, parameter);
		}

		//TODO 指令执行监听器
		return true;
	}

	private String checkInput(String input, Shell shell) {
		PluginInterface api = getApi();

		//检查输入完整性
		StringBuilder builder = new StringBuilder(input);
		while (true) {
			//检查字符串引号匹配
			int count = 0;
			for (char i : builder.toString().toCharArray()) if (i == '"') count++;
			if (count % 2 == 0) {
				//去除开头和结尾空格
				while (builder.charAt(0) == ' ') builder.deleteCharAt(0);
				int index;
				while (builder.charAt(index = (builder.length() - 1)) == ' ')
					builder.deleteCharAt(index);
				//若匹配则检查结尾字符串
				int lastIndex = builder.length() - 1;
				char last = builder.charAt(lastIndex);
				if (last == ':' && lastIndex != 0) {
					//以':'结尾: 资源组存在则继续输入, 不存在则提示
					String id = builder.substring(0, builder.length() - 1);
					if (Resources.id.contains(id)) {
						builder.append(api.console.inputNewLine(shell.input));
					} else {
						shell.println("&color:red.null[Resource ID '" + id + "' not found]&");
						return null; //返回null表示结束输入
					}
				} else break; //检查通过则返回
			} else {
				builder.append('\n').append(api.console.inputNewLine(shell.input));
			}
		}

		return builder.toString();
	}

}
