package com.stevesouza.aspectj.javastyle.field;


/**
 * Created by stevesouza on 2/4/15.
 */
public class MyClass  {

    private int age;
    protected String fname;
    public String lname;
    String department;

    public MyClass(String fname, String lname, int age) {
        this.fname=fname;
        this.lname=lname;
        this.age=age;
        department="software";
    }

    public static void main(String[] args) {
        MyClass m = new MyClass("steve","souza",35);
        System.out.println("ObjectId="+m);
        System.out.println(m.fname);
        System.out.println(m.lname);
        System.out.println(m.age);
        System.out.println(m.department);



    }
}
