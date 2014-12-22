package com.stevesouza.jmx;

/**
 * Created by stevesouza on 11/19/14.
 */
public class ExceptionImp implements ExceptionInterface {
    @Override
    public void throwException() {
        throw new RuntimeException("This is my exception!!");
    }
}
