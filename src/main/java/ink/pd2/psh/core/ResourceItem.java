package ink.pd2.psh.core;

public abstract class ResourceItem<T> {
	protected int moduleId;

	/* |<- 常规功能 ->|*/
	public abstract T get(int cid, String key);
	public abstract String[] getKeys(int cid);
	public abstract boolean containsKey(int cid, String key);
//	public abstract boolean containsKeys(int cid, String... keys);
	public abstract boolean containsValue(int cid, T value);

	/* |<- 增强功能 ->|*/
	public String getValueAsString(int cid, String key) {
		Object object = get(cid, key);
		return (object == null)? null : object.toString();
	}

	protected final boolean isNoReadPermission(int cid) {
		return moduleId != cid &&
				!Permission.check(cid, Permission.READ_GLOBAL_RESOURCES);
	}

	protected final boolean isNoWritePermission(int cid) {
		return moduleId != cid &&
				!Permission.check(cid, Permission.WRITE_GLOBAL_RESOURCES);
	}
}
