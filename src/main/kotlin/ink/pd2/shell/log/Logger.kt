package ink.pd2.shell.log

import java.util.*
import kotlin.Exception

fun writeLog(location: String, info: String) {
    println("[${Date()}][$location] $info")
}

fun writeDebugLog(location: String, info: String) {
}

fun writeException(location: String, exception: Exception) {
}
