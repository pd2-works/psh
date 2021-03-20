package ink.pd2.shell.api;

public interface Extension extends Initializeable {
	@Override
	default void init() throws InitializationException {
		//TODO 把扩展功能加入插件
	}
}
