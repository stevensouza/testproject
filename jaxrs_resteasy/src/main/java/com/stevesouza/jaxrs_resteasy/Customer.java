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
    
    
    private int id;
    private String fName;
    private String lName;
    
    
    public Customer(int id, String fName, String lName) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
    }
    
    
    public int getId() {
        return id;
    }
    
    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }
    
}
