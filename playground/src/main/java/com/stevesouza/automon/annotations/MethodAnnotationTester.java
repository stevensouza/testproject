package com.stevesouza.automon.annotations;

import com.jamonapi.aop.spring.MonitorAnnotation;

/**
 * Created by stevesouza on 3/12/15.
 */
public class MethodAnnotationTester {

    @MonitorAnnotation
    public void annotated() {
        System.out.println("  jamon annotated()");
    }

    public void notAnnotated() {
        System.out.println("  jamon notAnnotated()");
    }
}
