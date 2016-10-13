/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.jaxrs_resteasy.customer;

/**
 *
 * @author stevesouza
 */
public class MyNotFoundException extends RuntimeException {

    /**
     * Creates a new instance of <code>MyNotFoundException</code> without detail
     * message.
     */
    public MyNotFoundException() {
    }

    /**
     * Constructs an instance of <code>MyNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public MyNotFoundException(String msg) {
        super(msg);
    }
}
