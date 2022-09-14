package com.plugin.impl;

import com.plugin.PluginInterface;
import com.plugin.entity.Persion;

public class PluginImpl2 implements PluginInterface {
    private Persion persion;

    public Persion getPersion() {
        return persion;
    }

    public void setPersion(Persion persion) {
        this.persion = persion;
    }

    @Override
    public String sayHello(String name) {
        Persion persion1 = new Persion();
        persion1.setName(name);
        persion1.setAge(123);
        System.out.println(persion1.getName()+persion1.getAge());
        return persion1.toString();
    }
}
