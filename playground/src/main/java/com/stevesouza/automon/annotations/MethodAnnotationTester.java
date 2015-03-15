package com.stevesouza.automon.annotations;

import com.jamonapi.aop.spring.MonitorAnnotation;
import org.automon.annotations.Monitor;

/**
 * Created by stevesouza on 3/12/15.
 */
public class MethodAnnotationTester {

    @MonitorAnnotation
    @Monitor
    public void annotated() {
        System.out.println("  jamon annotated()");
    }

    public void notAnnotated() {
        System.out.println("  jamon notAnnotated()");
    }
}
