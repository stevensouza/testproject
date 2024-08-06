package com.stevesouza.aspectj.logging.automon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyLoggerClass {
    private void method1() {
        nameMethod2("joe", "souza", 88);
    }

    private void nameMethod2(String fname, String lname, int age) {
        occupationMethod3("chief");
    }

    private void occupationMethod3(String occupation) {
    }

    public static void main(String[] args) {
        MyLoggerClass myLoggerClass = new MyLoggerClass();
        System.out.println("** MyLoggerClass.method1");
        myLoggerClass.method1();

        System.out.println("** MyLoggerClass.occupationMethod3");
        myLoggerClass.occupationMethod3("developer");

        System.out.println("** MyLoggerClass.nameMethod2");
        myLoggerClass.nameMethod2("steve", "souza", 62);
    }
}
