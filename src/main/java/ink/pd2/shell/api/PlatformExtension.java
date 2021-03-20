package ink.pd2.shell.api;

import ink.pd2.shell.core.i18n.Language;
import ink.pd2.shell.util.PluginUtils;

import java.io.File;

public abstract class PlatformExtension implements Extension {
	@Override
	public void initLanguage(File[] files) throws InitializationException {
		try {
			for (File file : files) {
				PluginUtils.INS.loadLanguage(new Language(file));
			}
		} catch (Exception e) {
			throw new InitializationException(
					"An exception has been thrown while an extension of the plugin '"
							+ getResourcesId() + "' is initializing.", e);
		}
	}

	@Override
	public final String getResourcesId() {
		return "psh";
	}

	@Override
	public abstract int getVersionCode();

}
