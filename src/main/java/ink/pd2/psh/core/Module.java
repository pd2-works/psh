package ink.pd2.psh.core;

public abstract class Module {
	protected final int id = ModuleBoard.newRandomId(this);

	protected abstract void onInitial() throws Exception;

}
