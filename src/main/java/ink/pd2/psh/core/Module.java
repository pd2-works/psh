package ink.pd2.psh.core;

public abstract class Module {
	protected final int id = ModuleBoard.newRandomId();

	protected abstract void onInitial() throws Exception;

	protected void onCalling(String[] signal) {}

}
