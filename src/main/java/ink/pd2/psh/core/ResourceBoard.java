package ink.pd2.psh.core;

import java.util.HashMap;

public final class ResourceBoard {
	private final static HashMap<Integer, Resources> resourcesMap = new HashMap<>();

	protected static Resources get(Integer id) {
		return resourcesMap.get(id);
	}

	protected static Resources put(Integer id, Resources value) {
		return resourcesMap.put(id, value);
	}

	protected static boolean exist(Integer id) {
		return resourcesMap.containsKey(id);
	}
}
