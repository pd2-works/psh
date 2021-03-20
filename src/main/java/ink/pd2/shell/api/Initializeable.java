package ink.pd2.shell.api;

import java.io.File;

public interface Initializeable {
	void init() throws InitializationException;
	void initLanguage(File[] files) throws InitializationException;

	String getResourcesId();
	int getVersionCode();

	File[] getI18nFiles();
}
