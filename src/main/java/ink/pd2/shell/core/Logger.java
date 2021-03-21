package ink.pd2.shell.core;

import ink.pd2.shell.Main;
import ink.pd2.shell.io.Output;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

public final class Logger {
	public static final Logger INS = new Logger();

	public Output inf = null;
	public Output err = null;
	public boolean isDebug = false;

	public void debug(String location, String message) {
		if (inf == null) return;
		if (isDebug)
			inf.println("[&color:blue.null["
					+ getDate(true)
					+ "]&] [&color:yellow.null[D]&|&color:cyan.null["
					+ location + "]&] " + message);
	}

	public void info(String location, String message) {
		if (inf == null) return;
		inf.println("[&color:blue.null["
				+ getDate(false)
				+ "]&] [&color:green.null[I]&|&color:cyan.null["
				+ location + "]&] " + message);
	}

	public void error(String location, String message) {
		if (err == null) return;
		err.println("[&color:blue.null["
				+ getDate(false)
				+ "]&] [&color:red.null[E]&|&color:cyan.null["
				+ location + "]&] &color:red.null[" + message + "]&");
	}

	public void writeException(String location, Exception exception) {
		StringWriter s = new StringWriter();
		PrintWriter p = new PrintWriter(s);
		exception.printStackTrace(p);
		debug(location, "&color:red.null[EXCEPTION in thread '"
				+ Thread.currentThread().getName() + "':\n" + s + "]&");
	}

	private String getDate(boolean ms) {
		Date d = new Date();
		if (ms) return String.format("%tF %tT.%tL %tZ", d, d, d, d);
		return String.format("%tF %tT %tZ", d, d, d);
	}
}
