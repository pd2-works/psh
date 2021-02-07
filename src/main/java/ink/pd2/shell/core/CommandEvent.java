package ink.pd2.shell.core;

public interface CommandEvent {
	void run(Shell shell, CommandParameter parameter);
}
