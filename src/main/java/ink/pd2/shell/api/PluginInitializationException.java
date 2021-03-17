package ink.pd2.shell.api;

public class PluginInitializationException extends Exception {
	public PluginInitializationException(String message) {
		super(message);
	}

	public PluginInitializationException(String message, Throwable cause) {
		super(message, cause);
	}
}
