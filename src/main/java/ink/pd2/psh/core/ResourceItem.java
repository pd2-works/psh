package ink.pd2.psh.core;

import java.util.HashMap;
import java.util.Set;

public class ResourceItem<T> {
    protected int moduleId;
    private final HashMap<String, T> valueMap = new HashMap<>();

    /* |<- 常规功能 ->|*/

    public T get(int cid, String key) {
        if (moduleId != cid && !Permission.check(
                cid, Permission.READ_GLOBAL_RESOURCES)) return null;
        return valueMap.get(key);
    }

    public T put(int cid, String key, T value) {
        if (moduleId != cid && !Permission.check(
                cid, Permission.WRITE_GLOBAL_RESOURCES)) return null;
        return valueMap.put(key, value);
    }

    public Set<String> keySet(int cid) {
        if (moduleId != cid && !Permission.check(
                cid, Permission.READ_GLOBAL_RESOURCES)) return null;
        return valueMap.keySet();
    }

    public boolean containsKey(int cid, String key) {
        if (moduleId != cid && !Permission.check(
                cid, Permission.READ_GLOBAL_RESOURCES)) return false;
        return valueMap.containsKey(key);
    }

    public boolean containsValue(int cid, T value) {
        if (moduleId != cid && !Permission.check(
                cid, Permission.READ_GLOBAL_RESOURCES)) return false;
        return valueMap.containsValue(value);
    }

    /* |<- 增强功能 ->|*/

    public String getValueAsString(int cid, String key) {
        Object object = get(cid, key);
        return (object == null)? null : object.toString();
    }

}
