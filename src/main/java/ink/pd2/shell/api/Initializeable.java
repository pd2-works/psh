package ink.pd2.shell.api;

import java.io.File;

public interface Initializeable {
	void init() throws PluginInitializationException;
	void initLanguage(File[] files) throws PluginInitializationException;

	String getResourcesGroup();
	int getVersionCode();

	File[] getI18nFiles();
}
