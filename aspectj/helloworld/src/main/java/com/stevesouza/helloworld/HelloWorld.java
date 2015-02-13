package com.stevesouza.helloworld;

/**
 * Created by stevesouza on 2/13/15.
 */
public class HelloWorld {

    public String getFirstName() {
        return "Steve";
    }

    public String getLastName() {
        return "Souza";
    }

    public static void main(String[] args) {
        HelloWorld hw = new HelloWorld();
        System.out.println(hw.getFirstName());
        System.out.println(hw.getLastName());
    }
}
