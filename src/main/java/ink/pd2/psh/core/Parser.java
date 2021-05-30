package ink.pd2.psh.core;

import java.util.HashMap;

public final class Parser {
	private final Resources resources;

	public Parser(Resources resources) {
		this.resources = resources;
	}

	public String parseSign(String s) {
		if (s.length() < 2) return s;
		char startChar = s.charAt(0);
		String sub = s.substring(1);
		switch (startChar) {
			case '@': return parseResources(sub);
			case '$': return parseVariable(sub);
			case '^': return parseURL(sub);
		}
		return s;
	}

	public String parseResources(String key) {
		String result = resources.getItemValueAsString(key);
		return (result == null)? key : result;
	}

	public String parseVariable(String key) {
		//TODO
		return key;
	}

	public String parseURL(String key) {
		//TODO
		return key;
	}

	/*=========================================*/
	private static final HashMap<String, Runnable> keyMap = new HashMap<>();

	private static boolean isNoWritePermission(int cid) {
		return !Permission.check(cid, Permission.WRITE_GLOBAL_RESOURCES);
	}
}
