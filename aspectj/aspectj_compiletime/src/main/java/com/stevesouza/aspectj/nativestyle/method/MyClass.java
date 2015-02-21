package com.stevesouza.aspectj.nativestyle.method;

import com.stevesouza.aspectj.MyClassBase;

/**
 * Created by stevesouza on 2/4/15.
 */
public class MyClass extends MyClassBase {

    public void myPublicMethod() {

    }

    public static void main(String[] args) {
        MyClass m = new MyClass();
        m.deliver("hi ");
        m.deliver("mom", "hi");
        m.myPublicMethod();
    }
}
