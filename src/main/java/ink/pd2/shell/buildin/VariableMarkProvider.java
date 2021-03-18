package ink.pd2.shell.buildin;

import ink.pd2.shell.core.Logger;
import ink.pd2.shell.core.Mark;
import ink.pd2.shell.core.MarkProvider;

public class VariableMarkProvider implements MarkProvider {
	public final static VariableMarkProvider INS = new VariableMarkProvider();

	private final VariableMark variables = new VariableMark(null);
	public VariableMark getVariables() {
		return variables;
	}

	public String getSign() {
		return "v";
	}

	public String onMarkUpdate(Mark mark, String value) {
		if (mark instanceof VariableMark) {
			String variable = ((VariableMark) mark).get(value);
			if (variable == null) {
				return "\\&v:" + value + "\\&";
			}
			return variable;
		}
		else {
			Logger.INS.error("Variable", mark + " is NOT a VariableMark object.");
			return "";
		}
	}
}
