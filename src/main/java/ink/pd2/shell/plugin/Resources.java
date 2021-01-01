package ink.pd2.shell.plugin;

public class Resources {
    public static String getString(String key) {
        return ink.pd2.shell.core.Resources.INSTANCE.getString(key);
    }
    public static void putString(String key, String value) {
        ink.pd2.shell.core.Resources.INSTANCE.putString(key, value);
    }
    public static String removeString(String key) {
        return ink.pd2.shell.core.Resources.INSTANCE.removeString(key);
    }
}
