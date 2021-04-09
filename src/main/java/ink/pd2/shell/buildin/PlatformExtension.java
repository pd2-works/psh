package ink.pd2.shell.buildin;

import ink.pd2.shell.api.Extension;

import java.util.Properties;

public abstract class PlatformExtension implements Extension {
    //平台标识
    public static final int PLATFORM_UNIX = 0x00;
    public static final int PLATFORM_LINUX = 0x01;
    public static final int PLATFORM_BSD = 0x02;
    public static final int PLATFORM_WINDOWS = 0x10;
    public static final int PLATFORM_WINDOWS_9X = 0x11;
    public static final int PLATFORM_MACOS = 0x20;
    public static final int PLATFORM_ARCHLINUX = 0xFFFFFFFF;

//	//特性标识
//	public static final int FUNCTION_SYSTEM = 0x01;
//	public static final int FUNCTION_CHDIR = 0x02;
//	public static final int FUNCTION_SU = 0x03;
//	public static final int FUNCTION_SYSTEM_SUDO = 0x04;

    @Override
    public final String getResourcesId() {
        return "psh";
    }

    @Override
    public abstract int getVersionCode();

    @Override
    public final Object run(Object... args) {
        return null;
    }

    //信息
    public abstract int getPlatform();

    public abstract int getVersion();

    //平台特性支持
    public abstract int system(String command);

    public abstract boolean chdir(String path);

    public abstract boolean su(String username);

    public abstract int system_sudo(String command);

    public abstract Properties loadProperties();

    public abstract boolean saveProperties(Properties properties);

}
