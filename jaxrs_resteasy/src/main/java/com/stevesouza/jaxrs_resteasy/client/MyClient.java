/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.jaxrs_resteasy.client;

import com.stevesouza.jaxrs_resteasy.customer.Customer;
import java.util.Map;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *  See ResteasyClientProxy for an even better way to do this...
 * @author stevesouza
 */
public class MyClient {
    
    private  Client client = ClientBuilder.newClient();

    
    public Customer getCustomer(int id) {
        WebTarget target = client.target("http://localhost:8081/jaxrs/services/customers/"+id);
        
        // note you can also be explicit: target.accept("application/json").get(Customer.class);
        Customer customer = target.request().get(Customer.class);

        return customer;

    }
    
    // gives you more control over the response as opposed to just converting to a customer object 
    // like the above does.
     public Response getCustomerResponse(int id) {
        WebTarget target = client.target("http://localhost:8081/jaxrs/services/customers/"+id);       
        return target.request(MediaType.APPLICATION_JSON).get();
    }
    
    public Map<Integer, Customer> getCustomers() {
        WebTarget target = client.target("http://localhost:8081/jaxrs/services/customers");
        return target.request(MediaType.APPLICATION_JSON).get(new GenericType<Map<Integer, Customer>>(){});
    }
    
    public void createCustomer() {
        WebTarget target = client.target("http://localhost:8081/jaxrs/services/customers");
        // note there are other Entity methods like Entity.xml(...) and Entity.form(...)
        Response response = target.request().post(Entity.json(new Customer("cici","souza")));
        System.out.println("create status="+response.getStatus());

        response.close();        
    }
    
    public int getCustomerCount() {
        WebTarget target = client.target("http://localhost:8081/jaxrs/services/customers/count");
        
        int customerCount = target.request().get(Integer.class);

        return customerCount;
    }
    
    public void close() {
        client.close();
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
        System.out.println("customers: "+client.getCustomers());

        
        client.createCustomer();
        customerCount = client.getCustomerCount();
        System.out.println("customer count after creation="+customerCount);
        System.out.println();
        
        // response allows more full info about the request: status, language, headers etc, and you can still convert the body into Customer
        Response response = client.getCustomerResponse(1);
        
        System.out.println("customer response oject: "+response);
        response.close();
        
        System.out.println("The following customer was created"+client.getCustomer(customerCount));
        client.close();

    }
    
}
