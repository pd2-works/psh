package ink.pd2.shell;

import ink.pd2.shell.core.Listener;
import ink.pd2.shell.core.Logger;
import ink.pd2.shell.core.Resources;
import ink.pd2.shell.plugin.CommandExecutedListener;

import java.util.ArrayList;

public class Shell {
	//常量
	public final static int DEFAULT_PRIORITY = 2;

	//Shell信息
	private String title = "Pd2 Shell";
	private String user;
	public String getUser() {
		return user;
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
		listeners.add(new ArrayList<>(3));
		listeners.add(new ArrayList<>(0));
		listeners.add(new ArrayList<>(1));
		Logger.INS.writeLog("Shell", Resources.INS.getString("psh.log-init-shell"));
		//将事件按优先级排序
		Listener[] ls = Resources.INS.getListeners("psh", "command-executed");
		for (Listener li : ls) {
			CommandExecutedListener l = (CommandExecutedListener) li;
			listeners.get(l.getPriority()).add(l);
		}
		user = getCurrentUser();
	}

	public void run() {
		shell:
		while (true) {
			//指令处理和执行
			String c = Main.input.getCommand();
			for (ArrayList<CommandExecutedListener> ls : listeners)
				for (CommandExecutedListener l : ls) {
				if (!
					l.event(this, c)
				) break shell;
			}

		}
		//退出事件

	}

}
