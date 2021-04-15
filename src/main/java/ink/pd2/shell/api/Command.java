package ink.pd2.shell.api;

import ink.pd2.psh.util.Parameter;
import ink.pd2.psh.util.ParameterTemplate;
import ink.pd2.shell.Main;
import ink.pd2.shell.core.Shell;

import java.util.Arrays;
import java.util.HashSet;

public class Command {
	private final String name; //指令名
	private final String group; //组名

	private final HashSet<CommandEvent> events = new HashSet<>(); //指令执行事件
	private ParameterTemplate template; //指令参数模板

	public Command(String group, String name) {
		this(group, name, (ParameterTemplate) null, (CommandEvent) null);
	}
	public Command(String group, String name, CommandEvent... events) {
		this(group, name, null, events);
	}
	public Command(String group, String name, ParameterTemplate template, CommandEvent... events) {
		this.group = group;
		this.name = name;
		this.template = template;
		if (events != null && events.length != 0) this.events.addAll(Arrays.asList(events));
	}

	public int onExecute(Shell shell, Parameter parameter) {
		int returnCode = 0;
		Thread currentThread = Thread.currentThread();
		boolean isDefaultShell = shell == Main.defaultShell;
		if (isDefaultShell) Main.defaultShellRunningThread = currentThread;
		for (CommandEvent event : events) {
			int tmp = event.run(shell, parameter);
			if (tmp != 0) returnCode = tmp;
		}
		if (isDefaultShell) Main.defaultShellRunningThread = null;
		return returnCode;
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
	public String getFullName() {
		return group + ':' + name;
	}

	public ParameterTemplate getTemplate() {
		return template;
	}
	public void setTemplate(ParameterTemplate template) {
		this.template = template;
	}

}
