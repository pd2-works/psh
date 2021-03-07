package ink.pd2.shell.core;

public abstract class CommandEnteredListener implements Listener {

	@Override
	public String getType() {
		return "command-entered";
	}

	/**
	 * <h2>getPriority() | 事件执行的优先级</h2>
	 *
	 * <p>可用的优先级有 {@code PRIORITY_HIGH} (高优先级),
	 * {@code PRIORITY_MEDIUM} (中等优先级),
	 * {@code PRIORITY_LOW} (低优先级)</p>
	 *
	 * @return 优先级
	 *
	 * @see ink.pd2.shell.core.Shell
	 *
	 * @since PSH 1.0
	 */

	public abstract int getPriority();

	/**
	 * <h2>event() | 事件活动</h2>
	 *
	 * @param shell 执行指令的Shell对象
	 * @param command 执行的指令对象
	 *
	 * @return 下一步操作: true为继续运行, false为退出, null为跳过其他事件直接进入下一指令输入过程
	 *
	 * @since PSH 1.0
	 */

	public abstract Boolean event(Shell shell, String command);
}
