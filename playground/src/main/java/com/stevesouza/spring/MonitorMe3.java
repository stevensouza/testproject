package com.stevesouza.spring;

import com.stevesouza.spring.aop.MonitorAnnotation;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * Created by stevesouza on 6/2/14.
 */

@Component
public class MonitorMe3 {

    public void myMethod() throws Exception {
        Thread.sleep(250);
    }
    public void myMethod2(String str) throws Exception {
        Thread.sleep(10);
    }

    @MonitorAnnotation
    public void hiMethod3(String str, Date date) throws Exception {
        Thread.sleep(5);
    }

    public void anotherMethod1() throws Exception {
        Thread.sleep(10);
    }

    public void anotherMethod2() throws Exception {
        throw new IOException("file io exception simulation");
    }
}
