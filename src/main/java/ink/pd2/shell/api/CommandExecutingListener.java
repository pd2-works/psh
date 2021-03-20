package ink.pd2.shell.api;

import ink.pd2.shell.core.Listener;

public interface CommandExecutingListener extends Listener {
	@Override
	default String getType() {
		return "command-executing";
	}
}
