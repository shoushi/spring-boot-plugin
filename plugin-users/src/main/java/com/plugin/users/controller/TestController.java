package com.plugin.users.controller;

import com.plugin.users.utils.ClassLoaderUtil;
import com.plugin.users.utils.SpringUtil;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author glq
 * @version 1.0
 * @date 2021/10/5
 */
@RestController
public class TestController {
    @Resource
    private SpringUtil springUtil;


    /**
     * 运行时注册bean
     */
    @GetMapping("/reload")
    public Object reload(){
        try {
            ClassLoader classLoader = ClassLoaderUtil.getClassLoader();
            Assert.notNull(classLoader, "ClassLoader must not be null");
            final List<String> loadClasses = ClassLoaderUtil.findLoadClasses();
            for (String className : loadClasses) {
                springUtil.registerBean(className, classLoader.loadClass(className));
            }
            return "reload success";
        } catch (ClassNotFoundException e) {
            return "reload failed";
        }
    }
}
