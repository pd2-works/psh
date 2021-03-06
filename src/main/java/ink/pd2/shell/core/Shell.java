package ink.pd2.shell.core;

import ink.pd2.shell.Main;
import ink.pd2.shell.buildin.VariableMark;
import ink.pd2.shell.buildin.VariableMarkProvider;
import ink.pd2.shell.io.Input;
import ink.pd2.shell.io.Output;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Shell {
	//常量
	public final static int PRIORITY_HIGH = 0;
	public final static int PRIORITY_MEDIUM = 1;
	public final static int PRIORITY_LOW = 2;
	public final static int DEFAULT_PRIORITY = PRIORITY_MEDIUM;

	//命令提示
	private String lPrompt, rPrompt;
	public String getPrompt() {
		return lPrompt;
	}
	public String[] getPrompts() {
		return new String[]{lPrompt, rPrompt};
	}
	public void setPrompt(String L, String R) {
		lPrompt = L;
		rPrompt = R;
	}

	//环境变量标记
	private VariableMark variables;
	public void putVariable(String key, String value) {
		variables.put(key, value);
		Logger.INS.debug("Shell@" + thread.getId() + "<Variable>",
				"&nomark&" + key + " -> \"" + value + '"');
	}
	public String getVariableValue(String key) {
		String value = variables.get(key);
		Logger.INS.debug(getLogLocation() + "<Variable>",
				"&nomark&^ " + key + " : " + (value != null));
		return value;
	}
	public String removeVariable(String key) {
		String value = VariableMarkProvider.INS.getVariables().remove(key);
		Logger.INS.debug(getLogLocation() + "<Variable>",
				"&nomark&- " + key + " : " + (value != null));
		return value;
	}
	public VariableMark getVariableMark() {
		return variables;
	}

	//Shell信息
	private Thread thread; //Shell所处线程
	public Thread getThread() {
		return thread;
	}

//	private String title = "Pd2 Shell";

	private String user; //实时用户
	public String getUser() {
		return user;
	}

	private File dir; //当前目录
	public File getDirectory() {
		return dir;
	}
	public void setDirectory(File file) {
		if (file.isDirectory()) dir = file;
		else dir = file.getParentFile();
	}

	public int returnCode = 0; //最近一次返回值

	//基本输入输出
	public void print(String s) {
		printSimple(Mark.INS.update(s));
	}
	public void println(String s) {
		printlnSimple(Mark.INS.update(s));
	}
	public void printSimple(String s) {
		output.print(s);
	}
	public void printlnSimple(String s) {
		output.println(s);
	}
	public String readLine() {
		return input.readLine();
	}

	public Input input = Main.input;
	public Output output = Main.output;

	//功能和特性
	public String getCurrentUser() {
		return getVariableValue("user");
	}

	//分优先级的指令执行事件
	private final ArrayList<ArrayList<CommandEnteredListener>> listeners
			= new ArrayList<>(5);

	private void init() {
		listeners.add(new ArrayList<>(0));
		listeners.add(new ArrayList<>(0));
		listeners.add(new ArrayList<>(1));
		Logger.INS.info("Shell.init", Resources.INS.getString("psh.log-init-shell"));
		//将事件按优先级排序
		List<Listener> ls = Resources.INS.getListeners("psh.command-entered");
		for (Listener li : ls) {
			CommandEnteredListener l = (CommandEnteredListener) li;
			listeners.get(l.getPriority()).add(l);
		}
		try {
			String lp = Resources.INS.getString("psh.shell-prompt-text-left");
			String rp = Resources.INS.getString("psh.shell-prompt-text-right");
			setPrompt(lp, rp);
			setDirectory(new File(getVariableValue("current_dir")).getCanonicalFile());
		} catch (IOException e) {
			Logger.INS.writeException("Shell.init", e);
		}
	}

	public void run() {
		run(VariableMarkProvider.INS.getVariables().newChild());
	}
	public final void run(VariableMark variables) {
		thread = Thread.currentThread();
		thread.setName(getLogLocation());

		this.variables = variables;
		init();

		Logger.INS.info(getLogLocation(), "A new shell started.");

		//CLI启动
		while (true) {
			//更新变量值
			user = getCurrentUser();
			try {
				putVariable("current_dir", dir.getCanonicalPath());
				File temp = dir.getCanonicalFile();
				putVariable("current_folder", temp.getPath().equals("/")? "/" : temp.getName());
			} catch (IOException e) {
				Logger.INS.writeException(getLogLocation(), e);
			}
			putVariable("return", String.valueOf(returnCode));
			//指令处理和执行
			String c = input.getCommandLine(this, variables);

			if (!onCommandEntered(c)) break;

		}
		//退出事件

		Logger.INS.info(getLogLocation(), "The shell on '" + user + "' exit.");
	}

	public Boolean onCommandEntered(String command) {
		Logger.INS.debug(getLogLocation() + "<CommandLine>", command);

		Boolean b = true;
		event:
		for (ArrayList<CommandEnteredListener> ls : listeners)
			for (CommandEnteredListener l : ls) {
				b = l.event(this, command);
				if (b == null || !b) break event;
			}

		return b;
	}

	public String getLogLocation() {
		return "Shell@" + thread.getId();
	}

}
