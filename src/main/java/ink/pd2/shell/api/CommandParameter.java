package ink.pd2.shell.api;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.LinkedList;

public class CommandParameter {
	//指令参数
	private final String command;
	private final String name;
	private final String[] args;

	public CommandParameter(String command) {
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
		list.removeIf(String::isEmpty);
		args = list.toArray(new String[0]);

		name = list.size() == 0 ? null : list.get(0);
	}

	//get & set
	public String getCommandName() {
		return name;
	}
	public String getCommand() {
		return command;
	}
	public String[] getArguments() {
		return args;
	}

	public CommandLine parseParameter(Options options) throws ParseException {
		return new DefaultParser().parse(options, args);
	}

}
