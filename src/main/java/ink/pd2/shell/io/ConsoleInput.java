package ink.pd2.shell.io;

import ink.pd2.shell.Main;
import ink.pd2.shell.core.Logger;
import ink.pd2.shell.core.Mark;
import ink.pd2.shell.core.Resources;
import ink.pd2.shell.core.Shell;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.nio.charset.Charset;

public class ConsoleInput extends Input {
	private final LineReader reader;

	public ConsoleInput() throws IOException {
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
	}

	@Override
	public String readLine() {
		return reader.readLine();
	}

	@Override
	public String getCommand(Shell shell, Mark mark) {
		if (mark == null) mark = Mark.INS;
		try {
			return reader.readLine(
					mark.update(Resources.INS.getString("psh.shell-prompt-text-left")),
					mark.update(Resources.INS.getString("psh.shell-prompt-text-right")),
					(Character) null, null);
		} catch (UserInterruptException e) {
			return null;
		}
	}

	@Override
	public String newLine(String lPrompt, String rPrompt, Mark mark) {
		if (mark == null) mark = Mark.INS;
		return reader.readLine(mark.update(lPrompt),
				mark.update(rPrompt), (Character) null, null);
	}
}
