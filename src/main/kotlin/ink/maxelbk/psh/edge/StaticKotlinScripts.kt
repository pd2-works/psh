package ink.maxelbk.psh.edge

object StaticKotlinScripts {
    object Logger {
        fun writeLog(location: String, info: String) {
            ink.pd2.shell.log.writeLog(location, info)
        }
        fun writeException(location: String, exception: Exception) {
            ink.pd2.shell.log.writeException(location, exception)
        }
    }
}