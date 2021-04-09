package ink.pd2.psh.core;

import java.util.HashMap;

public final class Resources {
    protected Module module;
    private final HashMap<String, ResourceItem<?>> itemMap = new HashMap<>();
    
    protected final void putItem(String name, ResourceItem<?> object) {
        object.moduleId = module.id;
        itemMap.put(name, object);
    }
    
    public final ResourceItem<?> getItem(String name) {
        return itemMap.get(name);
    }
    
    public final String getItemValueAsString(String key) {
        String[] tmp = key.split("/", 2);
        if (tmp.length != 2) return null;
        ResourceItem<?> item = itemMap.get(tmp[0]);
        if (item == null) return null;
        return item.getValueAsString(module.id, key);
    }
}
