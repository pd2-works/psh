package ink.pd2.psh.core;

import java.util.HashMap;
import java.util.HashSet;

public final class ModuleBoard {

	/* |<- 全局成员 ->| */
	protected static HashMap<String, Module> moduleNidMap = new HashMap<>();
	protected static HashSet<Integer> moduleIdSet = new HashSet<>();

	protected static void initial(Module module) throws Exception {
//		moduleIdMap.put(module.id, module);
		module.onInitial();
		//TODO 国际化
	}

	protected static void reload(Module module) throws Exception {
		//TODO 热重载
	}

	public static int newRandomId(Module module) {
		return Main.random.nextInt(module.hashCode());
	}

	public static boolean exist(int cid, String nid) {
		if (isNoReadingPermission(cid)) return false;
		return exist(nid);
	}
	protected static boolean exist(String nid) {
		return moduleNidMap.containsKey(nid);
	}

	protected static boolean isNoManagingPermission(int cid) {
		return !Permission.check(cid, Permission.MANAGE_MODULES);
	}

	protected static boolean isNoReadingPermission(int cid) {
		return !Permission.check(cid, Permission.READ_MODULES);
	}
}
