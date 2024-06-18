package com.stevesouza.aspectj.nativestyle.structure;

/**
 *  The aspect in this package introduces a static crosscutting implementation of an interface to
 *  CustomerClass. Note the code runs in intellij although it shows a compiler error
 *  in the IDE.
 */
public class CustomerClass {

    public String getName() {
        return "Steve Souza";
    }

    public static void main(String[] args) {
        CustomerClass cc = new CustomerClass();
        cc.getName();
        // the following code runs despite showing an error in intellij
        cc.setExtraInfo("Hi Steve");
        System.out.println("Calling method introduced by aspectj: "+cc.getExtraInfo());
    }
}
