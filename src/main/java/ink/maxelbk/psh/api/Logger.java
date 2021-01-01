package ink.maxelbk.psh.api;

public class Logger {
	public static void writeLog(String location, String info) {
		StaticKotlinScripts.Logger.INSTANCE.writeLog(location, info);
	}
	public static void writeException(String location, Exception exception) {
		StaticKotlinScripts.Logger.INSTANCE.writeException(location, exception);
	}
}
