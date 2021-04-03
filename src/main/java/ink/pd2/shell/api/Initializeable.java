package ink.pd2.shell.api;

import java.io.File;

public interface Initializeable {
	void onInit() throws InitializationException;
	void onInitLanguage(File[] files) throws InitializationException;

	String getResourcesId();
	int getVersionCode();
}
