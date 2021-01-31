package ink.pd2.shell.io;

public abstract class Input {
	public abstract String readLine();
	public String readPassword() {
		return readLine();
	}
	public String newLine(String lPrompt, String rPrompt) {
		return readLine();
	}
	public String getCommand() {
		return readLine();
	}
}
