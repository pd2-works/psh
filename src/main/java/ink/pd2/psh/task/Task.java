package ink.pd2.psh.task;

public interface Task {
	default void run(TaskEvent event) {
		boolean b;
		do {
			b = runEvent(event);
		} while (b);
	}

	default String getName() {
		return null;
	}

	boolean runEvent(TaskEvent event);
}
