package ink.pd2.shell.buildin;

import ink.pd2.shell.core.Shell;
import ink.pd2.shell.core.CommandExecutedListener;
import ink.pd2.shell.plugin.Plugin;

public class CorePlugin extends Plugin {
	public CorePlugin() {
		super("psh", "Pd2 Shell",13 ,
				"The core commands and options.");
	}

	@Override
	public void init() {
		new CommandExecutedListener() {
			@Override
			public int getPriority() {
				return Shell.DEFAULT_PRIORITY;
			}
			@Override
			public Boolean event(Shell shell, String command) {
				return null;
			}
		};
	}
}
