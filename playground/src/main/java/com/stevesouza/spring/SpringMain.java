package com.stevesouza.spring;

import com.stevesouza.Utils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by stevesouza on 5/24/14.
 */
public class SpringMain {
    private static final Logger LOG = Logger.getLogger(SpringMain.class);

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        HelloSpringBean hi = (HelloSpringBean) context.getBean("hiSpringBean");
        LOG.info("SpringBeanSays: "+hi.helloSpring());

        MyAutowiredClass myAutowiredClass = (MyAutowiredClass) context.getBean("myComponent");
        LOG.info("MyAutowiredClass says: "+myAutowiredClass.getString());
        try {
            LOG.info("MyAutowiredClass says: " + myAutowiredClass.getThrowException());
        } catch (Exception e) {
        }

        // monitored with jamon
        myAutowiredClass.getSlowMethod();
        myAutowiredClass.getSlowerMethod();
        // marked with @MonitoredAnnotation
        MonitorMe1 monitorMe1 = context.getBean("monitorMe1", MonitorMe1.class);
        MonitorMe2 monitorMe2 = context.getBean("monitorMe2", MonitorMe2.class);

        // monitored not with annotation, but in applicationContext.xml
        MonitorMe3 monitorMe3 = context.getBean("monitorMe3", MonitorMe3.class);
        MonitorMe4 monitorMe4 = context.getBean("monitorMe4", MonitorMe4.class);


        for (int i=0;i<10;i++) {
            monitorMe1.myMethod();
            monitorMe2.myMethod();
            monitorMe3.myMethod();
            monitorMe3.myMethod2("hello world");
            monitorMe3.hiMethod3("hello world", null);
            monitorMe3.anotherMethod1();
            monitorMe4.myMethod();
            monitorMe4.myName("steve", "souza");
            try {
                monitorMe3.anotherMethod2(); // throws exception
            } catch (Exception e) {

            }
        }

        myAutowiredClass.getPassArgs("myStringValue", new HelloSpringBean(), "notUsedValue");
        Utils.displayJamon();

    }
}
