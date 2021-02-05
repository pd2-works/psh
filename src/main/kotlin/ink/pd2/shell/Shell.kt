package ink.pd2.shell

import ink.pd2.shell.log.writeLog
import ink.pd2.shell.core.Resources
import ink.pd2.shell.io.Input
import ink.pd2.shell.io.Output
import ink.pd2.shell.plugin.CommandExecutedListener

class Shell(val input: Input, val output: Output) {
	//常量
	companion object {
		const val DEFAULT_PRIORITY: Int = 2
	}

	//Shell信息
	var title: String = "Pd2 Shell"
	private var user: String
		get() = field
	fun getCurrentUser(): String {
		return getVariableValue("user")!!
	}

	//分优先级的指令执行事件
	private val listeners = arrayOf<ArrayList<CommandExecutedListener>>(
		ArrayList(1), ArrayList(1), ArrayList(10),
		ArrayList(1), ArrayList(2))

	init {
		writeLog("Shell", Resources.getString("psh.log-init-shell"))
		//将事件按优先级排序
		val ls = Resources.getListeners("psh", "command-executed")
		for (li in ls) {
			val l = li as CommandExecutedListener
			listeners[l.priority].add(l)
		}
		user = getCurrentUser()
	}

	fun run() {
		shell@
		while (true) {
			//指令处理和执行
			val c = input.command
			for (ls in listeners) for (l in ls) {
				if (!
					l.event(this, c)
				) break@shell
			}

		}
		//退出事件

	}

}
