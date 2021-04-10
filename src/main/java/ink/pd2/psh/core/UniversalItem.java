package ink.pd2.psh.core;

import java.util.HashMap;

public class UniversalItem<T> extends ResourceItem<T> {
    private final HashMap<String, T> valueMap = new HashMap<>();

    @Override
    public T get(int cid, String key) {
        if (isNoReadPermission(cid)) return null;
        return valueMap.get(key);
    }

    public T put(int cid, String key, T value) {
        if (isNoWritePermission(cid)) return null;
        return valueMap.put(key, value);
    }

    @Override
    public String[] getKeys(int cid) {
        if (isNoReadPermission(cid)) return null;
        return valueMap.keySet().toArray(new String[0]);
    }

    @Override
    public boolean containsKey(int cid, String key) {
        if (isNoReadPermission(cid)) return false;
        return valueMap.containsKey(key);
    }

    @Override
    public boolean containsValue(int cid, T value) {
        if (isNoReadPermission(cid)) return false;
        return valueMap.containsValue(value);
    }
}
