package ink.pd2.psh.console;

public interface Console {
	boolean initial() throws InitialException; //初始化
	void run() throws ConsoleException; //运行控制台
	int execute(String command); //运行单条指令
	default boolean start() throws ConsoleException {
		boolean b = initial();
		if (!b) return false;
		run(); return true;
	}

	String getCurrentUser();
	String getCurrentCommandLine();

//	Map<String, String> getVariableMap();

	Thread getThread();
	Thread getCurrentThread();

	void print(String str);
	default void printf(String format, Object... args) {
		print(String.format(format, args));
	}
	default void println(String str) {
		print(str + '\n');
	}

	String readLine();
	default String readPassword() {
		return readLine();
	}
	default String readLine(String prompt) {
		print(prompt);
		return readLine();
	}
	default String readPassword(String prompt) {
		print(prompt);
		return readPassword();
	}
	default void clean() throws UnsupportedConsoleOperationException {
		throw new UnsupportedConsoleOperationException();
	}

}
