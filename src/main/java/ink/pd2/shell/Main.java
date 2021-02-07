package ink.pd2.shell;

import ink.pd2.shell.buildin.Initializer;
import ink.pd2.shell.buildin.VariableMarkProvider;
import ink.pd2.shell.core.Logger;
import ink.pd2.shell.core.Mark;
import ink.pd2.shell.core.Shell;
import ink.pd2.shell.io.*;
import ink.pd2.shell.core.Resources;

import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;

public final class Main {
	//Shell对象
	private final static List<Shell> shells = new LinkedList<>();

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
		VariableMarkProvider.INSTANCE.getVariables().put(key, value);
	}
	public static String getVariableValue(String key) {
		return VariableMarkProvider.INSTANCE.getVariables().get(key);
	}
	public static String removeVariable(String key) {
		return VariableMarkProvider.INSTANCE.getVariables().remove(key);
	}

	/**
	 * <h2>mainProcess() | 主进程</h2>
	 *
	 * Pd2 Shell 的主进程起始点.
	 *
	 * <h3>用法</h3>
	 *
	 * 主进程由 main() 方法自动执行, 若无特别需要, 切记:
	 *
	 * 请勿尝试调用此方法!
	 *
	 * 请勿尝试调用此方法!!
	 *
	 * 请勿尝试调用此方法!!!
	 */

	public static void mainProcess() {
		Logger.INS.writeDebugLog("Main[OBJECT]", "output: " + output);
		print(Resources.INS.getString("psh.shell-greet-text"));
		try {
			putVariable("user", System.getProperty("user.name"));
			putVariable("host", InetAddress.getLocalHost().getHostName());
//			startShell(new Shell());
			//TODO 监听网络Shell
		} catch (Exception e) {
			e.printStackTrace();
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
		shell.run();
	}

	public static void exit(String reason) {
		System.out.println(Resources.INS.getString("psh.exit") + ": " + reason);
	}

	/**
	 * <h2>main() | 程序入口</h2>
	 *
	 * @author Maxel Black
	 * @since PSH 1.0
	 */

	public static void main(String[] args) {
		output = new  ConsoleOutput(); //设置output流

		Initializer.INS.initMarks(); //初始化默认标记

		Logger.INS.writeDebugLog("Main.PreInit", "Initialization started.");

		Initializer.INS.initResources(); //初始化资源
		Initializer.INS.initTheme(); //初始化主题

		input = new ConsoleInput(); //设置input流

		//TODO 初始化插件

		//TODO 判断是否有另一个psh进程正在运行

		Logger.INS.writeDebugLog("Main.PreInit", "Initialization finished.");

		Logger.INS.writeLog("Main", "The shell on '&v:user&' exit.");
	}
}
