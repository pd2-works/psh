package ink.pd2.shell.io;

import org.fusesource.jansi.AnsiConsole;
import org.fusesource.jansi.AnsiPrintStream;

public class ConsoleOutput extends Output {
	private final AnsiPrintStream stream = AnsiConsole.out();

	@Override
	public void print(char c) {
		stream.print(c);
	}

	@Override
	public void print(String s) {
		stream.print(s);
	}

	@Override
	public void println(String s) {
		stream.println(s);
	}
}
