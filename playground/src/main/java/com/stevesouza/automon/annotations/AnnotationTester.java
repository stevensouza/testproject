package com.stevesouza.automon.annotations;

/**
 * Used to test annotations pointcut in automon.
 */
public class AnnotationTester {
    public static void main(String[] args) {
        MethodAnnotationTester mt = new MethodAnnotationTester();
        ClassAnnotationTester ct = new ClassAnnotationTester();
        mt.annotated();
        mt.notAnnotated();
        ct.classAnnotated1();
        ct.classAnnotated2();
    }
}
