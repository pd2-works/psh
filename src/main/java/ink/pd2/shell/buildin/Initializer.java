package ink.pd2.shell.buildin;

import ink.pd2.shell.core.Logger;
import ink.pd2.shell.core.Mark;
import ink.pd2.shell.core.Resources;
import org.fusesource.jansi.Ansi;

public final class Initializer {
	public final static Initializer INS = new Initializer();

	public void initTheme() {
		Logger.INS.info("Initializer",
			Resources.INS.getString("psh.log-init-theme"));
		//TODO 初始化主题
	}

	public void initResources() {
		Logger.INS.info("Initializer", "Initializing core resources...");

		//初始化资源

		//字符串
		putStr("psh.name", "Pd2 Shell");
		putStr("psh.version", "1.0.0-a115");
		putStr("psh.description", "The core commands, options and APIs.");

		putStr("psh.log-init-plugin", "Initializing Plugins...");
		putStr("psh.log-init-shell", "Initializing new shell thread...");
		putStr("psh.log-init-theme", "Loading UI theme...");
		putStr("psh.log-init-jline-reader", "Initializing JLine terminal reader...");
		putStr("psh.log-error-init-jline", "JLine Initialization FAILED.");

		putStr("psh.exit", "Exit");

		putStr("psh.plugin-no-name", "Unnamed");
		putStr("psh.plugin-no-description", "There is no description.");

		putStr("psh.shell-prompt-text-left", "&color:green.null[\\&v:user\\&]&:&color:blue.null[\\&v:current_folder\\&]&$ ");
		putStr("psh.shell-prompt-text-right", "(&color:blue.null[\\&v:return\\&]&)");

		putStr("psh.shell-greet-text", "&color:blue.null[Welcome to Pd2 Shell!\n    ____      _____      _____ __         ____   __  \n   / __ \\____/ /__ \\    / ___// /_  ___  / / /   \\ \\ \n  / /_/ / __  /__/ /    \\__ \\/ __ \\/ _ \\/ / /     \\ \\\n / ____/ /_/ // __/    ___/ / / / /  __/ / /      / /\n/_/    \\__,_//____/   /____/_/ /_/\\___/_/_/      /_/ ]&\n\n");
		/*
		=====================================================
		Welcome to Pd2 Shell!
		    ____      _____      _____ __         ____   __
           / __ \____/ /__ \    / ___// /_  ___  / / /   \ \
          / /_/ / __  /__/ /    \__ \/ __ \/ _ \/ / /     \ \
		 / ____/ /_/ // __/    ___/ / / / /  __/ / /      / /
		/_/    \__,_//____/   /____/_/ /_/\___/_/_/      /_/

		=====================================================
		 */

		//监听器
		Resources.INS.registerListenerType("psh", "command-entered");

		Logger.INS.info("Initializer", "Resources initialization finished.");
	}
	private void putStr(String k, String v) {
		Resources.INS.putString(k, v);
	}

	public void initMarks() {
		Mark.INS.registerMarkProvider(VariableMarkProvider.INS);
		VariableMarkProvider.INS.getVariables().put("user", "user");
		VariableMarkProvider.INS.getVariables().put("host", "localhost");
		Mark.INS.registerMarkProvider(ColorMarkProvider.INS);
		ColorMarkProvider.INS.putColor("black", Ansi.Color.BLACK.value());
		ColorMarkProvider.INS.putColor("blue", Ansi.Color.BLUE.value());
		ColorMarkProvider.INS.putColor("cyan", Ansi.Color.CYAN.value());
		ColorMarkProvider.INS.putColor("default", Ansi.Color.DEFAULT.value());
		ColorMarkProvider.INS.putColor("green", Ansi.Color.GREEN.value());
		ColorMarkProvider.INS.putColor("magenta", Ansi.Color.MAGENTA.value());
		ColorMarkProvider.INS.putColor("red", Ansi.Color.RED.value());
		ColorMarkProvider.INS.putColor("white", Ansi.Color.WHITE.value());
		ColorMarkProvider.INS.putColor("yellow", Ansi.Color.YELLOW.value());
	}
}
