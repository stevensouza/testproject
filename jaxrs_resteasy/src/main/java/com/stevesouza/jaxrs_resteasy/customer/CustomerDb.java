/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.jaxrs_resteasy.customer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


/**
 *
 * @author stevesouza
 */
public class CustomerDb {
    private Map<Integer, Customer>  customers = new HashMap<>();
    private AtomicInteger id = new AtomicInteger();
    
    public CustomerDb() {       
        // load with initial data
        addCustomer(new Customer("steve","souza"));
        addCustomer(new Customer("jeff","beck"));
        addCustomer(new Customer("william","reid"));
        addCustomer(new Customer("keith","richards"));
   }
    
    public void addCustomer(Customer customer) {
        customers.put(getNextId(), customer);
    }
    
    public void deleteCustomer(int id) {
        customers.remove(id);
    }
    
    public boolean updateCustomer(int id, Customer updateFrom) {
        if (contains(id)) {
            Customer customer = getCustomer(id);
            customer.update(updateFrom);
            return true;
        }

        return false;
    }
    
    
    public Customer getCustomer(int id) {
        return customers.get(id);
    }
    
    public Map<Integer, Customer>  getCustomerList() {
        return customers;
    }
    
    public int getCustomerCount() {
        return customers.size();
    }
    
    public boolean contains(int id) {
        return customers.containsKey(id);
    }
    
    private int getNextId() {
        return id.incrementAndGet();
    }
    
}
