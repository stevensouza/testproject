/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.jaxrs_resteasy;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

/**
 *
 * @author stevesouza
 */
public class CustomerDb {
    private Map<Integer, Customer>  customers = new HashMap<>();
    private AtomicInteger id = new AtomicInteger();
    
    public CustomerDb() {
        addCustomer(new Customer("steve","souza"));
        addCustomer(new Customer("jeff","beck"));
        addCustomer(new Customer("william","reid"));
        addCustomer(new Customer("keith","richards"));
   }
    
    public void addCustomer(Customer customer) {
        customers.put(getNextId(), customer);
    }
    
    public Customer getCustomer(int id) {
        return customers.get(id);
    }
    
    @GET
    @Produces("application/json")
    public Map<Integer, Customer>  getCustomerList() {
        return customers;
    }
    
    private int getNextId() {
        return id.incrementAndGet();
    }
    
}
