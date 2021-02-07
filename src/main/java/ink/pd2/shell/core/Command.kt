package ink.pd2.shell.core

import ink.pd2.shell.Shell

open class Command(val name: String) {
	val events = HashSet<CommandEvent>(setOf()) //指令执行事件

	constructor(name: String, vararg events: CommandEvent) : this(name) {
		this.events += events
	}

	open fun onExecute(shell: Shell, para: CommandParameter) {
		for (event in events) {
			event.run(shell, para)
		}
	}

	//添加事件
	fun addEvent(event: CommandEvent) {
		events += event
	}
	fun addEvent(vararg events: CommandEvent) {
		this.events += events
	}
}
