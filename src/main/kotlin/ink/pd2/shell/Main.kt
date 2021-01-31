package ink.pd2.shell

import ink.pd2.shell.buildin.VariableMarkProvider
import ink.pd2.shell.core.Mark
import ink.pd2.shell.core.Resources
import ink.pd2.shell.io.Input
import ink.pd2.shell.io.Output
import ink.pd2.shell.log.writeDebugLog
import java.util.LinkedList
import kotlin.concurrent.thread
import kotlin.system.exitProcess

//Shell列表
private val shells = LinkedList<Shell>(listOf())

//I/O流
var input: Input? = null
var output: Output? = null

//默认的print方法
fun print(s: String) {
	output?.print(Mark.update(s))
}
fun println(s: String) {
	output?.println(Mark.update(s))
}

//环境变量修改
fun addVariable(key: String, value: String) {
	VariableMarkProvider.variables[key] = value
}
fun removeVariable(key: String) {
	VariableMarkProvider.variables.remove(key)
}

/**
 * ## mainThread() | 主线程方法
 *
 * Pd2 Shell 的主线程起始点.
 *
 * ### 用法
 *
 * 主线程由 main() 方法自动执行, 若无特别需要, 切记:
 *
 * 请勿尝试调用此方法!
 *
 * 请勿尝试调用此方法!!
 *
 * 请勿尝试调用此方法!!!
 */

fun mainThread() {
	writeDebugLog("Main[OBJECT]", "output: $output")
	output?.print(Resources.getString("psh.shell-greet-text"))
	//TODO Shell开搞
}

/**
 * ## startShell() | 运行新的 Shell 会话
 *
 * 用于在新的线程运行一个新的 Shell 会话
 *
 * @param shell 将要运行的 Shell 会话对象
 *
 * @see ink.pd2.shell.Shell
 *
 * @author Maxel Black
 * @since PSH 1.0
 */

fun startShell(shell: Shell) {
	shells.add(shell)
	thread {
		shell.run()
	}
}

fun exit(status: Int, reason: String) {
	kotlin.io.println("${Resources.getString("psh.exit")}: $reason")
	exitProcess(status)
}
