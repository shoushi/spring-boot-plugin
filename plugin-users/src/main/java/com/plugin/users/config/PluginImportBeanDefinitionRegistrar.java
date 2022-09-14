package com.plugin.users.config;

import com.plugin.users.utils.ClassLoaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;
import java.util.stream.Collectors;


/**
 * 启动时注册bean
 *
 * @author glq
 * @version 1.0
 */
public class PluginImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar{

    private static final Logger log= LoggerFactory.getLogger(PluginImportBeanDefinitionRegistrar.class);
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        ClassLoader classLoader = ClassLoaderUtil.getClassLoader();
        try {
            Assert.notNull(classLoader, "ClassLoader must not be null");
            final List<String> loadClasses = ClassLoaderUtil.findLoadClasses();
            for (String className : loadClasses) {
                registerBean(registry,classLoader,className);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static void registerBean(BeanDefinitionRegistry registry, ClassLoader classLoader, String className) throws ClassNotFoundException {
        Class<?> clazz;
        clazz = classLoader.loadClass(className);
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        BeanDefinition beanDefinition = builder.getBeanDefinition();
        registry.registerBeanDefinition(clazz.getName(), beanDefinition);
        log.info("register bean [{}],Class [{}] success.", clazz.getName(), clazz);
    }

}
