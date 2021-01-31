package ink.pd2.shell.log

import java.io.PrintWriter
import java.io.StringWriter
import java.util.*
import kotlin.Exception

fun writeLog(location: String, info: String) {
	println("[${Date()}][$location] $info")
}

fun writeDebugLog(location: String, info: String) {
	println("[${Date()}][$location(Debug)] $info")
}

fun writeException(location: String, exception: Exception) {
	val s = StringWriter()
	val p = PrintWriter(s)
	exception.printStackTrace(p)
	println("[${Date()}][$location] EXCEPTION\n$s")
}
