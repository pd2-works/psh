package ink.pd2.shell.api;

import ink.pd2.shell.core.CommandParameter;
import ink.pd2.shell.core.Shell;

import java.util.Arrays;
import java.util.HashSet;

public class Command {
	private final String name; //指令名
	private final String group; //组名

	private final HashSet<CommandEvent> events = new HashSet<>(); //指令执行事件

	public Command(String group, String name) {
		this.group = group;
		this.name = name;
	}
	public Command(String group, String name, CommandEvent... events) {
		this.group = group;
		this.name = name;
		if (events.length != 0) this.events.addAll(Arrays.asList(events));
	}

	public void onExecute(Shell shell, CommandParameter parameter) {
		for (CommandEvent event : events) {
			event.run(shell, parameter);
		}
	}

	//添加事件
	public void addEvent(CommandEvent event) {
		events.add(event);
	}
	public void addEvent(CommandEvent... events) {
		this.events.addAll(Arrays.asList(events));
	}
	//移除事件
	public void removeEvent(CommandEvent event) {
		events.remove(event);
	}

	//get & set
	public String getName() {
		return name;
	}
	public String getGroup() {
		return group;
	}
}
