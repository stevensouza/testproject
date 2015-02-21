package com.stevesouza.aspectj.javastyle.constructor;

import com.stevesouza.aspectj.MyClassBase;

/**
 * Created by stevesouza on 2/4/15.
 */
public class MyClass extends MyClassBase {

    public MyClass() {
    }

    public MyClass(String fname, String lname) {
    }

    protected MyClass(String fname) {

    }

    public static void main(String[] args) {
        MyClass m = new MyClass();
        m.deliver("hi ");
        m.deliver("mom", "hi");

        m = new MyClass("steve", "souza");
        m.deliver("hi");

        m = new MyClass("souza");
        m.deliver("hi");
    }
}
