package ink.pd2.psh.console;

import ink.pd2.psh.core.Variables;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class ShellConsole implements Console {
	private String currentUser;
	private String currentCommandLine = null;
	private Path currentDirectory;

	private Thread thread;
	private Thread currentThread;

	private Variables variables;

	public ShellConsole() {
	}

	public ShellConsole(ShellConsole parent) {
		currentDirectory = parent.currentDirectory;
	}

	@Override
	public boolean initial() throws InitialException {
		try {
			// shell init
			currentUser = System.getProperty("user.name");
			thread = Thread.currentThread();
			currentThread = thread;
		} catch (Exception e) {
			throw new InitialException("An exception was thrown while initialization.", e);
		}
		return true;
	}

	@Override
	public void run() {
		//TODO shell run
	}

	@Override
	public int execute(String command) {
		currentCommandLine = command;
		// shell execute
		AtomicInteger returnCode = new AtomicInteger();
		Thread t = new Thread(() -> returnCode.set(onCommandExecute(command)));
		t.setUncaughtExceptionHandler((thread, throwable) -> {
			//TODO 未捕获的异常处理
		});
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
			//TODO 线程异常处理
		}
		currentCommandLine = null;
		return returnCode.intValue();
	}

	private int onCommandExecute(String command) {
		//TODO 指令处理与执行
		return 0;
	}

	@Override
	public String getCurrentUser() {
		return currentUser;
	}
	@Override
	public String getCurrentCommandLine() {
		return currentCommandLine;
	}
	@Override
	public Thread getThread() {
		return thread;
	}
	@Override
	public Thread getCurrentThread() {
		return currentThread;
	}

	@Override public abstract void print(String str);
	@Override public abstract String readLine();
	protected abstract String readCommand();

	//get & set
	public Variables getVariables() {
		return variables;
	}
	public Path getCurrentDirectory() {
		return currentDirectory;
	}
}
