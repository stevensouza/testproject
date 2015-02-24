package com.stevesouza.aspectj.javastyle.afterthrowing;


/**
 * Created by stevesouza on 2/4/15.
 */
public class MyClass  {

    private String fname;
    private int counter=1;

    public MyClass(String fname) {
        this.fname=fname;
    }

    public MyClass() {
        throw new RuntimeException("Exception thrown from no arg constructor");
    }

    public void myMethod() {
        try {
            myMethod1();
        } catch(Throwable e) {

        } finally {
            System.out.println("  in finally block");
        }
    }

    private void myMethod1() {
        myMethod2();
    }

    private void myMethod2() {
        myException("steve");
    }

    private void myException(String name) {
        counter++;
        if (counter%2==0) {
            String myNpe = null;
            myNpe.equals("hi");
        } else {
            throw new RuntimeException("This is my runtime exception: "+name);
        }
    }

    public static void main(String[] args) {
        MyClass m = new MyClass("steve");
        m.myMethod();
        m.myMethod();
        try {
            new MyClass();
        } catch(Throwable e) {
        }
    }
}
