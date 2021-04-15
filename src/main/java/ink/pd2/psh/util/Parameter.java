package ink.pd2.psh.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class Parameter {
	//指令参数
	private final String command;
	private String name;
	protected final LinkedList<String> args;

	//参数解析相关
	private ParsedParameter parsedParameter = null;

	public Parameter(String command) {
		this.command = command;

		LinkedList<String> list = new LinkedList<>();
		String[] temp = {"", command};
		t: while (true) {
			temp = temp[1].split(" ", 2);
			switch (temp.length) {
				case 1:
					list.add(temp[0]);
					break t;
				case 2:
					list.add(temp[0]);
					if ((!temp[1].isEmpty())
							&& temp[1].startsWith("\"")
							&& temp[1].length() > 1) {
						String[] temp1 = temp[1].substring(1).split("\"", 2);
						list.add(temp1[0]);
						if (temp1.length == 2) {
							temp[1] = temp1[1];
						}
					}
			}
		}
		list.removeIf(String::isEmpty); //忽略所有多余空格(即长度为零的参数)

		name = list.size() == 0 ? null : list.removeFirst();
		args = list;
	}
	public Parameter(String[] args) {
		command = null;
		name = null;
		(this.args = new LinkedList<>()).addAll(Arrays.asList(args));
	}

	public ParsedParameter parseParameter(ParameterTemplate template)
			throws ParameterException {
		//加载参数列表
		HashMap<String, Option> optionMap = new HashMap<>();
		for (Option i : template.getOptionList()) {
			if (i.name != null) optionMap.put(i.name, i);
			if (i.shortName != null) optionMap.put(i.shortName.toString(), i);
		}
		ParsedParameter parsed = new ParsedParameter();

		//解析参数
		HashMap<Option, ArrayList<String>> tempMap = parsed.getOptionMap();
		Option tempOption = null;
		Option tempDefaultOption = template.getDefaultOption();

		for (String i : args) {
			if (i.charAt(0) == '-') {
				// 1. 参数
				int length = i.length();
				String sub;
				if (length > 1 && i.charAt(1) == '-') {
					// 1) --xxx 型
					if (length == 2) continue;
					sub = i.substring(2);
				} else {
					// 2) -x 型
					//TODO POSIX风格复合参数支持
					if (length == 1) continue;
					sub = i.substring(1);
				}
				Option temp = optionMap.get(sub);
				if (temp == null) throw new ParameterException("0: Option does NOT exist.");
				//添加参数
				tempOption = temp;
				tempMap.computeIfAbsent(temp, k -> new ArrayList<>());
			} else {
				// 2. 字符串值
				if (tempOption == null) {
					// 1) 第一个参数
					if (tempDefaultOption == null) continue;
					tempOption = tempDefaultOption;
					tempMap.computeIfAbsent(tempDefaultOption, k -> new ArrayList<>());
				}
				// 2) 非第一个参数
				tempMap.get(tempOption).add(i);
			}
		}
		for (Option i : tempMap.keySet()) {
			ArrayList<String> list = tempMap.get(i);
			if (list.isEmpty() && i.defaultValue != null) list.add(i.defaultValue);
		}

		//返回结果
		parsedParameter = parsed;
		return parsed;
	}

	//get & set
	public String getCommandName() {
		return name;
	}
	public void setCommandName(String name) {
		this.name = name;
	}
	public String getCommandLine() {
		return command;
	}
	public String[] getArguments() {
		return args.toArray(new String[0]);
	}
	public ParsedParameter getParsedParameter() {
		return parsedParameter;
	}

}
