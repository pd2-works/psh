package ink.pd2.shell

import ink.pd2.shell.buildin.Initializer
import ink.pd2.shell.buildin.VariableMarkProvider
import ink.pd2.shell.core.Mark
import ink.pd2.shell.core.Resources
import ink.pd2.shell.io.ConsoleInput
import ink.pd2.shell.io.ConsoleOutput
import ink.pd2.shell.io.Input
import ink.pd2.shell.io.Output
import ink.pd2.shell.log.writeDebugLog
import ink.pd2.shell.log.writeErrorLog
import ink.pd2.shell.log.writeException
import ink.pd2.shell.log.writeLog
import java.net.InetAddress
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
	val ss = Mark.update(s)
	output?.println(ss)
}

//环境变量修改
fun putVariable(key: String, value: String) {
	VariableMarkProvider.variables[key] = value
}
fun getVariableValue(key: String): String? {
	return VariableMarkProvider.variables[key]
}
fun removeVariable(key: String) {
	VariableMarkProvider.variables.remove(key)
}

/**
 * ## mainProcess() | 主进程
 *
 * Pd2 Shell 的主进程起始点.
 *
 * ### 用法
 *
 * 主进程由 main() 方法自动执行, 若无特别需要, 切记:
 *
 * 请勿尝试调用此方法!
 *
 * 请勿尝试调用此方法!!
 *
 * 请勿尝试调用此方法!!!
 */

fun mainProcess() {
	writeDebugLog("Main[OBJECT]", "output: $output")
	output?.print(Mark.update(Resources.getString("psh.shell-greet-text")))
	putVariable("user", System.getProperty("user.name"))
	putVariable("host", InetAddress.getLocalHost().hostName)
//	startShell(Shell(input!!, output!!))
	//TODO 监听网络Shell
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

private fun startShell(shell: Shell) {
	shells.add(shell)
//	thread {
		shell.run()
//	}
}

fun exit(status: Int, reason: String) {
	kotlin.io.println("${Resources.getString("psh.exit")}: $reason")
	exitProcess(status)
}

/**
 * ## main() | 程序入口
 *
 * @author Maxel Black
 * @since PSH 1.0
 */

fun main(args: Array<String>) {
	output = ConsoleOutput() //设置output流

	Initializer.initMarks() //初始化默认标记

	writeDebugLog("Main.PreInit", "Initialization started.")

	Initializer.initResources() //初始化资源
	Initializer.initTheme() //初始化主题

	input = ConsoleInput() //设置input流

	//TODO 初始化插件

	//TODO 判断是否有另一个psh进程正在运行

	writeDebugLog("Main.PreInit", "Initialization finished.")

	mainProcess() //启动主进程任务

	writeLog("Main", "The shell on '&v:user&' exit.")
}
