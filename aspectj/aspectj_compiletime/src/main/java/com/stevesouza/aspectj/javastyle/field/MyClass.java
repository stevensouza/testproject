package com.stevesouza.aspectj.javastyle.field;

import com.stevesouza.aspectj.MyClassBase;

/**
 * Created by stevesouza on 2/4/15.
 */
public class MyClass extends MyClassBase {

    private int age;
    protected String fname;
    public String lname;
    String department;
    public MyClass() {

    }
    public MyClass(String fname, String lname, int age) {
        this.fname=fname;
        this.lname=lname;
        this.age=age;
        department="software";
    }

//    public String toString() {
//        return fname+" "+lname+" "+department+" "+age;
//    }


    public static void main(String[] args) {
        MyClass m = new MyClass("steve","souza",35);
        System.out.println("ObjectId="+m);
        System.out.println(m.fname);
        System.out.println(m.lname);
        System.out.println(m.age);
        System.out.println(m.department);



    }
}
