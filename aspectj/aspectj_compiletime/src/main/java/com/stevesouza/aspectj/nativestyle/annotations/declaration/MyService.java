package com.stevesouza.aspectj.nativestyle.annotations.declaration;

/**
 * This class is used to show how declaration @ annotations works but some of the assigned annofations
 * will be redundant or won't make sense. Annotations will be assigned during compilation by
 * {@link DeclareAnnotationsAspect}
 */

public class MyService {

    public void processRequest() {
        System.out.println("processRequest()");
    }

    public void processPurchase() {
    }

    public void timedSetMethod() {
    }

    public void inheritsClassAnnotations() {
    }

    public void aMethod() {
    }

    public void bMethod() {
    }

}