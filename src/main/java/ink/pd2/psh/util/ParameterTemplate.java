package ink.pd2.psh.util;

import java.util.Arrays;
import java.util.LinkedList;

public class ParameterTemplate {
	private final LinkedList<Option> options = new LinkedList<>();
	private Option defaultOption = null;

	public ParameterTemplate() { }
	public ParameterTemplate(Option option) {
		options.add(option);
	}
	public ParameterTemplate(Option... options) {
		this.options.addAll(Arrays.asList(options));
	}

	public void addOption(Option option) {
		options.add(option);
	}
	public void addOption(Option... options) {
		this.options.addAll(Arrays.asList(options));
	}
	public void removeOption(Option option) {
		options.remove(option);
	}
	public Option[] getAllOptions() {
		return options.toArray(new Option[0]);
	}

	//get & set
	public LinkedList<Option> getOptionList() {
		return options;
	}
	public Option getDefaultOption() {
		return defaultOption;
	}
	public void setDefaultOption(Option defaultOption) {
		this.defaultOption = defaultOption;
	}
}
