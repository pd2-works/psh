package ink.pd2.psh.core;

import java.util.HashMap;

public final class ModuleBoard {

	/* |<- 全局成员 ->| */
	protected static HashMap<String, Module> moduleNidMap = new HashMap<>();

	protected static HashMap<Integer, Module> moduleIdMap = new HashMap<>();

	protected static void initial(Module module) throws Exception {
		moduleIdMap.put(module.id, module);
		module.onInitial();
		//TODO 国际化
	}

	public static void load(int cid, Module module) throws Exception {
		if (isNoManagingPermission(cid)) return;
		load(module);
	}
	protected static void load(Module module) throws Exception {
		//TODO 加载模块
	}

	public static void reload(int cid, Module module) throws Exception {
		if (isNoManagingPermission(cid)) return;
		reload(module);
	}
	protected static void reload(Module module) throws Exception {
		//TODO 热重载
	}

	public static boolean exist(int cid, String nid) {
		if (isNoReadingPermission(cid)) return false;
		return exist(nid);
	}
	protected static boolean exist(String nid) {
		return moduleNidMap.containsKey(nid);
	}

	protected static int newRandomId() {
		return Main.random.nextInt();
	}

	protected static boolean isNoManagingPermission(int cid) {
		return !Permission.check(cid, Permission.MANAGE_MODULES);
	}

	protected static boolean isNoReadingPermission(int cid) {
		return !Permission.check(cid, Permission.READ_MODULES);
	}
}
