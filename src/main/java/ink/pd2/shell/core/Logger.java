package ink.pd2.shell.core;

import ink.pd2.shell.Main;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

public final class Logger {
	public static final Logger INS = new Logger();

	public void debug(String location, String message) {
		Main.println("[&color:blue.null[" + getDate(true) + "]&] [&color:yellow.null[D]&|&color:cyan.null[" + location + "]&] " + message);
	}

	public void info(String location, String message) {
		Main.println("[&color:blue.null[" + getDate(false) + "]&] [&color:green.null[I]&|&color:cyan.null[" + location + "]&] " + message);
	}

	public void error(String location, String message) {
		Main.println("[&color:blue.null[" + getDate(false) + "]&] [&color:red.null[E]&|&color:cyan.null[" + location + "]&] " + message);
	}

	public void writeException(String location, Exception exception) {
		StringWriter s = new StringWriter();
		PrintWriter p = new PrintWriter(s);
		exception.printStackTrace(p);
		debug(location, "&color:red.null[EXCEPTION:\n" + s + "]&");
	}

	private String getDate(boolean ms) {
		Date d = new Date();
		if (ms) return String.format("%tF %tT.%tL %tZ", d, d, d, d);
		return String.format("%tF %tT %tZ", d, d, d);
	}
}
