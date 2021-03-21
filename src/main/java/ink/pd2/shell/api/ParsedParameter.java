package ink.pd2.shell.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ParsedParameter {
	private final HashMap<Option, ArrayList<String>> optionMap = new HashMap<>();

	protected ParsedParameter() { }

	//添加
	public ArrayList<String> addOption(Option option, String... values) {
		ArrayList<String> valueList = new ArrayList<>(1);

		if (values.length != 0) {
			if (values.length == 1) valueList.add(values[0]);
			else valueList.addAll(Arrays.asList(values));
		}

		return optionMap.put(option, valueList);
	}
	public boolean addOptionValue(Option option, String... values) {
		ArrayList<String> valueList = optionMap.get(option);
		if (valueList == null) return false;

		if (values.length == 0) return false;
		if (values.length == 1) return valueList.add(values[0]);
		return valueList.addAll(Arrays.asList(values));
	}

	//获取
	public String[] getOptionValues(Option option) {
		ArrayList<String> values = optionMap.get(option);
		if (values == null) return null;
		return values.toArray(new String[0]);
	}
	public String getOptionValue(Option option) {
		ArrayList<String> values = optionMap.get(option);
		if (values == null) return null;
		return values.get(0);
	}

	//移除
	public String removeOptionValue(Option option, int index) {
		ArrayList<String> values = optionMap.get(option);
		if (values == null) return null;
		String temp = values.remove(index);
		if (values.size() == 0) optionMap.remove(option);
		return temp;
	}
	public ArrayList<String> removeOption(Option option) {
		return optionMap.remove(option);
	}

	//get & set
	public HashMap<Option, ArrayList<String>> getOptionMap() {
		return optionMap;
	}
}
