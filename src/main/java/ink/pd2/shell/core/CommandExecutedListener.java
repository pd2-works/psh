package ink.pd2.shell.core;

public interface CommandExecutedListener extends ink.pd2.shell.core.Listener {
	int getPriority();
	Boolean event(Shell shell, String command);
}
