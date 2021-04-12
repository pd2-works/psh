package ink.pd2.psh.core;

import java.lang.reflect.Field;
import java.util.HashMap;

public final class ModuleBoard {

	/* |<- 全局成员 ->| */
	protected static HashMap<String, Module> moduleNidMap = new HashMap<>();
	protected static HashMap<Integer, Module> moduleIdMap = new HashMap<>();

	protected static boolean initial(Module module) throws ReflectiveOperationException {
		Field id = module.getClass().getDeclaredField("id");
		id.set(module, 0);
		return true;
	}

	public static boolean exist(int cid, String nid) {
		if (isNoManagingPermission(cid)) return false;
		return exist(nid);
	}
	protected static boolean exist(String nid) {
		Module module = moduleNidMap.get(nid);
		return module != null;
	}

	protected static boolean isNoManagingPermission(int cid) {
		return !Permission.check(cid, Permission.MANAGE_MODULES);
	}

	protected static boolean isNoReadingPermission(int cid) {
		return !Permission.check(cid, Permission.READ_MODULES);
	}
}
