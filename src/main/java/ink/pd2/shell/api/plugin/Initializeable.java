package ink.pd2.shell.api.plugin;

import java.io.File;

public interface Initializeable {
	void init();
	void initLanguage(File[] files);

	String getResourcesGroup();
	int getVersionCode();
}
