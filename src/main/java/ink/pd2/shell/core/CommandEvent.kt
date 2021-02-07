package ink.pd2.shell.core

import ink.pd2.shell.Shell

interface CommandEvent {
	fun run(shell: Shell, para: CommandParameter)
}
