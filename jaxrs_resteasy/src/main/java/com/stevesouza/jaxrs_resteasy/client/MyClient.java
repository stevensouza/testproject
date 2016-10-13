/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.jaxrs_resteasy.client;

import com.stevesouza.jaxrs_resteasy.customer.Customer;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 *
 * @author stevesouza
 */
public class MyClient {
    
    public Customer getCustomer(int id) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/jaxrs/services/customers/"+id);
        
        Customer customer = target.request().get(Customer.class);
        client.close();

        return customer;

    }
    
    public void createCustomer() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/jaxrs/services/customers");
        Response response = target.request().post(Entity.json(new Customer("cici","souza")));
        System.out.println("create status="+response.getStatus());

        response.close();
        client.close();
        
    }
    
    public int getCustomerCount() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/jaxrs/services/customers/count");
        
        int customerCount = target.request().get(Integer.class);
        client.close();

        return customerCount;
    }
    
    public static void main(String[] args) {
        // have to start up jaxrs applicaiton first before this code works.
        MyClient client = new MyClient();
        int customerCount = client.getCustomerCount();
        System.out.println("customer count before creation="+customerCount);
        System.out.println();
        System.out.println("list all customers");

        for (int i=1;i<=customerCount;i++) {
            System.out.println(client.getCustomer(i));
        }
        System.out.println();
        
        client.createCustomer();
        customerCount = client.getCustomerCount();
        System.out.println("customer count after creation="+customerCount);
        System.out.println();

        System.out.println("The following customer was created"+client.getCustomer(customerCount));

    }
    
}
