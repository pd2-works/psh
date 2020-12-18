package ink.pd2.shell.plugin;

import ink.pd2.shell.core.Resources;

public class R {
    public static String getString(String key) {
        return Resources.INSTANCE.getString(key);
    }
    public static void putString(String key, String value) {
        Resources.INSTANCE.putString(key, value);
    }
    public static String removeString(String key) {
        return Resources.INSTANCE.removeString(key);
    }
}
