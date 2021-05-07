package ink.pd2.psh.buildin;

import ink.pd2.psh.console.ShellConsole;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;

public class LocalShell extends ShellConsole {
	private final LineReader reader;
	private final LineReader passwordReader;
	private final Terminal terminal;
	private final PrintWriter writer;

	public LocalShell() throws IOException {
		LineReaderBuilder r;
		Terminal t = TerminalBuilder.builder()
				.jansi(true).jna(true).dumb(true)
				.name("Pd2 Shell Terminal")
				.encoding(Charset.defaultCharset())
				.build();
		//TODO 处理自动补全
		r = LineReaderBuilder.builder()
				.appName("Pd2 Shell")
				.terminal(t);
		reader = r.build();
		passwordReader = r.build();
		terminal = t;
		writer = t.writer();
	}

	@Override
	public void printf(String format, Object... args) {
		writer.printf(format, args);
	}

	@Override
	public void println(String str) {
		writer.println(str);
	}

	@Override
	public String readPassword() {
		return passwordReader.readLine('*');
	}

	@Override
	public String readLine(String prompt) {
		return reader.readLine(prompt);
	}

	@Override
	public String readPassword(String prompt) {
		return passwordReader.readLine(prompt, '*');
	}

	@Override
	public void print(String str) {
		writer.print(str);
	}

	@Override
	public String readLine() {
		return reader.readLine();
	}

	@Override
	protected String readCommand() {
		return null;
	}
}
