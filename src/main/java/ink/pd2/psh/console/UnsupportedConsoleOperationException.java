package ink.pd2.psh.console;

public class UnsupportedConsoleOperationException extends ConsoleException {

	public UnsupportedConsoleOperationException() {
		super("The operation is not supported by current console.");
	}
}
