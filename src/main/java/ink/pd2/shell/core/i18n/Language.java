package ink.pd2.shell.core.i18n;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Properties;

public class Language {
	private final Properties lang;

	public Language(File file) throws IOException {
		Properties lang = new Properties();
		InputStream stream = new FileInputStream(file);
		Reader reader = new InputStreamReader(stream, Charset.defaultCharset());
		lang.load(reader);
		this.lang = lang;
	}

	public Properties getProperties() {
		return lang;
	}
}
