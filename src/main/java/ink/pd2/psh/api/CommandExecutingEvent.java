package ink.pd2.psh.api;

import ink.pd2.psh.core.Event;

public interface CommandExecutingEvent extends Event {
	@Override
	default String getId() {
		return "psh.command-executing";
	}

	int run() throws Exception;
}
