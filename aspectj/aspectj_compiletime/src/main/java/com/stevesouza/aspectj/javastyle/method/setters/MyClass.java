package com.stevesouza.aspectj.javastyle.method.setters;

import com.stevesouza.aspectj.MyClassBase;

/**
 * Created by stevesouza on 2/4/15.
 */
public class MyClass extends MyClassBase {

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    private String fname;
    private String lname;



    public static void main(String[] args) {
        MyClass m = new MyClass();
        m.deliver("hi ");
        m.deliver("mom", "hi");
        m.setFname("steve");
        m.getFname();
        m.setLname("souza");
        m.getLname();

    }
}
