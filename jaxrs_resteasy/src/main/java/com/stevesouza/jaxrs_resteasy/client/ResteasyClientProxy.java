/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.jaxrs_resteasy.client;

import com.stevesouza.jaxrs_resteasy.customer.Customer;
import com.stevesouza.jaxrs_resteasy.customer.CustomerService;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

/**
 *
 *  Super cool.  Simply proxy jaxrs service and the proxy will call the services in java pojo native code!
 * 
 * Compare this to the complexity of MyClient (and even MyClient is better than calling raw http)
 * @author stevesouza
 */
public class ResteasyClientProxy {
    private String serviceBaseUrl;
    
    public ResteasyClientProxy(String serviceBaseUrl) {
        this.serviceBaseUrl = serviceBaseUrl;      
    }
       

      public  CustomerService getCustomerService() {
        
        ResteasyClient client = new ResteasyClientBuilder().build();  
        // note the above only has a pool of 1 connection and so if you don't close any Response's returned 
        // from methods you will lose that connection.  The following would allow you to not close everyone though
        // it is a resource leak. Still good to know these methods.
        //    ResteasyClient client = new ResteasyClientBuilder().connectionPoolSize(10).connectionCheckoutTimeout(10, TimeUnit.SECONDS).build();  
  
        ResteasyWebTarget target = client.target(serviceBaseUrl);
        // alternatively could do: return target.proxy(CustomerService.class);
        return target.proxyBuilder(CustomerService.class).build();

      }

    
    public static void main(String[] args) {
        // have to start up jaxrs applicaiton first before this code works.
        //  i.e. war in tomcat
 
        ResteasyClientProxy client = new ResteasyClientProxy("http://localhost:8081/jaxrs/services/customers");
        CustomerService customer = client.getCustomerService();
        System.out.println("customer count before creation="+customer.getCustomerCount());
        
        System.out.println();
        System.out.println("list all customers: "+customer.getCustomerList());
        // NOTE: You must close any response object that is returned or else you loose a connection.
        //  By default you can only have one connection, so it makes the line after createCustomer fail.
        //  as a connection isn't available.  This isn't ideal as you should be able to call createCustomer
        //  without it losing the connection.
        Response response = customer.createCustomer(new Customer("albert", "einstein"));
        response.close();
        System.out.println("customer count after creation="+customer.getCustomerCount());

        System.out.println();
        System.out.println("list all customers: "+customer.getCustomerList()); 

    }  
}
