package ink.pd2.psh.core;

import java.util.HashMap;

public final class Permission {

	/*<-------------------------- Permissions -------------------------->*/

	public static final boolean DEFAULT_MANAGE_MODULES = false;
	public static final boolean DEFAULT_READ_MODULES = false;
	public static final boolean DEFAULT_READ_PERMISSION = false;
	public static final boolean DEFAULT_WRITE_PERMISSION = false;
	public static final boolean DEFAULT_READ_GLOBAL_RESOURCES = true;
	public static final boolean DEFAULT_WRITE_GLOBAL_RESOURCES = false;
	public static final boolean DEFAULT_EXECUTE_COMMAND = false;
	public static final boolean DEFAULT_ADD_EVENT = true;
	public static final boolean DEFAULT_ADD_EVENT_KEY = true;
	public static final boolean DEFAULT_READ_EVENTS = true;
	public static final boolean DEFAULT_MANAGE_EVENTS = false;

	public static final int ROOT = 0;
	public static final int MANAGE_MODULES = 1;
	public static final int READ_MODULES = 2;
	public static final int READ_PERMISSION = 3;
	public static final int WRITE_PERMISSION = 4;
	public static final int READ_GLOBAL_RESOURCES = 5;
	public static final int WRITE_GLOBAL_RESOURCES = 6;
	public static final int EXECUTE_COMMAND = 7;
	public static final int ADD_EVENT = 8;
	public static final int ADD_EVENT_KEY = 9;
	public static final int READ_EVENTS = 10;
	public static final int MANAGE_EVENTS = 11;

	public static final int MAX_PERMISSION_INDEX = 12;

	/*===================================================================*/

	private static final HashMap<Integer, PermissionInfo> infoMap = new HashMap<>();

	public static Boolean check(int cid, String nid, int permission) {
		if (!Permission.check(cid, Permission.READ_PERMISSION)) return true;
		Module module = ModuleBoard.moduleNidMap.get(nid);
		if (module == null) return null;
		return check(module.id, permission);
	}

	public static boolean set(int cid, String nid, int permission, boolean value) {
		if (!Permission.check(cid, Permission.WRITE_PERMISSION)) return false;
		Module module = ModuleBoard.moduleNidMap.get(nid);
		if (module == null) return false;
		infoMap.get(module.id).set(permission, value);
		return true;
	}

	protected static boolean check(Integer id, int permission) {
		PermissionInfo info = infoMap.get(id);
		if (info == null) return false;
		return info.get(Permission.ROOT) || info.get(permission);
	}

	protected static PermissionInfo putInfo(Integer id, PermissionInfo info) {
		return infoMap.put(id, info);
	}

	protected static final class PermissionInfo {
		private final Boolean[] items = new Boolean[MAX_PERMISSION_INDEX];
		protected Boolean get(int permission) {
			if (permission > MAX_PERMISSION_INDEX || permission < 0)
				throw new NoSuchPermissionException();
			return items[permission];
		}
		synchronized protected void set(int permission, boolean value) {
			if (permission > MAX_PERMISSION_INDEX || permission < 0)
				throw new NoSuchPermissionException();
			items[permission] = value;
		}
	}

}
