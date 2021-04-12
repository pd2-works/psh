package ink.pd2.psh.core;

public abstract class Module {
	public int id;
	public int versionCode;

	protected abstract void onInitial() throws Exception;

}
