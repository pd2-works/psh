package ink.maxelbk.psh.api

object KotlinScripts {
	object Logger {
		fun writeLog(location: String, info: String) {
			ink.pd2.shell.log.writeLog(location, info)
		}
		fun writeException(location: String, exception: Exception) {
			ink.pd2.shell.log.writeException(location, exception)
		}
	}
	object Main {
		fun exit(status: Int, reason: String) {
			ink.pd2.shell.exit(status, reason)
		}
	}
}
