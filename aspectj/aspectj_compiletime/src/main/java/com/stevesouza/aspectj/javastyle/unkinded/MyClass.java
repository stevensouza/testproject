package com.stevesouza.aspectj.javastyle.unkinded;


/**
 * Created by stevesouza on 2/4/15.
 */
public class MyClass  {

    private String fname;

    public MyClass(String fname) {
        this.fname=fname;
    }

    public void myMethod() {
        myMethod1();
        myMethod3();
        "steve".equals("steve");
    }

    private void myMethod1() {
        myMethod2();
    }

    private void myMethod2() {
        System.out.println(Math.abs(-100));
    }

    private void myMethod3() {
    }


    public static void main(String[] args) {
        MyClass m = new MyClass("steve");
        System.out.println("ObjectId="+m);
        System.out.println(m.fname);
        m.myMethod();
    }
}
