package com.stevesouza.aspectj.javastyle.method;

import com.stevesouza.aspectj.MyClassBase;

/**
 * Created by stevesouza on 2/4/15.
 */
public class MyClass extends MyClassBase {

    public void myPublicFunction() {

    }

    protected void myProtectedFunction() {

    }

    private void myPrivateFunction() {

    }

    public static void main(String[] args) {
        MyClass m = new MyClass();
        m.deliver("hi ");
        m.deliver("mom", "hi");
        m.myPublicFunction();
        m.myProtectedFunction();
        m.myPrivateFunction();
    }
}
