/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.annotations;

/**
 *
 * @author stevesouza
 */
@ExpressionLanguageAnnotation("myclass.method")
public class AnnotationExperiments {
    @ExpressionLanguageAnnotation("mymethod.annotation")
    public void myAnnotatedMethod() {
        
    }
    
    @ExpressionLanguageAnnotation("mymethod.annotation2")
    public void myAnnotatedMethod2(String fname, String lname) {
        
    }
}
