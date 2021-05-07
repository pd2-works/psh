package ink.pd2.psh.task;

import java.util.LinkedHashSet;

public final class TaskBoard {
	private LinkedHashSet<TaskEvent> taskSet = new LinkedHashSet<>();

	public void runTask(Task task) {
		event(task);
	}

	private void event(Task task) {
		TaskEvent event = new TaskEvent(task);
		taskSet.add(event);
		task.run(event);
		taskSet.remove(event);
	}

	public TaskEvent[] getRunningEvents() {
		return taskSet.toArray(new TaskEvent[0]);
	}
}
