package ink.pd2.shell.api;

import ink.pd2.shell.core.Shell;

public interface CommandEvent {
	int run(Shell shell, Parameter parameter);
}
