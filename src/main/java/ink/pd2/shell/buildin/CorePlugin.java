package ink.pd2.shell.buildin;

import ink.pd2.psh.util.*;
import ink.pd2.shell.Main;
import ink.pd2.shell.api.*;
import ink.pd2.shell.core.Logger;
import ink.pd2.shell.core.Resources;
import ink.pd2.shell.core.Shell;
import ink.pd2.shell.core.CommandEnteredListener;
import ink.pd2.shell.io.Output;
import ink.pd2.shell.util.PluginUtils;
import ink.pd2.shell.core.Property;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class CorePlugin extends Plugin {
	private static HashMap<String, ArrayList<Command>> commandList; //指令列表
	private static final HashMap<String, Plugin> pluginList = new HashMap<>(); //插件列表

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
		//获取jar所在目录
		String directory = new File(getJarPath()).getParent();
		//添加监听器
		api.listener.add((CommandEnteredListener) this::runCommandEvent);

		//TODO Other functions

		//加载插件
		if (Property.mode_load_module) {
			String path;
			if (Property.path_module_folder == null)
				path = directory + File.separator + "plugins";
			else
				path = Property.path_module_folder;

			File pluginDirectory = new File(path);
			if (pluginDirectory.exists())
				loadPlugins(pluginDirectory.getPath());
		}

		//加载基本指令
		loadBasicCommands();

		//加载测试功能
		loadTestingFunctions();

		newCommandList();
	}

	/* ================= */
	/* |<- 功能函数区 ->| */
	/* ================= */

	private void loadPlugins(String path) {
		try {
			Plugin[] plugins = PluginUtils.INS.load(path);
			for (Plugin i : plugins) {
				PluginUtils.INS.initObject(i);
				Resources.INS.addPlugin(i);
				pluginList.put(i.getResourcesId(), i);
			}
		} catch (InitializationException|PluginLoadingException e) {
			Logger.INS.writeException("Plugin.Init", e);
			Logger.INS.error("Plugin.Init", "Plugin initialization FAILED.");
			getApi().core.exit("Plugin initialization FAILED.");
		}
	}

	private void loadBasicCommands() {
		ParameterTemplate tpl_ls = new ParameterTemplate();
		Option ls_o_dir = new Option("directory", 'd');
		tpl_ls.addOption(ls_o_dir);
		tpl_ls.setDefaultOption(ls_o_dir);

		getApi().command.add(
				new Command("psh", "cd", (shell, parameter) -> {
					String path = parameter.getCommandLine();
					if (path.length() < 4) return 2;
					File dir;
					try {
						dir = new File(path.substring(3)).getCanonicalFile();
					} catch (IOException e) {
						Logger.INS.writeException("ChangeDirectory", e);
						return -1;
					}
					if (!dir.exists()) {
						shell.println("&color:red.null[Not found:]& " + dir.getPath());
						return 2;
					}
					if (!dir.isDirectory()) {
						shell.println("&color:red.null[Not a directory:]& " + dir.getPath());
						return 1;
					}
					shell.setDirectory(dir);
					return 0;
				}),
				new Command("psh", "ls", (shell, parameter) -> {
					try {
						ParsedParameter parsed = parameter.parseParameter(tpl_ls);
						String dir = parsed.getOptionValue(ls_o_dir);
						if (dir == null) dir = shell.getDirectory().getPath();
						Stream<Path> paths = Files.list(Paths.get(dir));
						paths.forEachOrdered(path -> {
							shell.printlnSimple(path.toFile().getName());
						});
					} catch (Exception e) {
						Logger.INS.writeException("List", e, true);
						return -1;
					}
					return 0;
				})
		);
	}

	private void loadTestingFunctions() {
		Resources.id.add("psh-lab");

		Command c1 = new Command("psh", "test.args", (shell, parameter) -> {
			String[] s = parameter.getArguments();
			for (int i = 0; i < s.length; i++) Main.println(i + " [" + s[i] + "]");
			return 0;
		});
		Command c2 = new Command("psh", "test.repeat", (shell, parameter) -> {
			shell.println("You chose the command from 'psh'.");
			return 0;
		});
		Command c3 = new Command("psh-lab", "test.repeat", (shell, parameter) -> {
			shell.println("You chose the command from 'psh-lab'.");
			return 1;
		});
		getApi().command.add(c1, c2, c3);
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
			ParameterTemplate template = temp.getTemplate();
			//处理参数
			if (template != null) {
				ParsedParameter p;
				try {
					p = parameter.parseParameter(template);
				} catch (ParameterException e) {
					getApi().log.writeException(e);
					int cause = e.getMessage().charAt(0) - 0x30;
					//TODO 处理参数错误
					return true;
				}
				//TODO 验证参数
				Option[] opsTmp = template.getAllOptions();
				for (Option i : opsTmp) if (i.required && !p.containsOption(i)) {
					shell.println("The value of option '" + i.name + "' is required.");
					shell.print("Please enter: ");
					String s = shell.readLine();
					p.addOption(i, s);
				}
			}
			try {
				final int[] returnCode = new int[1];
				Command finalTemp = temp;
				Thread thread = new Thread(() ->
						returnCode[0] = finalTemp.onExecute(shell, parameter));
				thread.setName(temp.getFullName() + thread.getId());
				thread.setUncaughtExceptionHandler((t, e) -> {
					getApi().log.writeException(new Exception(e), true);
					t.interrupt();
					returnCode[0] = -1;
				});
				thread.start();
				thread.join();
				shell.returnCode = returnCode[0];
			} catch (Exception e) {
				getApi().log.writeException(e);
			}
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
						String temp = api.console.inputNewLine(shell.input);
						builder.append(temp);
					} else {
						shell.println("&color:red.null[Resource ID '" + id + "' not found]&");
						return null; //返回null表示结束输入
					}
				} else break; //检查通过则返回
			} else {
				String temp = api.console.inputNewLine(shell.input);
				if (temp == null) return null; //返回null表示结束输入
				builder.append('\n').append(temp);
			}
		}

		return builder.toString();
	}

	private void printCommands(Output output, List<Command> commands) {
		for (int i = 0; i < commands.size(); i++) {
			output.println(i + 1 + ") " + commands.get(i).getFullName());
		}
	}

	//get & set
	public static HashMap<String, Plugin> getPluginList() {
		return pluginList;
	}

}
