package com.stevesouza.jmx;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;

public class MyClass implements MyInterfaceMXBean {

    private String name = "Steve Souza";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        System.out.println("Hello " + name);
    }

    public String getPageStats() {
        return MonitorFactory.getMonitor("pageTime", "ms.").toString();
    }

    public void generateJamonStats() {
        for (int i = 0; i < 100; i++) {
            Monitor m = MonitorFactory.start("pageTime");
            try {
                Thread.sleep(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            m.stop();
        }


    }

}
