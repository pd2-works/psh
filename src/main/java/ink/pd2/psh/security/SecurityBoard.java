package ink.pd2.psh.security;

import java.util.ArrayList;

public final class SecurityBoard {
	public final static SecurityManager manager = new SecurityManager();

	private final static ArrayList<Class<?>> classList = new ArrayList<>();
	public static void declareClass(Class<?> clazz) {
		classList.add(clazz);
	}
}
