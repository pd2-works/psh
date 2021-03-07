package ink.pd2.shell.io;

import ink.pd2.shell.core.Mark;
import ink.pd2.shell.core.Shell;

public abstract class Input {
	public abstract String readLine();
	public String readPassword() {
		return readLine();
	}
	public String newLine(String lPrompt, String rPrompt, Mark mark) {
		return readLine();
	}
	public String getCommand(Shell shell, Mark mark) {
		return readLine();
	}
}
