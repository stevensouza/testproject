package com.stevesouza.camel;

/**
 * Created by stevesouza on 5/19/14.
 */
public class PersonsName {


    // seems to be needed for unmarshalling
    public PersonsName() {

    }

    public PersonsName(String fname, String lname) {
        this.fname = fname;
        this.lname = lname;
    }

    private String fname;
    private String lname;

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

    public String toString() {
        return "PersonsName (fname=" + fname + ", lname=" + lname + ")";
    }

}
