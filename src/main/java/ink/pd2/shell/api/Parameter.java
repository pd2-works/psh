package ink.pd2.shell.api;

import java.util.LinkedList;

public class Parameter {
	//指令参数
	private final String command;
	private String name;
	private final String[] args;

	public Parameter(String command) {
		this.command = command;

		LinkedList<String> list = new LinkedList<>();
		String[] temp = {"", command};
		t: while (true) {
			temp = temp[1].split(" ", 2);
			switch (temp.length) {
				case 1:
					list.add(temp[0]);
					break t;
				case 2:
					list.add(temp[0]);
					if ((!temp[1].isEmpty())
							&& temp[1].startsWith("\"")
							&& temp[1].length() > 1) {
						String[] temp1 = temp[1].substring(1).split("\"", 2);
						list.add(temp1[0]);
						if (temp1.length == 2) {
							temp[1] = temp1[1];
						}
					}
			}
		}
		list.removeIf(String::isEmpty); //忽略所有多余空格
		args = list.toArray(new String[0]);

		name = list.size() == 0 ? null : list.get(0);
	}

	//get & set
	public String getCommandName() {
		return name;
	}
	public void setCommandName(String name) {
		this.name = name;
	}
	public String getCommand() {
		return command;
	}
	public String[] getArguments() {
		return args;
	}

	public ParsedParameter parseParameter() {
		return null;
	}

}
