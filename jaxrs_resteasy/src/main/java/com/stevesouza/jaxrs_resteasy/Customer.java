/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.jaxrs_resteasy;

/**
 *
 * @author stevesouza
 */
public class Customer {

    private String fName;
    private String lName;
    
    public Customer(String fName, String lName) {
        this.fName = fName;
        this.lName = lName;
    }
    
    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }
    
}
