package com.stevesouza.spring;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;
import com.stevesouza.camel.MessagePeeker;
import org.apache.camel.Exchange;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by stevesouza on 5/24/14.
 */
public class SpringMain {
    private static final Logger LOG = Logger.getLogger(SpringMain.class);

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        HelloSpringBean hi = (HelloSpringBean) context.getBean("hiSpringBean");
        LOG.info("SpringBeanSays: "+hi.helloSpring());

        MyAutowiredClass myAutowiredClass = (MyAutowiredClass) context.getBean("myComponent");
        LOG.info("MyAutowiredClass says: "+myAutowiredClass.getString());
        try {
            LOG.info("MyAutowiredClass says: " + myAutowiredClass.getThrowException());
        } catch (Exception e) {

        // monitored with jamon
        myAutowiredClass.getSlowMethod();
        myAutowiredClass.getSlowerMethod();
        for (Monitor mon : MonitorFactory.getRootMonitor().getMonitors()) {
            LOG.info(mon);
        }
    }


    }
}
