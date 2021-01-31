package ink.pd2.shell.plugin;

import ink.pd2.shell.core.Command;
import ink.pd2.shell.core.Mark;

import java.util.Set;

public final class Resources {
    public static String getString(String key) {
        return Mark.INSTANCE.update(ink.pd2.shell.core.
                Resources.INSTANCE.getString(key), false);
    }
    public static void putString(String key, String value) {
        ink.pd2.shell.core.
                Resources.INSTANCE.putString(key, value);
    }
    public static String removeString(String key) {
        return ink.pd2.shell.core.
                Resources.INSTANCE.removeString(key);
    }

    public static Set<Command> getAllCommands() {
        return ink.pd2.shell.core.
                Resources.INSTANCE.getCommands();
    }
}
