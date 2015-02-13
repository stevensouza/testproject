package com.stevesouza.aspectj;

/**
 * Created by stevesouza on 2/4/15.
 */
public class MessageCommunicator {

    public void deliver(String message) {
        System.out.println(message);
    }

    public void deliver(String person, String message) {
        System.out.println(person+", "+message);
    }

    public static void main(String[] args) {
        MessageCommunicator m = new MessageCommunicator();
        m.deliver("hi ");
        m.deliver("mom", "hi");
    }
}
