package ink.pd2.shell.core

import ink.pd2.shell.log.writeLog

object Resources {
    //资源组列表
    private val groups = HashSet<String>(setOf())
    fun addGroup(groupName: String) {
        if (groups.contains(groupName)) {
            groups += groupName
        }
    }
    fun getGroups(): Set<String> {
        return groups
    }

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
    fun getListeners(group: String, fkey: String): List<Listener> {

        val list = ArrayList<Listener>(listOf())
        for (key in listeners.keys) {
            val skey = key.split('.')
            if (skey[0] == group && skey[1] == fkey) {
                val l = listeners[key]
                if (l != null) list.add(l)
            }
        }
        writeLog("Resources<Listener>", "^ $fkey : ${list.isNotEmpty()}")
        return list
    }
    fun putListener(key: String, listener: Listener) {
        //判断key是否符合规范 | 添加对象
        val skey = key.split('.')
        if (skey.size > 2) {
            if (groups.contains(skey[0])) {
                listeners[key] = listener
            } else {
                throw ResourceKeyFormatException("Group name does NOT exist.")
            }
        } else {
            throw ResourceKeyFormatException("The key's format does NOT match the standard.")
        }
        writeLog("Resources<Listener>", "$key -> $listener")
    }
    fun removeListener(key: String): Listener? {
        //移除对象
        val listener = listeners.remove(key)
        writeLog("Resources<Listener>", "^ $key : ${listener != null}")
        return listener
    }
}
