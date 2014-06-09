package com.stevesouza.spring;

import com.stevesouza.spring.aop.MonitorAnnotation;
import org.springframework.stereotype.Component;

/**
 * Created by stevesouza on 6/2/14.
 */
@MonitorAnnotation
@Component
public class MonitorMe4 {

    public void myMethod() throws Exception {
        Thread.sleep(150);
    }


    public void myName(String fname, String lname) {

    }
}
