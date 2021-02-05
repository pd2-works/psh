package ink.pd2.shell.log

import ink.pd2.shell.println

import java.io.PrintWriter
import java.io.StringWriter
import java.util.*
import kotlin.Exception

fun writeDebugLog(location: String, info: String) {
//	println("[&color:blue.null[${getDate(true)}]&] [&color:yellow.null[Debug]&|&color:cyan.null[$location]&] $info")
}

fun writeLog(location: String, info: String) {
	println("[&color:blue.null[${getDate()}]&] [&color:green.null[Info]&|&color:cyan.null[$location]&] $info")
}

fun writeErrorLog(location: String, info: String) {
	println("[&color:blue.null[${getDate()}]&] [&color:red.null[Error]&|&color:cyan.null[$location]&] $info")
}

fun writeException(location: String, exception: Exception) {
	val s = StringWriter()
	val p = PrintWriter(s)
	exception.printStackTrace(p)
	writeDebugLog(location, "&color:red.null[EXCEPTION:\n$s]&")
}

private fun getDate(ms: Boolean = false): String {
	val d = Date()
	if (ms) return String.format("%tF %tT.%tL %tZ", d, d, d, d)
	return String.format("%tF %tT %tZ", d, d, d)
}
