package ink.pd2.shell

import ink.pd2.shell.log.writeLog
import ink.pd2.shell.plugin.RunCommandListener
import ink.pd2.shell.core.Resources
import ink.pd2.shell.io.Input
import ink.pd2.shell.io.Output

class Shell(val input: Input, val output: Output) {
	//Shell信息
	var title: String = "Pd2 Shell"
	private var user: String

	//Listeners
	private val runCommandListenerList = ArrayList<RunCommandListener>(listOf())

	init {
		writeLog("Shell", Resources.getString("psh.log-init-shell"))
		user = System.getProperty("user.name")
		startShell(this)
	}

	fun run() {
		while (true) {
			//TODO 继续：按键处理和指令执行
			for (listener in runCommandListenerList) {

			}
		}
	}

}
