package com.stevesouza.spring;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by stevesouza on 5/24/14.
 */
// found automatically by spring.  used instead of putting it in xml.  If "myComponent" is not passed then the name
// would be "myAutowiredClass"
@Component("myComponent")
public class MyAutowiredClass {

    //@Autowired - @Inject is standards based version of autowiring
    @Inject
    private HelloSpringBean helloSpringBean;

    public String getString() {
        return helloSpringBean.helloSpring();
    }

    public String getThrowException() {
        throw new RuntimeException("Exception thrown!!!");
    }

    public void getSlowMethod() throws InterruptedException {
        Thread.sleep(250);
    }

    // monitored with JamonPerformanceMonitorInterceptor in applicationContext.xml
    public void getSlowerMethod() throws InterruptedException {
        Thread.sleep(500);
    }

    public void getPassArgs(String myString, HelloSpringBean helloSpringBean, String notUsed) {
        System.out.println("in getPassArgs: " + "myString=" + myString + ", helloSpringBean=" + helloSpringBean + ", notUsed=" + notUsed);
    }
}
