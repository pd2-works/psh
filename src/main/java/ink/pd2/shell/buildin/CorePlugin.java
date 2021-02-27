package ink.pd2.shell.buildin;

import ink.pd2.shell.api.PluginUtils;
import ink.pd2.shell.core.Resources;
import ink.pd2.shell.core.Shell;
import ink.pd2.shell.core.CommandExecutedListener;
import ink.pd2.shell.api.Plugin;

public class CorePlugin extends Plugin {
	public CorePlugin() {
		super("psh", 13,
				Resources.INS.getString("psh.name"),
				Resources.INS.getString("psh.description"));
		setVersionName(Resources.INS.getString("psh.version"));
	}

	@Override
	public void init( PluginUtils utils) {
		utils.listener.add(new CommandExecutedListener() {
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
