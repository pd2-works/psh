package ink.pd2.shell

import ink.pd2.shell.core.Resources
import ink.pd2.shell.io.ConsoleInput
import ink.pd2.shell.io.Input
import ink.pd2.shell.io.Output
import ink.pd2.shell.log.writeDebugLog
import ink.pd2.shell.log.writeLog
import java.util.LinkedList
import kotlin.concurrent.thread

//Shell列表
private val shells = LinkedList<Shell>(listOf())

//IO流
var input: Input? = null
var output: Output? = null

fun mainThread() {
	writeDebugLog("Main[OBJECT]", "output: $output")
	output?.write(Resources.getString("psh.shell-greet-text"))
	//TODO Shell开搞
}

fun startShell(shell: Shell) {
	shells.add(shell)
	thread {
		shell.run()

	}
}
