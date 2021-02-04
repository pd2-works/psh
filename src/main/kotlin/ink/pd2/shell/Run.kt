package ink.pd2.shell

import ink.pd2.shell.buildin.Initializer
import ink.pd2.shell.io.ConsoleInput
import ink.pd2.shell.io.ConsoleOutput
import ink.pd2.shell.log.writeDebugLog

fun main(args: Array<String>) {
	writeDebugLog("PreInit", "Initialization started.")

	Initializer.initResources() //初始化资源
	Initializer.initMarks() //初始化默认标记
	Initializer.initTheme() //初始化主题

	//设置I/O流
	input = ConsoleInput()
	output = ConsoleOutput()

	//TODO 判断是否有另一个psh进程正在运行

	writeDebugLog("PreInit", "Initialization finished.")

	mainThread()

	println("\\& &color:red[[E] There is a error.]& \\&")
}
