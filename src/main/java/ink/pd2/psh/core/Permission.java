package ink.pd2.psh.core;

import java.util.HashMap;

public final class Permission {
    public static final boolean DEFAULT_MANAGE_MODULES = false;
    public static final boolean DEFAULT_MANAGE_PERMISSION = false;
    public static final boolean DEFAULT_READ_GLOBAL_RESOURCES = true;
    public static final boolean DEFAULT_WRITE_GLOBAL_RESOURCES = false;
    public static final boolean DEFAULT_EXECUTE_COMMAND = false;

    public static final int MANAGE_MODULES = 2;
    public static final int READ_PERMISSION = 4;
    public static final int WRITE_PERMISSION = 5;
    public static final int READ_GLOBAL_RESOURCES = 6;
    public static final int WRITE_GLOBAL_RESOURCES = 7;
    public static final int EXECUTE_COMMAND = 8;

    /*===================================================================*/
    private static final HashMap<Integer, PermissionInfo> infoMap = new HashMap<>();

    protected static boolean check(Integer id, int permission) {
        PermissionInfo info = infoMap.get(id);
        return !info.get(permission); //通过返回false, 不通过返回true
    }

    protected static PermissionInfo putInfo(Integer id, PermissionInfo info) {
        return infoMap.put(id, info);
    }

    public static final class PermissionInfo {
        boolean[] items = new boolean[16];
        public boolean get(int permission) {
            return items[permission];
        }
        synchronized public void set(int permission, boolean value) {
            items[permission] = value;
        }
    }
}
