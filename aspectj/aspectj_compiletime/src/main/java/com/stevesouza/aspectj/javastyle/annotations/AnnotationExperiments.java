/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.aspectj.javastyle.annotations;

/**
 *
 * @author stevesouza
 */
@ExpressionLanguageAnnotation("myclass.method")
public class AnnotationExperiments {
    @ExpressionLanguageAnnotation("mymethod.annotation")
    public void myAnnotatedMethod() {
        
    }
    
    /* interface MyInterface {
  void myMetod(@ParamName("foo") Object foo, @ParamName("bar") Object bar);
}
    or compile with  javac  -g argument. 
    */
    @ExpressionLanguageAnnotation("mymethod.annotation2")
    public void myAnnotatedMethod2(@ParamName("fname") String fname, String lname) {
        
    }
}
