package ink.pd2.psh.console;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class ShellConsole implements Console {
	private String currentUser;
	private String currentCommandLine = null;

	private Thread thread;
	private Thread currentThread;

	@Override
	public boolean initial() throws InitialException {
		try {
			//TODO shell init
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
		//TODO shell execute
		AtomicInteger returnCode = new AtomicInteger();
		Thread t = new Thread(() -> returnCode.set(onCommandExecute(command)));
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
			//TODO 异常处理
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
	public String getCurrentCommandline() {
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

}
