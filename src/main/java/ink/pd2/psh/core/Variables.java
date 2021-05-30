package ink.pd2.psh.core;

import java.util.HashMap;
import java.util.HashSet;

public class Variables {
	private final Variables parent;

	private final HashSet<Variables> children = new HashSet<>(0);

	private Variables(Variables parent) {
		this.parent = parent;
	}

	public Variables getParent() {
		return parent;
	}

	public Variables newChild() {
		Variables child = new Variables(this);
		children.add(child);
		return child;
	}

	public Variables[] getChildren() {
		return children.toArray(new Variables[0]);
	}

	/*==============================================*/

	private final HashMap<String, String> variableMap = new HashMap<>();

	public String get(String key) {
		String r = variableMap.get(key);
		return ((r == null)? ((parent == null)? null : parent.get(key)) : r);
	}

	public String put(String key, String value) {
		return variableMap.put(key, value);
	}

	public Variables contains(String key) {
		if (variableMap.containsKey(key)) return this;
		return ((parent == null)? null : parent.contains(key));
	}
}
