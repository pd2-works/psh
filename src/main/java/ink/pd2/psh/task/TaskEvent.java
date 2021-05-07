package ink.pd2.psh.task;

public class TaskEvent {
	private final Task task;
	private String info;

	public TaskEvent(Task task) {
		this.task = task;
	}

	//get & set
	public Task getCurrentTask() {
		return task;
	}
	public void setInfo(String info) {
		this.info = info;
		//TODO Event Listener
	}
}
