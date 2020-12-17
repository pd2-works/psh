package ink.pd2.shell

import kotlin.concurrent.thread

private val shells = ArrayList<Shell>(listOf())

fun startShell(shell: Shell) {
    shells.add(shell)
    thread {
        shell.run()

    }
}