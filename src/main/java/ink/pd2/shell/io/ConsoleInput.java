package ink.pd2.shell.io;

import ink.pd2.shell.Main;
import ink.pd2.shell.core.Logger;
import ink.pd2.shell.core.Mark;
import ink.pd2.shell.core.Resources;
import ink.pd2.shell.core.Shell;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Scanner;

public class ConsoleInput extends Input {
	private final LineReader reader;
	private final Scanner scanner;

	public ConsoleInput() throws IOException, EndOfFileException {
		Logger.INS.info("ConsoleInput.init",
				Resources.INS.getString("psh.log-init-jline-reader"));
		//初始化 JLine LineReader
		LineReader r;
		Terminal t = TerminalBuilder.builder()
				.jansi(true).jna(true).dumb(true)
				.name("Pd2ShellTerminal")
				.encoding(Charset.defaultCharset())
				.build();
		//TODO 处理自动补全
		r = LineReaderBuilder.builder()
				.appName("Pd2Shell")
				.terminal(t)
				.build();
		reader = r;

		//初始化 Scanner
		scanner = new Scanner(System.in);
	}

	@Override
	public String readLine() {
		try {
			return scanner.nextLine();
		} catch (IllegalStateException e) {
			Logger.INS.writeException("ConsoleInput.readLine", e);
			return null;
		}
	}

	@Override
	public String getCommandLine(Shell shell, Mark mark) {
		if (mark == null) mark = Mark.INS;
		try {
			return reader.readLine(
					mark.update(Resources.INS.getString("psh.shell-prompt-text-left")),
					mark.update(Resources.INS.getString("psh.shell-prompt-text-right")),
					(Character) null, null);
		} catch (UserInterruptException ignore) {
		} catch (EndOfFileException e) {
			Logger.INS.error("ConsoleInput.getCommandLine",
					"Console input stream was closed forcibly.");
			Main.exit(1, "Force stopped");
		}
		return null;
	}

	@Override
	public String nextCommandLine(String lPrompt, String rPrompt, Mark mark) {
		if (mark == null) mark = Mark.INS;
		try {
			return reader.readLine(mark.update(lPrompt),
					mark.update(rPrompt), (Character) null, null);
		} catch (UserInterruptException ignore) {
		} catch (EndOfFileException e) {
			Logger.INS.error("ConsoleInput.nextCommandLine",
					"Console input stream was closed forcibly.");
			Main.exit(1, "Force stopped");
		}
		return null;
	}
}
