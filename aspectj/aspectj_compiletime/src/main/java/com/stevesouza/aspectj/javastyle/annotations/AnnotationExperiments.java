/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.aspectj.javastyle.annotations;

/**
 * @author stevesouza
 */
@ExpressionLanguageAnnotation("myclass.annotation")
public class AnnotationExperiments {
    @ExpressionLanguageAnnotation("mymethod.annotation")
    public void myAnnotatedMethod() {

    }

    /* interface MyInterface {
        void myMethod(@ParamName("foo") Object foo, @ParamName("bar") Object bar);
        }
    or compile with  javac  -g argument or I think compiling with ajc doesn't need special treatment
    */
    @ExpressionLanguageAnnotation("mymethod.annotation2")
    public void myAnnotatedMethod2(@ParamName("fname") String fname, String lname) {

    }
}
