package ink.pd2.psh.util;

public class Logger {
	public final static Logger DEFAULT = new Logger();

	private String defaultInfo;
	private boolean hasTime;

	public Logger() {
		this(null);
	}
	public Logger(String defaultInfo) {
		this(defaultInfo, true);
	}
	public Logger(boolean hasTime) {
		this(null, hasTime);
	}
	public Logger(String defaultInfo, boolean hasTime) {
		this.defaultInfo = defaultInfo;
		this.hasTime = hasTime;
	}

	//TODO log输出方法

	public String getDefaultInfo() {
		return defaultInfo;
	}
	public void setDefaultInfo(String defaultInfo) {
		this.defaultInfo = defaultInfo;
	}
	public void showTime(boolean on) {
		this.hasTime = on;
	}
}
