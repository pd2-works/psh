package ink.pd2.shell.buildin;

import ink.pd2.shell.core.Mark;

import java.util.HashMap;

public class VariableMark extends Mark {
	private final VariableMark parent; //上级标记对象
	private final HashMap<String, String> variables = new HashMap<>(); //本级变量存储

	protected VariableMark(VariableMark parent) {
		this.parent = parent;
	}

	public String get(String key) {
		String v = variables.get(key);
		if (v == null)
			if (parent == null) return null;
			else return parent.get(key);
		return v;
	}
	public String put(String key, String value) {
		return variables.put(key, value);
	}
	public String remove(String key) {
		return variables.remove(key);
	}

	public VariableMark getParent() {
		return parent;
	}

	public VariableMark newChild() {
		return new VariableMark(this);
	}
}
