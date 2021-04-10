package ink.pd2.psh.core;

import java.util.HashMap;

public final class Permission {
    public static final boolean DEFAULT_MANAGE_MODULES = false;
    public static final boolean DEFAULT_MANAGE_MODULE_FILES = false;
    public static final boolean DEFAULT_READ_PERMISSION = false;
    public static final boolean DEFAULT_WRITE_PERMISSION = false;
    public static final boolean DEFAULT_READ_GLOBAL_RESOURCES = true;
    public static final boolean DEFAULT_WRITE_GLOBAL_RESOURCES = false;
    public static final boolean DEFAULT_EXECUTE_COMMAND = false;

    public static final int ALLOW_ALL = 0;
    public static final int MANAGE_MODULES = 2;
    public static final int MANAGE_MODULE_FILES = 3;
    public static final int READ_PERMISSION = 4;
    public static final int WRITE_PERMISSION = 5;
    public static final int READ_GLOBAL_RESOURCES = 6;
    public static final int WRITE_GLOBAL_RESOURCES = 7;
    public static final int EXECUTE_COMMAND = 8;

    public static final int MAX_PERMISSION_INDEX = 8;

    /*===================================================================*/

    private static final HashMap<Integer, PermissionInfo> infoMap = new HashMap<>();

    public static Boolean check(int cid, String nid, int permission) {
        if (!Permission.check(cid, Permission.READ_PERMISSION)) return true;
        Module module = Main.moduleNidMap.get(nid);
        if (module == null) return null;
        return check(module.id, permission);
    }

    public static boolean set(int cid, String nid, int permission, boolean value) {
        if (permission > MAX_PERMISSION_INDEX) throw new NoSuchPermissionException();
        if (!Permission.check(cid, Permission.WRITE_PERMISSION)) return false;
        Module module = Main.moduleNidMap.get(nid);
        if (module == null) return false;
        infoMap.get(module.id).set(permission, value);
        return true;
    }

    protected static boolean check(Integer id, int permission) {
        PermissionInfo info = infoMap.get(id);
        if (info == null) return false;
        return info.get(permission);
    }

    protected static PermissionInfo putInfo(Integer id, PermissionInfo info) {
        return infoMap.put(id, info);
    }

    private static final class PermissionInfo {
        boolean[] items = new boolean[MAX_PERMISSION_INDEX];
        private boolean get(int permission) {
            return items[permission];
        }
        synchronized private void set(int permission, boolean value) {
            items[permission] = value;
        }
    }

    public static final class NoSuchPermissionException extends RuntimeException {}
}
