package com.plugin.users.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * 类加载器工具类
 *
 * @author zlt
 * @version 1.0
 * @date 2021/10/5
 * <p>
 * Blog: https://zlt2000.gitee.io
 * Github: https://github.com/zlt2000
 */
@Slf4j
public class ClassLoaderUtil {
    public static ClassLoader getClassLoader() {
        String jarPath = Thread.currentThread().getContextClassLoader().getResource("plugins").getPath();
        try {
            File jarFiles = new File(jarPath);
            final File[] files = jarFiles.listFiles();
            if (files == null) {
                return null;
            }
            URL[] urls = new URL[files.length];
            int i = 0;
            for (File file : files) {
                String jarName = file.getName();
                if (!jarName.contains(".jar")) {
                    continue;
                }
                String jarFilePath = "file:\\" + file.getPath();
                urls[i] = new URL(jarFilePath);
                i++;
            }
            return new URLClassLoader(urls);
        } catch (Exception e) {
            log.error("getClassLoader-error", e);
            return null;
        }
    }

    /**
     *
     * @return 获取需要注入的bean
     */
    public static List<String> findLoadClasses() {
        String jarPath = Thread.currentThread().getContextClassLoader().getResource("plugins").getPath();
        List<String> classes = new ArrayList<>();
        try {
            File jarFiles = new File(jarPath);
            final File[] files = jarFiles.listFiles();
            if (files == null) {
                return classes;
            }
            for (File file : files) {
                JarFile jarFile = new JarFile(file);
                final List<String> list = jarFile.stream()
                        .filter(jarEntry -> {
                            String name = jarEntry.getName().toLowerCase();
                            return name.startsWith("com/plugin") && (name.endsWith("handler.class") || name.endsWith("task.class") || name.endsWith("controller.class"));
                        })
                        .map(jarEntry -> jarEntry.getName().replaceAll("/", ".").split(".class")[0])
                        .collect(Collectors.toList());
                classes.addAll(list);
            }
        } catch (IOException e) {
            log.error("find class error", e);
            throw new RuntimeException(e);
        }
        return classes;
    }
}
