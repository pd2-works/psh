package ink.pd2.psh.core;

public abstract class Module {
    public final int id;
    public final int versionCode;

    protected Module(int id, int versionCode) {
        this.id = id;
        this.versionCode = versionCode;
    }

    public abstract void onInitial() throws Exception;

}
