package ink.pd2.shell;

import ink.pd2.shell.api.*;
import ink.pd2.shell.buildin.CorePlugin;
import ink.pd2.shell.buildin.Initializer;
import ink.pd2.shell.buildin.VariableMarkProvider;
import ink.pd2.shell.core.Logger;
import ink.pd2.shell.core.Mark;
import ink.pd2.shell.core.Shell;
import ink.pd2.shell.io.*;
import ink.pd2.shell.core.Resources;

import java.io.IOException;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;

public final class Main {
	//Shell对象
	private final static List<Shell> shells = new LinkedList<>();
	public static List<Shell> getShells() {
		return shells;
	}

	//I/O流
	public static Input input;
	public static Output output;

	//默认的输出打印方法
	public static void print(String s) {
		if (output != null) output.print(Mark.INS.update(s));
	}
	public static void println(String s) {
		if (output != null) output.println(Mark.INS.update(s));
	}

	//环境变量
	public static void putVariable(String key, String value) {
		VariableMarkProvider.INS.getVariables().put(key, value);
		Logger.INS.debug("Main<Variable>", key + " -> \"" + value + '"');
	}
	public static String getVariableValue(String key) {
		String value = VariableMarkProvider.INS.getVariables().get(key);
		Logger.INS.debug("Main<Variable>", "^ " + key + " : " + (value != null));
		return value;
	}
	public static String removeVariable(String key) {
		String value = VariableMarkProvider.INS.getVariables().remove(key);
		Logger.INS.debug("Main<Variable>", "- " + key + " : " + (value != null));
		return value;
	}

	/**
	 * <h2>mainProcess() | 主进程</h2>
	 *
	 * <p>Pd2 Shell 的主进程起始点.</p>
	 *
	 * <b>用法:</b>
	 *
	 * <p>主进程由 main() 方法自动执行, 若无特别需要, 切记:</p>
	 *
	 * <p><b>请勿尝试调用此方法!</b></p>
	 *
	 * <p><b>请勿尝试调用此方法!!</b></p>
	 *
	 * <p><b>请勿尝试调用此方法!!!</b></p>
	 *
	 * @author Maxel Black
	 * @since PSH 1.0
	 */

	public static void mainProcess() {
		Logger.INS.debug("Main[OBJECT]", "output: " + output);
		print(Resources.INS.getString("psh.shell-greet-text"));
		try {
			putVariable("user", System.getProperty("user.name"));
			putVariable("host", InetAddress.getLocalHost().getHostName());
			startShell(new Shell());
			//TODO 监听网络Shell
		} catch (Exception e) {
			Logger.INS.writeException("Main", e);
		}
	}

	/**
	 * <h2>startShell() | 运行新的 Shell 会话</h2>
	 *
	 * 用于在新的线程运行一个新的 Shell 会话
	 *
	 * @param shell 将要运行的 Shell 会话对象
	 *
	 * @see Shell
	 *
	 * @author Maxel Black
	 * @since PSH 1.0
	 */

	private static void startShell(Shell shell) {
		shells.add(shell);
		//TODO 把shell扔进另一个线程
		new Thread(shell::run).start();
	}

	public static void exit(int status, String reason) {
		System.out.println(Resources.INS.getString("psh.exit") + ": " + reason);
		//TODO 退出事件监听器
		System.exit(1);
	}

	/**
	 * <h2>main() | 程序入口</h2>
	 *
	 * @author Maxel Black
	 * @since PSH 1.0
	 */

	public static void main(String[] args) {
		parseArgument(args); //解析参数

		output = new ConsoleOutput(); //设置output流

		Initializer.INS.initMarks(); //初始化默认标记

		Resources.id.add("psh"); //添加核心资源组

		Logger.INS.debug("Main.PreInit", "Initialization started.");

		Initializer.INS.initResources(); //初始化资源
		Initializer.INS.initTheme(); //初始化主题

		try {
			input = new ConsoleInput(); //设置input流
		} catch (IOException e) {
			Logger.INS.writeException("ConsoleInput.init", e);
			Logger.INS.error("ConsoleInput.init",
					Resources.INS.getString("psh.log-error-init-jline"));
			Main.exit(1, Resources.INS.getString("psh.log-error-init-jline"));
		}

		//加载核心插件和API
		CorePlugin core = new CorePlugin();
		try {
			core.init();
		} catch (InitializationException e) {
			Logger.INS.writeException("Main.PreInit", e);
		}

		//TODO 判断是否有另一个psh进程正在运行

		Logger.INS.debug("Main.PreInit", "Initialization finished.");

		//主进程
		mainProcess();
	}

	private static void parseArgument(String[] args) {
		Parameter parameter = new Parameter(args);

		Option op_debug = new Option("debug", null, "false");
		Option op_log = new Option("log", 'l', "inf");
		Option op_directory = new Option("directory", 'd', ".");
		ParameterTemplate template = new ParameterTemplate(op_debug, op_log, op_directory);

		ParsedParameter parsed;
		try {
			parsed = parameter.parseParameter(template);
		} catch (ParameterException e) {
			parsed = null;
			e.printStackTrace();
			System.exit(1);
		}

		String s_debug = parsed.getOptionValue(op_debug);
		String s_log = parsed.getOptionValue(op_log);
		String s_directory = parsed.getOptionValue(op_directory);

		if (s_debug != null) Logger.INS.isDebug = Boolean.parseBoolean(s_debug);
		if (s_log != null) switch (s_log) {
			case "inf": Logger.INS.inf = output;
			case "err": Logger.INS.err = output;
		}
		if (s_directory != null) {
			VariableMarkProvider.INS.getVariables().put("current_dir", s_directory);
		} else {
			VariableMarkProvider.INS.getVariables().put("current_dir", ".");
		}
	}
}
