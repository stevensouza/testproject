package com.stevesouza.misc;

/**
 * Created by stevesouza on 9/13/15.
 */
public class HelloWorldAspectJImpl implements IHelloWorld {

    @Override
    public String getResponse(String hello) {
        // return "say hello:" + hello + ",at:" + new Date();
        return "say hello:";

    }


}
