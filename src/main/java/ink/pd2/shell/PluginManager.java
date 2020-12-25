package ink.pd2.shell;

import ink.pd2.shell.plugin.Plugin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginManager {
    private static final List<Plugin> plugins = new ArrayList<>();

    private static String readJsonFile(String path) throws IOException {
        //初始化文件读取流
        JarFile file = new JarFile(path);
        JarEntry entry = file.getJarEntry("plugin.json");
        InputStreamReader r = new InputStreamReader(
                file.getInputStream(entry), StandardCharsets.UTF_8);

        //读取文件内容
        StringBuilder s = new StringBuilder();
        int tmp;
        while((tmp = r.read()) != -1){
            s.append((char) tmp);
        }

        return s.toString();
    }
}
