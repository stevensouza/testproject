package com.stevesouza.aspectj.javastyle.context;

import com.stevesouza.aspectj.MyClassBase;

/**
 * Created by stevesouza on 2/4/15.
 */
public class MyClass extends MyClassBase {


    public void myMethod(int i, String myString) {
       System.out.println("\n'myMethod' info");
    }

    public static void main(String[] args) {
        MyClass m = new MyClass();
        m.myMethod(100, "steve");
        System.out.println("\n'main' method info");

    }
}
