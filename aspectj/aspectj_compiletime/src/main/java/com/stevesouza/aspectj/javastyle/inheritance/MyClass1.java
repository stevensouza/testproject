package com.stevesouza.aspectj.javastyle.inheritance;


/**
 * Created by stevesouza on 2/4/15.
 */
public class MyClass1 extends MyClass {

    public void myMethod1() {
    }

    public static void main(String[] args) {
        MyClass1 m = new MyClass1();
        m.myMethod();
        m.myMethod1();
    }
}
