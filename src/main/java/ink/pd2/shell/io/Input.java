package ink.pd2.shell.io;

import ink.pd2.shell.core.Mark;
import ink.pd2.shell.core.Shell;

public abstract class Input {
	public abstract String readLine();
	public String readPassword() {
		return readLine();
	}
	public String nextCommandLine(String lPrompt, String rPrompt, Mark mark) {
		return readLine();
	}
	public String getCommandLine(Shell shell, Mark mark) {
		return readLine();
	}
}
