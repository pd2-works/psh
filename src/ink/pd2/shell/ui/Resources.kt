package ink.pd2.shell.ui

import ink.pd2.shell.log.writeLog

object Resources {
    //字符串
    private val strings = HashMap<String, String>(mapOf())
    fun getString(key: String): String {
        return strings[key] ?: return ""
    }
    fun putString(key: String, value: String) {
        strings[key] = value
        writeLog("Resources", "$key -> $value")
    }
    fun removeString(key: String): String? {
        return strings.remove(key)
    }
}
