package ink.pd2.shell.core

import ink.pd2.shell.log.writeDebugLog
import ink.pd2.shell.log.writeLog

/**
 * ## Resources | 全局资源
 *
 * 用于集中管理Shell中的全局资源, 目前有监听器(Listener)/指令(Command)/字符串(String)资源可用.
 *
 * ### 用法
 *
 * 每个资源都有相对应的 `key` 和 `value`, `key` 为资源的标记, `value` 即是资源对象本身.
 *
 * 通过基本的4个方法
 *
 * 1. `Resources.getXXX(key)`
 * 2. `Resources.putXXX(key, value)`
 * 3. `Resources.addXXX(key, value)`
 * 4. `Resources.removeXXX(key)`
 *
 * 来获取和管理资源(其中 `put` / `add` 方法对于任意资源类型不一定都存在,
 * 不同资源也可能有一些不同的特殊操作方法).
 *
 * 监听器/指令资源带有资源组机制(详见 `Groups` 子对象), `key` 的命名规则需遵循资源组限制,
 * 否则将会抛出 `ResourceKeyFormatException` 异常.
 *
 * @see ink.pd2.shell.core.Resources.Groups
 * @see ink.pd2.shell.core.ResourceKeyFormatException
 *
 * @author Maxel Black
 * @since PSH 1.0
 */

object Resources {

	/**
	 * ## Resources.Groups | 资源组管理
	 *
	 * 用于支持全局资源的资源组机制, 使资源管理更加方便
	 *
	 * ### 用法
	 *
	 * 每个插件都需要指定自身的资源组名称, 可以直接在插件初始化中指定 `resourcesGroup` 变量.
	 *
	 * 也可直接添加资源组, 但**不建议这样做**. 若无特殊需求和作用,
	 * 这样会增加资源管理的繁琐程度, 并降低插件的通用兼容性. 如确实有需求,
	 * **整个插件初始化过程的最前面**是最佳的添加组方法( `add(groupName)` )调用位置
	 *
	 * @see ink.pd2.shell.core.Resources
	 * @see ink.pd2.shell.plugin.Plugin.resourcesGroup
	 * @see ink.pd2.shell.core.ResourceKeyFormatException
	 *
	 * @author Maxel Black
	 * @since PSH 1.0
	 */

	//资源组
	object Groups {
		private val groups = HashSet<String>(setOf())
		fun add(groupName: String) {
			//判断组名是否符合规范 | 添加组
			if (groupName.contains('.') || groupName.contains('\n')) {
				throw ResourceKeyFormatException("Group name CANNOT contains any dot(.) or '\\n' character.")
			} else {
				groups += groupName
			}
		}

		fun contains(name: String): Boolean {
			//判断组名是否存在
			return groups.contains(name)
		}

		fun remove(name: String): Boolean {
			//移除组
			return groups.remove(name)
		}

		fun getAllGroupsName(): Set<String> {
			//获取所有组名
			return groups.toSet()
		}
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

	fun getCommands(): Set<Command> {
		//获取所有指令
		return commands.values.toSet()
	}

	fun getCommands(command: String): List<Command> {
		//获取同名的所有指令
		val commands = ArrayList<Command>(listOf())
		for(c in this.commands.values) {
			if (c.name == command) commands += c
		}
		return commands
	}

	fun putCommand(group: String, vararg c: Command) {
		if (!Groups.contains(group))
			throw ResourceKeyFormatException("Group name does NOT exist.")
		for (i in c) {
			commands["$group:${i.name}"] = i
		}
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

	fun putListener(key: String, value: Listener) {
		//判断key是否符合规范 | 添加对象
		val skey = key.split('.')
		if (skey.size > 2) {
			if (Groups.contains(skey[0])) {
				listeners[key] = value
			} else {
				throw ResourceKeyFormatException("Group name does NOT exist.")
			}
		} else {
			throw ResourceKeyFormatException("The key's format does NOT match the standard.")
		}
		writeLog("Resources<Listener>", "$key -> $value")
	}

	fun removeListener(key: String): Listener? {
		//移除对象
		val listener = listeners.remove(key)
		writeDebugLog("Resources<Listener>", "^ $key : ${listener != null}")
		return listener
	}
}
