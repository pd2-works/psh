package ink.pd2.shell.plugin;

import ink.pd2.shell.Shell;
import ink.pd2.shell.core.CommandParameter;

public interface RunCommandListener extends Listener {
    void event(Command command, CommandParameter parameter, Shell shell);
}
