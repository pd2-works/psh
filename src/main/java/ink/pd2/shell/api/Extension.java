package ink.pd2.shell.api;

import ink.pd2.shell.buildin.CorePlugin;
import ink.pd2.shell.core.Resources;
import ink.pd2.shell.core.i18n.Language;
import ink.pd2.shell.util.PluginUtils;

import java.io.File;

public interface Extension extends Initializeable {
	@Override
	default void init() throws InitializationException {
		Plugin plugin = CorePlugin.getPluginList().get(getResourcesId());
		if (plugin == null)
			throw new InitializationException(
					"The plugin with resources ID '" + getResourcesId() + "' was NOT found");
		Resources.INS.addExtension(
				CorePlugin.getPluginList().get(getResourcesId()), this);
	}

	@Override
	default void initLanguage(File[] files) throws InitializationException {
		try {
			for (File file : files) {
				PluginUtils.INS.loadLanguage(new Language(file));
			}
		} catch (Exception e) {
			throw new InitializationException(
					"An exception has been thrown while an extension of resources ID '"
							+ getResourcesId() + "' is initializing.", e);
		}
	}

	@Override
	default int getVersionCode() {
		return CorePlugin.getPluginList().get(getResourcesId()).getVersionCode();
	}

	Object getObject(Object... args);
	Object getType();

}
