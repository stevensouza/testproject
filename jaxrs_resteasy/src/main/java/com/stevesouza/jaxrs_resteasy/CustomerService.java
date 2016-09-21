/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.jaxrs_resteasy;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
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
    private static AtomicInteger id = new AtomicInteger();
    
    public CustomerService() {
        customers.add(new Customer(getNextId(), "steve","souza"));
        customers.add(new Customer(getNextId(), "jeff","beck"));
        customers.add(new Customer(getNextId(), "william","reid"));
        customers.add(new Customer(getNextId(), "keith","richards"));
   }
    
    @GET
    @Produces("application/json")
    public Set getCustomerList() {
        return customers;
    }
    
    private static int getNextId() {
        return id.incrementAndGet();
    }
}
