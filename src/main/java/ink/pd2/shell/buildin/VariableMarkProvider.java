package ink.pd2.shell.buildin;

import ink.pd2.shell.core.Mark;
import ink.pd2.shell.core.MarkProvider;

import java.util.HashMap;

public class VariableMarkProvider implements MarkProvider {
	public final static VariableMarkProvider INS = new VariableMarkProvider();

	private final HashMap<String, String> variables = new HashMap<>();
	public HashMap<String, String> getVariables() {
		return variables;
	}

	//TODO 基于对象区分的环境变量层叠式分配

	public String getSign() {
		return "v";
	}

	public String onMarkUpdate(Mark mark, String value) {

		String variable = variables.get(value);
		if (variable == null) {
			return "\\&v:" + value + "\\&";
		}
		return variable;
	}
}
