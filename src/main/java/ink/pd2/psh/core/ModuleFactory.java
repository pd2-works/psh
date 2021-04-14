package ink.pd2.psh.core;

import java.io.InputStream;
import java.util.Map;

public class ModuleFactory {
	private InputStream xmlStream = null;
	private final ModuleConfig config;
	
	public ModuleFactory(InputStream xmlStream) {
		this.xmlStream = xmlStream;
		config = null; //TODO
	}
	public ModuleFactory(ModuleConfig config) {
		this.config = config;
	}
	
	private void readConfig() {
		
	}

	public static class ModuleConfig {
		public static int versionCode = 0;
		public static String mainClass;
		public static String name;
		public static String description;
		public static Map<String, String[]> functions;
	}
}
