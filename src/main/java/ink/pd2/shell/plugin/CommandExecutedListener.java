package ink.pd2.shell.plugin;

import ink.pd2.shell.Shell;
import ink.pd2.shell.core.CommandParameter;

public interface CommandExecutedListener extends Listener {
	void event(Shell shell, String command);
}
