package ink.pd2.shell.io;

import ink.pd2.shell.Main;
import ink.pd2.shell.core.Logger;
import ink.pd2.shell.core.Mark;
import ink.pd2.shell.core.Resources;
import ink.pd2.shell.core.Shell;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.nio.charset.Charset;

public class ConsoleInput extends Input {
	private final LineReader reader;

	public ConsoleInput() {
		Logger.INS.info("ConsoleInput.init",
				Resources.INS.getString("psh.log-init-jline-reader"));
		//初始化 JLine LineReader
		LineReader r;
		try {
			Terminal t = TerminalBuilder.builder().jansi(true).name("Pd2ShellTerminal")
					.jna(true).dumb(true).encoding(Charset.defaultCharset()).build();
			//TODO 处理自动补全
			r = LineReaderBuilder.builder().appName("Pd2Shell").terminal(t).build();
		} catch (IOException e) {
			r = null;
			Logger.INS.writeException("ConsoleInput.init", e);
			Main.exit("JLine Initialization FAILED.");
		}
		reader = r;
	}

	@Override
	public String readLine() {
		return reader.readLine();
	}

	@Override
	public String getCommand(Shell shell) {
		return reader.readLine(
				Mark.INS.update(Resources.INS.getString("psh.shell-prompt-text-left")),
				Mark.INS.update(Resources.INS.getString("psh.shell-prompt-text-right")),
				(Character) null, null);
	}
}
