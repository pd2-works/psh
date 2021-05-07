package ink.pd2.psh.task;

import ink.pd2.psh.core.Event;

public interface TaskInfoChangedEvent extends Event {
	@Override
	default String getId() {
		return "psh.task-info-changed";
	}

	void runEvent(Task task, String info);
}
