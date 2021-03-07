package ink.pd2.shell.util;

import ink.pd2.shell.core.Mark;
import ink.pd2.shell.io.Input;
import ink.pd2.shell.io.Output;

public final class ConsoleUtils {
	public final static ConsoleUtils INS = new ConsoleUtils();

	public String inputNewLine(Input input) {
		return input.newLine("&color:green.null[>>]& ",
				" &color:green.null[<<]&", null);
	}
	public int choice(Output output, Input input, String... choices) {
		int l = choices.length;
		//TODO
		return 0;
	}

	public int choice(Input input, Mark mark, int start, int end) {
		int c;
		while (true) {
			String temp = input.newLine("Make a choice from "
					+ start + " to " + end + ": ", null, mark);
			try {
				c = Integer.parseInt(temp);
			} catch (NumberFormatException e) { continue; }
			if ((c >= start && c <= end)) break;
		}
		return c;
	}
}
