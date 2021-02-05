package ink.pd2.shell

import ink.pd2.shell.log.writeLog
import ink.pd2.shell.core.Resources
import ink.pd2.shell.io.Input
import ink.pd2.shell.io.Output

class Shell(val input: Input, val output: Output) {
	//Shell信息
	var title: String = "Pd2 Shell"
	var user: String
	fun getCurrentUser(): String {
		return getVariableValue("user")!!
	}


	//Listeners
	private val runningCommandListeners =
		Resources.getListeners("psh", "command-running")

	init {
		writeLog("Shell", Resources.getString("psh.log-init-shell"))
		user = getCurrentUser()
	}

	fun run() {
		while (true) {
			//TODO 继续：指令处理和执行
			val c = input.command

		}
	}

}
