package com.stevesouza.aspectj.javastyle.field;


/**
 * Created by stevesouza on 2/4/15.
 */
public class MyClass {

    public String lname;
    protected String fname;
    String department;
    private final int age;

    public MyClass(String fname, String lname, int age) {
        this.fname = fname;
        this.lname = lname;
        this.age = age;
        department = "software";
    }

    public static void main(String[] args) {
        MyClass m = new MyClass("steve", "souza", 35);
        System.out.println();
        System.out.println("ObjectId=" + m);
        System.out.println("   get value="+m.fname);
        System.out.println("   get value="+m.lname);
        System.out.println("   get value="+m.age);
        System.out.println("   get value="+m.department);


    }
}
