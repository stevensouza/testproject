package com.stevesouza.automon.annotations;

import com.jamonapi.aop.spring.MonitorAnnotation;

/**
 * Created by stevesouza on 3/12/15.
 */
@MonitorAnnotation
public class ClassAnnotationTester {

    public void classAnnotated1() {
        System.out.println("  jamon classAnnotated1()");
    }

    public void classAnnotated2() {
        System.out.println("  jamon classAnnotated2()");
    }
}
