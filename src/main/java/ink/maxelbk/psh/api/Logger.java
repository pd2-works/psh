package ink.maxelbk.psh.api;

public class Logger {
	public static void writeLog(String location, String info) {
		KotlinScripts.Logger.INSTANCE.writeLog(location, info);
	}
	public static void writeException(String location, Exception exception) {
		KotlinScripts.Logger.INSTANCE.writeException(location, exception);
	}
}
