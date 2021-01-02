package ink.pd2.shell.core

import ink.pd2.shell.log.writeLog
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
		writeLog("Initializer", "Initializing resources...")

		//初始化资源
		putStr("psh.log-init-shell", "Initializing new shell thread...")
		putStr("psh.log-init-theme", "Loading UI theme...")
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
}
