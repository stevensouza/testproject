package com.stevesouza.automon.annotations;

import com.jamonapi.aop.spring.MonitorAnnotation;
import org.automon.annotations.Monitor;

/**
 * Created by stevesouza on 3/12/15.
 */
@MonitorAnnotation
@Monitor
public class ClassAnnotationTester {

    public void classAnnotated1() {
        System.out.println("  jamon classAnnotated1()");
    }

    public void classAnnotated2() {
        System.out.println("  jamon classAnnotated2()");
    }
}
