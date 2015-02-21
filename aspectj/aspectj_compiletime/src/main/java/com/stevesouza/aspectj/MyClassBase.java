package com.stevesouza.aspectj;

/**
 * Created by stevesouza on 2/4/15.
 */
public class MyClassBase {

    public MyClassBase() {
        System.out.println(getClass());
    }
    public void deliver(String message) {
        System.out.println(message);
    }

    public void deliver(String person, String message) {
        System.out.println(person+", "+message);
    }

    public static void main(String[] args) {
        MyClassBase m = new MyClassBase();
        m.deliver("hi ");
        m.deliver("mom", "hi");
    }
}
