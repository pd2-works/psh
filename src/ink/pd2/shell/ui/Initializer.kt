package ink.pd2.shell.ui

import ink.pd2.shell.log.writeLog
import javax.swing.UIManager
import java.lang.ClassNotFoundException
import java.lang.InstantiationException
import java.lang.IllegalAccessException
import javax.swing.UnsupportedLookAndFeelException

object Initializer {
    fun initTheme() {
        writeLog("Initializer", Resources.getString("psh.log-init-theme"))

        //初始化主题
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun initResources() {
        writeLog("Initializer", "Initializing resources...")

        //初始化资源
        putStr("psh.log-init-shell", "Initializing new shell...")
        putStr("psh.log-init-theme", "Loading UI theme...")
        putStr("psh.plugin-no-description", "There is no description.")
        putStr("psh.shell-hint-text", "&USER:&HOSTNAME$ ")

        writeLog("Initializer", "Resources initialization finished.")
    }
    private fun putStr(k: String, v: String) {
        Resources.putString(k, v)
    }
}
