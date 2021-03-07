package ink.pd2.shell.core;

import ink.pd2.shell.Main;
import ink.pd2.shell.buildin.VariableMark;
import ink.pd2.shell.buildin.VariableMarkProvider;

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

	//环境变量标记
	private VariableMark variables;
	public void putVariable(String key, String value) {
		variables.put(key, value);
		Logger.INS.debug("Shell@" + thread.getId() + "<Variable>",
				key + " -> " + value);
	}
	public String getVariableValue(String key) {
		String value = variables.get(key);
		Logger.INS.debug("Shell@" + thread.getId() + "<Variable>",
				"^ " + key + " : " + (value != null));
		return value;
	}
	public String removeVariable(String key) {
		String value = VariableMarkProvider.INS.getVariables().remove(key);
		Logger.INS.debug("Shell@" + thread.getId() + "<Variable>",
				"- " + key + " : " + (value != null));
		return value;
	}

	//Shell信息
	private Thread thread;
	public Thread getThread() {
		return thread;
	}

//	private String title = "Pd2 Shell";

	private String user;
	public String getUser() {
		return user;
	}

	private File dir;
	public File getDirectory() {
		return dir;
	}
	public void setDirectory(File file) {
		if (file.isDirectory()) dir = file;
		else dir = file.getParentFile();
	}

	public String getCurrentUser() {
		return getVariableValue("user");
	}

	//分优先级的指令执行事件
	private final ArrayList<ArrayList<CommandEnteredListener>> listeners
			= new ArrayList<>(5);

	public Shell() {
		listeners.add(new ArrayList<>(0));
		listeners.add(new ArrayList<>(0));
		listeners.add(new ArrayList<>(1));
		Logger.INS.info("Shell", Resources.INS.getString("psh.log-init-shell"));
		//将事件按优先级排序
		List<Listener> ls = Resources.INS.getListeners("psh.command-entered");
		for (Listener li : ls) {
			CommandEnteredListener l = (CommandEnteredListener) li;
			listeners.get(l.getPriority()).add(l);
		}
		try {
			setDirectory(new File(getVariableValue("current_dir")).getCanonicalFile());
		} catch (IOException e) {
			Logger.INS.writeException("Shell.init", e);
		}
	}

	public void run() {
		thread = Thread.currentThread();
		Logger.INS.info(getLogLocation(), "A new shell started.");

		//CLI启动
		shell:
		while (true) {
			//TODO 层级环境变量
			//更新变量值
			user = getCurrentUser();
			try {
				putVariable("current_dir", dir.getCanonicalPath());
				putVariable("current_folder", dir.getCanonicalFile().getName());
			} catch (IOException e) {
				Logger.INS.writeException(getLogLocation(), e);
			}
			//指令处理和执行
			String c = Main.input.getCommand(this, variables);

			event:
			for (ArrayList<CommandEnteredListener> ls : listeners)
				for (CommandEnteredListener l : ls) {
					Boolean b = l.event(this, c);
					if (b == null) break event;
					if (!b) break shell;
				}

		}
		//退出事件

	}

	private String getLogLocation() {
		return "Shell@" + thread.getId();
	}

}
