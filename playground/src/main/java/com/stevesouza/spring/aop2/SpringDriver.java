package com.stevesouza.spring.aop2;

import com.stevesouza.camel.*;
import com.stevesouza.Utils;
import com.stevesouza.spring.MyAutowiredClass;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.StartupListener;
import org.apache.camel.impl.DefaultCamelContextNameStrategy;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Program that launches various camel tests.
 */
public class SpringDriver {
    private static final Logger LOG = Logger.getLogger(SpringDriver.class);


    public static void main(String[] ags) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("aop2TestApplicationContext.xml");
        MySpringBean mySpringBean = context.getBean("mySpringBean", MySpringBean.class);
        // aop is defined on this method in MySpringAspect
        mySpringBean.myMethod();
        mySpringBean.anotherMethod1();
        try {
          mySpringBean.methodThrowingException(); 
        } catch (Exception e) {
            LOG.info(e);
        }
    }



}
