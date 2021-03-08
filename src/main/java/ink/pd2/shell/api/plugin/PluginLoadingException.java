package ink.pd2.shell.api.plugin;

public class PluginLoadingException extends Exception {
	public PluginLoadingException(String message) {
		super(message);
	}
	public PluginLoadingException(String message, Throwable cause) {
		super(message, cause);
	}
}
