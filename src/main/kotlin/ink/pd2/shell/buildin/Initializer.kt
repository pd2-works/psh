package ink.pd2.shell.buildin

import ink.pd2.shell.core.Mark
import ink.pd2.shell.core.Resources
import ink.pd2.shell.log.writeLog
import org.fusesource.jansi.Ansi
import javax.swing.UIManager

object Initializer {
	fun initTheme() {
		writeLog("Initializer", Resources.getString("psh.log-init-theme"))

		//初始化主题
		//Swing主题配置
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}

	fun initResources() {
		writeLog("Initializer", "Initializing core resources...")

		//初始化资源
		putStr("psh.log-init-shell", "Initializing new shell thread...")
		putStr("psh.log-init-theme", "Loading UI theme...")
		putStr("psh.log-init-jline-reader", "Initializing JLine terminal reader...")
		putStr("psh.exit", "Exit")
		putStr("psh.plugin-no-description", "There is no description.")
		putStr("psh.shell-hint-text", "&USER:&HOSTNAME$ ")
		putStr("psh.shell-greet-text", "Welcome to Pd2 Shell!\n    ____      _____      _____ __         ____   __  \n   / __ \\____/ /__ \\    / ___// /_  ___  / / /   \\ \\ \n  / /_/ / __  /__/ /    \\__ \\/ __ \\/ _ \\/ / /     \\ \\\n / ____/ /_/ // __/    ___/ / / / /  __/ / /      / /\n/_/    \\__,_//____/   /____/_/ /_/\\___/_/_/      /_/ \n")
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

		writeLog("Initializer", "Resources initialization finished.")
	}
	private fun putStr(k: String, v: String) {
		Resources.putString(k, v)
	}

	fun initMarks() {
		Mark.regMarkProvider(VariableMarkProvider)
		Mark.regMarkProvider(ColorMarkProvider)
		ColorMarkProvider.putColor("black", Ansi.Color.BLACK.value())
		ColorMarkProvider.putColor("blue", Ansi.Color.BLUE.value())
		ColorMarkProvider.putColor("cyan", Ansi.Color.CYAN.value())
		ColorMarkProvider.putColor("default", Ansi.Color.DEFAULT.value())
		ColorMarkProvider.putColor("green", Ansi.Color.GREEN.value())
		ColorMarkProvider.putColor("magenta", Ansi.Color.MAGENTA.value())
		ColorMarkProvider.putColor("red", Ansi.Color.RED.value())
		ColorMarkProvider.putColor("white", Ansi.Color.WHITE.value())
		ColorMarkProvider.putColor("yellow", Ansi.Color.YELLOW.value())
	}
}
