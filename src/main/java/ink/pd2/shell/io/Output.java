package ink.pd2.shell.io;

public abstract class Output {
	public abstract void print(char c);
	public abstract void print(String s);
	public void println(String s) {
		print("$s\n");
	}
}
