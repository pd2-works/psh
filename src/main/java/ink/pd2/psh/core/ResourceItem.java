package ink.pd2.psh.core;

import java.util.HashMap;
import java.util.Set;

public class ResourceItem<T> {
    protected int moduleId;
    private final HashMap<String, T> valueMap = new HashMap<>();

    /* |<- 常规功能 ->|*/

    public T get(int id, String key) {
        if (moduleId != id && Permission.check(
                id, Permission.READ_GLOBAL_RESOURCES)) return null;
        return valueMap.get(key);
    }

    public T put(int id, String key, T value) {
        if (moduleId != id && Permission.check(
                id, Permission.WRITE_GLOBAL_RESOURCES)) return null;
        return valueMap.put(key, value);
    }

    public Set<String> keySet(int id) {
        if (moduleId != id && Permission.check(
                id, Permission.READ_GLOBAL_RESOURCES)) return null;
        return valueMap.keySet();
    }

    public boolean containsKey(int id, String key) {
        if (moduleId != id && Permission.check(
                id, Permission.READ_GLOBAL_RESOURCES)) return false;
        return valueMap.containsKey(key);
    }

    public boolean containsValue(int id, T value) {
        if (moduleId != id && Permission.check(
                id, Permission.READ_GLOBAL_RESOURCES)) return false;
        return valueMap.containsValue(value);
    }

    /* |<- 增强功能 ->|*/

    public String getValueAsString(int id, String key) {
        Object object = get(id, key);
        return (object == null)? null : object.toString();
    }

}
