package ink.pd2.shell.core;

import ink.pd2.shell.Main;

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
		return Main.getVariableValue("user");
	}

	//分优先级的指令执行事件
	private final ArrayList<ArrayList<CommandExecutedListener>> listeners
			= new ArrayList<>(5);

	public Shell() {
		listeners.add(new ArrayList<>(0));
		listeners.add(new ArrayList<>(0));
		listeners.add(new ArrayList<>(1));
		Logger.INS.info("Shell", Resources.INS.getString("psh.log-init-shell"));
		//将事件按优先级排序
		List<Listener> ls = Resources.INS.getListeners("psh", "command-executed");
		for (Listener li : ls) {
			CommandExecutedListener l = (CommandExecutedListener) li;
			listeners.get(l.getPriority()).add(l);
		}
		try {
			setDirectory(new File(Main.getVariableValue("current_dir")).getCanonicalFile());
		} catch (IOException e) {
			Logger.INS.writeException("Shell.init", e);
		}
	}

	public void run() {
		thread = Thread.currentThread();
		shell:
		while (true) {
			//TODO 层级环境变量
			//更新变量值
			user = getCurrentUser();
			try {
				Main.putVariable("current_dir", dir.getCanonicalPath());
			} catch (IOException e) {
				Logger.INS.writeException("Shell", e);
			}
			Main.putVariable("current_folder", dir.getName());
			//指令处理和执行
			String c = Main.input.getCommand(this);
			event:
			for (ArrayList<CommandExecutedListener> ls : listeners)
				for (CommandExecutedListener l : ls) {
					Boolean b = l.event(this, c);
					if (b == null) break event;
					if (!b) break shell;
				}

		}
		//退出事件

	}

}
