package com.stevesouza.aspectj.nativestyle.within_cflow;

import com.stevesouza.aspectj.MyClassBase;

import java.util.ArrayList;
import java.util.List;

public class MyClass extends MyClassBase {

    public void myMethod1() {
        System.out.println("     myMethod1");
        myMethod2();
        setDescription(getDescription()+"hi");
    }

    private void myMethod2() {
        System.out.println("     myMethod2");
        myMethod3();
    }

    private void myMethod3() {
        System.out.println("     myMethod3");
    }

    private void myMethod4() {
        myMethod1();
        System.out.println("     myMethod4");
    }

    private void myMethod5() {
        System.out.println("     myMethod5");
    }

    public static void main(String[] args) {
        MyClass my = new MyClass();
        my.myMethod1();
        my.myMethod3();
        my.myMethod4();
        my.myMethod5();
    }
}
