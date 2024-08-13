package com.stevesouza.aspectj.logging.automon.basic;

public class MyDriver {
    public static void main(String[] args) {
        MyLoggerClassBasic.main(args);
        System.out.println("From MyLoggerClassBasic.main(String[])");
        MyClass myClass = new MyClass();
        myClass.otherClassMethod();
    }
}
