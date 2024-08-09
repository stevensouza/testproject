package com.stevesouza.aspectj.nativestyle.context;

public class MyClass {

    private String name;
    private void method1() {
        name = "steve";
        method2();
    }

    private void method2() {
        System.out.println("name="+name);
    }

    public static void main(String[] args) {
        MyClass myClass = new MyClass();
        System.out.println("myClass: "+myClass);
        myClass.method1();
    }
}
