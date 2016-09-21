/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.jaxrs_resteasy;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author stevesouza
 */
@Path("/customers")
public class CustomerService {
    
    private Set<Customer>  customers = new HashSet<>();
    
    public CustomerService() {
        customers.add(new Customer("steve","souza"));
        customers.add(new Customer("jeff","beck"));
    }
    
    @GET
    @Produces("application/json")
    public Set getCustomerList() {
        return customers;
    }
}
