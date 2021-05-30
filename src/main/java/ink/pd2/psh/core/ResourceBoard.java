package ink.pd2.psh.core;

import java.util.HashMap;

public final class ResourceBoard {
	private final static HashMap<Integer, Resources> resourcesMap = new HashMap<>();

	public static Resources get(int cid, String nid) {
		int id = ModuleBoard.moduleNidMap.get(nid).id;
		if (isNoReadPermission(cid, id)) return null;
		return get(id);
	}
	protected static Resources get(Integer id) {
		return resourcesMap.get(id);
	}

	protected static Resources put(Integer id, Resources value) {
		return resourcesMap.put(id, value);
	}

	protected static boolean exist(Integer id) {
		return resourcesMap.containsKey(id);
	}

	private static boolean isNoReadPermission(int cid, int id) {
		return id != cid &&
				!Permission.check(cid, Permission.READ_GLOBAL_RESOURCES);
	}

	private static boolean isNoWritePermission(int cid, int id) {
		return id != cid &&
				!Permission.check(cid, Permission.WRITE_GLOBAL_RESOURCES);
	}
}
