package ink.pd2.psh.util;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public final class Signal {
	public static final String SIGHUP = "HUP";
	public static final String SIGINT = "INT";
	public static final String SIGQUIT = "QUIT";
	public static final String SIGILL = "ILL";
	public static final String SIGTRAP = "TRAP";
	public static final String SIGABRT = "ABRT";
	public static final String SIGFPE = "FPE";
	@Deprecated public static final String SIGKILL = "KILL";
	public static final String SIGSEGV = "SEGV";
	public static final String SIGPIPE = "PIPE";
	public static final String SIGALRM = "ALRM";
	public static final String SIGTERM = "TERM";
	public static final String SIGUSR1 = "USR1";
	public static final String SIGUSR2 = "USR2";
	public static final String SIGCHLD = "CHLD";
	public static final String SIGCONT = "CONT";
	@Deprecated public static final String SIGSTOP = "STOP";
	public static final String SIGTSTP = "TSTP";
	public static final String SIGTTIN = "TTIN";
	public static final String SIGTTOU = "TTOU";

	private static Class<?> cSignal;
	private static Class<?> cHandler;
	private static Method mHandle;

	private static boolean initialized = false;

	public static void handle(String signal, SignalHandler handler) {
		try {
			if (!initialized) initialClasses();
			Object oSignal = cSignal.getConstructor(String.class).newInstance(signal);
			Object oHandler = cHandler.cast(Proxy.newProxyInstance(
					cHandler.getClassLoader(), new Class[]{cHandler},
					((proxy, method, args1) -> {
						switch (method.getName()) {
							case "handle":
								handler.handle();
								break;
							case "equals":
								return proxy.equals(args1);
							case "hashCode":
								return proxy.hashCode();
							case "toString":
								return proxy.toString();
						}
						return null;
					})
			));
			mHandle.invoke(null, oSignal, oHandler);
		} catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	private static void initialClasses()
			throws ClassNotFoundException, NoSuchMethodException {
		cSignal = Class.forName("sun.misc.Signal");
		cHandler = Class.forName("sun.misc.SignalHandler");
		mHandle = cSignal.getMethod("handle", cSignal, cHandler);
		initialized = true;
	}
}
