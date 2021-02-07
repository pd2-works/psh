package ink.pd2.shell.buildin;

import ink.pd2.shell.core.MarkProvider;

import java.util.HashMap;

public class VariableMarkProvider implements MarkProvider {
	public final static VariableMarkProvider INSTANCE = new VariableMarkProvider();

	private final HashMap<String, String> variables = new HashMap<>();
	public HashMap<String, String> getVariables() {
		return variables;
	}

	public String getSign() {
		return "v";
	}

	public String onMarkUpdate(String value) {
		String variable = variables.get(value);
		if (variable == null) {
			return "&v:" + value + "&";
		}
		return variable;
	}
}
