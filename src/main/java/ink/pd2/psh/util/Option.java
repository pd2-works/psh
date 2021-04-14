package ink.pd2.psh.util;

public class Option {
	public String name;
	public Character shortName;
	public String description;

	public boolean required;
	public String defaultValue;

	public Option(String name, Character shortName) {
		this(name, shortName, null);
	}
	public Option(String name, Character shortName, String defaultValue) {
		this(name, shortName, defaultValue, false);
	}
	public Option(String name, Character shortName, String defaultValue, boolean required) {
		this.name = name;
		this.shortName = shortName;
		this.defaultValue = defaultValue;
		this.required = required;
	}
}
