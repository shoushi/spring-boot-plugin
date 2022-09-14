package com.plugin.controller;

import com.plugin.PluginInterface;
import com.plugin.impl.PluginImpl2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(){
        PluginInterface pluginInterface=new PluginImpl2();
        pluginInterface.sayHello("glq hello");
        return "Hello World";
    }
}
