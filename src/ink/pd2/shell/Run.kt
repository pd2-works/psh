package ink.pd2.shell

import ink.pd2.shell.core.Initializer
import ink.pd2.shell.io.ConsoleInput
import ink.pd2.shell.io.ConsoleOutput
import java.io.Console

fun main(args: Array<String>) {
    Initializer.initResources() //初始化资源
    Initializer.initTheme() //初始化主题

    input = ConsoleInput(System.`in`)
    output = ConsoleOutput(System.out)

    //TODO 判断是否有另一个psh进程正在运行

    mainThread()
}
