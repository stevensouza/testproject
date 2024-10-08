package com.stevesouza.aspectj.logging.automon.all;

public class MyLoggerClassAll {
    private String name;
    private void method1() {
        nameMethod2("joe", "souza", 88);
    }

    private String nameMethod2(String fname, String lname, int age) {
        occupationMethod3("chief");
        name = fname + " " + lname;
        return name;
    }

    private int occupationMethod3(String occupation) {
        System.out.println(occupation);
        return 22;
    }

    private void runtimeException(String message) {
        throw new RuntimeException("runtimeException = "+message);
    }

    public static void main(String[] args) {
        MyLoggerClassAll myLoggerClass = new MyLoggerClassAll();
        System.out.println("** MyLoggerClass.method1");
        myLoggerClass.method1();

        System.out.println("** MyLoggerClass.occupationMethod3");
        myLoggerClass.occupationMethod3("developer");

        System.out.println("** MyLoggerClass.nameMethod2");
        myLoggerClass.nameMethod2("steve", "souza", 62);

        System.out.println("** MyLoggerClass.runtimeException");
        try {
            myLoggerClass.runtimeException("steve");
        } catch (RuntimeException e) {
            // gobble
        }
    }
}
