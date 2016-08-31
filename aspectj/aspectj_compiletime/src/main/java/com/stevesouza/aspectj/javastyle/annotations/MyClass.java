/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.aspectj.javastyle.annotations;

import com.stevesouza.aspectj.MyClassBase;

/**
 *
 * @author stevesouza
 */
public class MyClass {
    
    public MyClass() {
        System.out.println(getClass());
    }
    
    @ExpressionLanguageAnnotation("deliver1.annotation")
    public void deliver1(String message) {
        System.out.println(message);
    }

    @ExpressionLanguageAnnotation(value="deliver2.annotation", label="[person={0}, message={1}]")
    public void deliver2(@ParamName("person") String person, String message) {
        System.out.println(person+", "+message);
    }

    
    public static void main(String[] args) {
        MyClass m = new MyClass();
        m.deliver1("hi");
        m.deliver2("mom", "hi");
    }
    
}
