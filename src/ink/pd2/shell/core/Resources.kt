package ink.pd2.shell.core

import ink.pd2.shell.log.writeLog

object Resources {
    //字符串
    private val strings = HashMap<String, String>(mapOf())
    fun getString(key: String): String {
        val string = strings[key]
        writeLog("Resources<String>", "^ $key : ${string != null}")
        return  string ?: return ""
    }
    fun putString(key: String, value: String) {
        strings[key] = value
        writeLog("Resources<String>", "$key -> \"$value\"")
    }
    fun removeString(key: String): String? {
        writeLog("Resources<String>", "- $key : ${strings.containsKey(key)}")
        return strings.remove(key)
    }

    //监听器
    private val listeners = HashMap<String, Listener>(mapOf())
    fun getListener(key: String): Listener? {
        val listener = listeners[key]
        writeLog("Resources<Listener>", "^ $key : ${listener != null}")
        return listener
    }
    fun getListeners(fkey: String): List<Listener> {
        val list = ArrayList<Listener>(listOf())
        for (key in listeners.keys) {
            if (key.split('.')[0] == fkey) {
                val l = listeners[key]
                if (l != null) list.add(l)
            }
        }
        writeLog("Resources<Listener>", "^ $fkey : ${list.isNotEmpty()}")
        return list
    }
    fun putListener(key: String, listener: Listener) {
        listeners[key] = listener
        writeLog("Resources<Listener>", "$key -> $listener")
    }
    fun removeListener(key: String): Listener? {
        val listener = listeners.remove(key)
        writeLog("Resources<Listener>", "^ $key : ${listener != null}")
        return listener
    }
}
