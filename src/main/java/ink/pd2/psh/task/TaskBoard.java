package ink.pd2.psh.task;

import java.util.LinkedHashSet;

public final class TaskBoard {
	private final static LinkedHashSet<TaskEvent> taskSet = new LinkedHashSet<>();

	public static void runTask(Task task) {
		event(task);
	}

	private static void event(Task task) {
		TaskEvent event = new TaskEvent(task);
		taskSet.add(event);
		task.run(event);
		taskSet.remove(event);
	}

	public static TaskEvent[] getRunningEvents() {
		return taskSet.toArray(new TaskEvent[0]);
	}
}
