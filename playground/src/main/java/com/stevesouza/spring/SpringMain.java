package com.stevesouza.spring;

import com.stevesouza.camel.MessagePeeker;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by stevesouza on 5/24/14.
 */
public class SpringMain {
    private static final Logger LOG = Logger.getLogger(SpringMain.class);

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        HelloSpringBean hi = (HelloSpringBean) context.getBean("hiSpringBean");
        LOG.info("SpringBeanSays: "+hi.helloSpring());

        MyAutowiredClass myAutowiredClass = (MyAutowiredClass) context.getBean("myComponent");
        LOG.info("MyAutowiredClass says: "+myAutowiredClass.getString());

    }
}
