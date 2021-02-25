package ink.pd2.shell.buildin;

import ink.pd2.shell.api.PluginUtils;
import ink.pd2.shell.core.Resources;
import ink.pd2.shell.core.Shell;
import ink.pd2.shell.core.CommandExecutedListener;
import ink.pd2.shell.api.Plugin;

public class CorePlugin extends Plugin {
	public CorePlugin() {
		super("psh", "Pd2 Shell",13 ,
				"The core commands, options and APIs.");
	}

	@Override
	public void init(PluginUtils utils) {
		Resources.INS.addListener("psh", new CommandExecutedListener() {
			@Override
			public int getPriority() {
				return Shell.DEFAULT_PRIORITY;
			}
			@Override
			public Boolean event(Shell shell, String command) {
				//TODO 解析指令
				return true;
			}
		});
	}
}
