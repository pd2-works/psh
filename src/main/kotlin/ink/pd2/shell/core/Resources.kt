package ink.pd2.shell.core

import ink.pd2.shell.log.writeDebugLog
import ink.pd2.shell.log.writeLog

object Resources {
	//资源组
	private val groups = HashSet<String>(setOf())
	fun addGroup(groupName: String) {
		//判断组名是否符合规范 | 添加组
		if (groupName.contains('.') || groupName.contains('\n')) {
			throw ResourceKeyFormatException("Group name CANNOT contains any dot(.) or '\\n' character.")
		} else {
			groups += groupName
		}
	}
	fun containsGroup(name: String): Boolean {
		//判断组名是否存在
		return groups.contains(name)
	}
	fun removeGroup(name: String): Boolean {
		//移除组
		return groups.remove(name)
	}
	fun getAllGroupsName(): Set<String> {
		//获取所有组名
		return groups.toSet()
	}

	//字符串
	private val strings = HashMap<String, String>(mapOf())
	fun getString(key: String): String {
		//获取字符串
		val string = strings[key]
		writeDebugLog("Resources<String>", "^ $key : ${string != null}")
		return  string ?: return ""
	}
	fun putString(key: String, value: String) {
		//添加字符串
		strings[key] = value
		writeLog("Resources<String>", "$key -> \"$value\"")
	}
	fun removeString(key: String): String? {
		//移除字符串
		writeDebugLog("Resources<String>", "- $key : ${strings.containsKey(key)}")
		return strings.remove(key)
	}

	//指令
	private val commands = HashMap<String, Command>(mapOf())
	fun getCommand(key: String): Command? {
		//获取对象
		val command = commands[key]
		writeDebugLog("Resources<Command>", "^ $key : ${command != null}")
		return command
	}
	fun getCommands(command: String): List<Command> {
		//TODO 获取所有同名指令
		val commands = ArrayList<Command>(listOf())
//		for(c in this.commands.values) {
//			if (c == command) commands += command
//		}
		return commands
	}

	//监听器
	private val listeners = HashMap<String, Listener>(mapOf())
	fun getListener(key: String): Listener? {
		//获取对象
		val listener = listeners[key]
		writeDebugLog("Resources<Listener>", "^ $key : ${listener != null}")
		return listener
	}
	fun getListeners(group: String, type: String): List<Listener> {
		//获取指定组类全部对象
		val list = ArrayList<Listener>(listOf())
		for (key in listeners.keys) {
			val skey = key.split('.')
			if (skey[0] == group && skey[1] == type) {
				val l = listeners[key]
				if (l != null) list.add(l)
			}
		}
		writeDebugLog("Resources<Listener>", "^ $group.$type.* : ${list.isNotEmpty()}")
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
		writeDebugLog("Resources<Listener>", "^ $key : ${listener != null}")
		return listener
	}
}
