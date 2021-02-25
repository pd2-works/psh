package ink.pd2.shell.core;

import ink.pd2.shell.api.Command;

import java.util.*;

/**
 * <h2>Resources | 全局资源</h2>
 *
 * <p>用于集中管理Shell中的全局资源, 目前有监听器(Listener)/指令(Command)/字符串(String)资源可用.</p>
 *
 * <h3>Resources | 全局资源</h3>
 *
 * <p>每个资源都有相对应的 {@code key} 和 {@code value}, {@code key} 为资源的标记,
 * {@code value} 即是资源对象本身.</p>
 *
 * <p>通过基本的4个方法:<br/>
 *
 * 1. {@code Resources.getXXX(key)} <br/>
 * 2. {@code Resources.putXXX(key, value)} <br/>
 * 3. {@code Resources.addXXX(key, value)} <br/>
 * 4. {@code Resources.removeXXX(key)} <br/>
 *
 * 来获取和管理资源(其中 {@code put} / {@code add} 方法对于任意资源类型不一定都存在,
 * 不同资源也可能有一些不同的特殊操作方法).</p>
 *
 * <p>监听器/指令资源带有资源组机制(详见 {@code Groups} 子对象), {@code key} 的命名规则需遵循资源组限制,
 * 否则将会抛出 {@code ResourceKeyFormatException} 异常.</p>
 *
 * @see groups
 * @see ResourceException
 *
 * @author Maxel Black
 * @since PSH 1.0
 */

public final class Resources {
	public final static Resources INS = new Resources();

	/**
	 * <h2>Resources.Groups | 资源组管理</h2>
	 *
	 * <p>用于支持全局资源的资源组机制, 使资源管理更加方便</p>
	 *
	 * <h3>用法</h3>
	 *
	 * <p>每个插件都需要指定自身的资源组名称, 可以直接在插件初始化中指定 {@code resourcesGroup} 变量.</p>
	 *
	 * <p>也可直接添加资源组, 但<b>不建议这样做</b>. 若无特殊需求和作用,
	 * 这样会增加资源管理的繁琐程度, 并降低插件的通用兼容性. 如确实有需求,
	 * 建议在<b>整个插件初始化过程的最前面</b>调用添加资源组的方法( {@code add(groupName)} )</p>
	 *
	 * @see ink.pd2.shell.core.Resources
	 * @see ink.pd2.shell.api.Plugin
	 * @see ResourceException
	 *
	 * @author Maxel Black
	 * @since PSH 1.0
	 */

	public static final class groups {
		private static Set<String> groups = new HashSet<>();
		public static void add(String groupName) {
			//判断组名是否符合规范并添加组
			if (groupName.contains(".") || groupName.contains("\n")) {
				throw new ResourceException(
						"Group name CANNOT contains any dot(.) or '\\n' character.");
			} else {
				groups.add(groupName);
			}
		}

		public static boolean contains(String name) {
			//判断组名是否存在
			return groups.contains(name);
		}

		public static boolean remove(String name) {
			//移除组
			return groups.remove(name);
		}
	}

	//字符串
	private final HashMap<String, String> strings = new HashMap<>();
	public String getString(String key) {
		//获取字符串
		String string = strings.get(key);
		Logger.INS.debug("Resources<String>",
				"&nomark&^ " + key + " : " + (string != null));
		return string == null? "" : string;
	}

	public void putString(String key, String value) {
		//添加字符串
		strings.put(key, value);
		Logger.INS.debug("Resources<String>",
				"&nomark&" + key + " -> \"" + value + "\"");
	}

	public String removeString(String key) {
		//移除字符串
		Logger.INS.debug("Resources<String>",
				"&nomark&- " + key + " : " + strings.containsKey(key));
		return strings.remove(key);
	}

	//指令
	private final HashMap<String, Command> commands = new HashMap<>();
	private Command getCommand(String key) {
		//获取对象
		Command command = commands.get(key);
		Logger.INS.debug("Resources<Command>",
				"&nomark&^ " + key + " : " + (command != null));
		return command;
	}
	//TODO 获取指令

	public void putCommand(Command... c) {
		for (Command i : c) {
			String group = i.getGroup();
			if (!groups.contains(group))
				throw new ResourceException("Group name does NOT exist.");
			commands.put(group + ":" + i.getName(), i);
		}
	}

	//监听器
	private final HashMap<String, ArrayList<Listener>> listeners = new HashMap<>();
	public List<Listener> getListeners(String key) {
		//获取指定组类全部对象
		ArrayList<Listener> list = listeners.get(key);
		Logger.INS.debug("Resources<Listener>",
				"&nomark&^ " + key + " : " + !list.isEmpty());
		return list;
	}

	public void addListener(String key, Listener value) {
		//添加对象
		if (listeners.containsKey(key)) {
			ArrayList<Listener> list = listeners.get(key);
			if (list == null) {
				throw new ResourceException("Listener type has NOT ever been reg");
			}
		} else {
			throw new ResourceException("Group name does NOT exist.");
		}
		Logger.INS.debug("Resources<Listener>",
				"&nomark&" + key + " += " + value);
	}

	public boolean removeListener(String key, Listener value) {
		//移除对象
		boolean b = listeners.get(key).remove(value);
		Logger.INS.debug("Resources<Listener>",
				"&nomark&- " + key + " : " + b);
		return b;
	}
}
