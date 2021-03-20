package ink.pd2.shell.api;

import ink.pd2.shell.core.Shell;

public interface CommandEvent {
	void run(Shell shell, Parameter parameter);
}
