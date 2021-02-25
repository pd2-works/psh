package ink.pd2.shell.core;

public interface MarkProvider {

	/**
	 * <h2>getSign() | 获取记号</h2>
	 *
	 * 如题, 用于获取这个标记类型的记号
	 *
	 * @return 记号字符
	 *
	 * @author Maxel Black (maxelblack@outlook.com)
	 */

	String getSign();

	/**
	 * <h2>onMarkUpdate() | 在更新标记时的事件</h2>
	 *
	 * 在执行字符串标记更新时, 这个事件会被调用以获取用于替换的字符
	 *
	 * @param mark 执行更新操作的Mark对象
	 * @param value 被标记的字符串
	 *
	 * @return 用于替换的字符串
	 *
	 * @author Maxel Black (maxelblack@outlook.com)
	 */

	String onMarkUpdate(Mark mark, String value);
}
