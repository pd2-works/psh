package ink.pd2.shell.api.plugin;

import java.io.IOException;

public class PluginInitializationException extends RuntimeException {
	public PluginInitializationException(String message) {
		super(message);
	}

	public PluginInitializationException(String message, Throwable cause) {
		super(message, cause);
	}
}
