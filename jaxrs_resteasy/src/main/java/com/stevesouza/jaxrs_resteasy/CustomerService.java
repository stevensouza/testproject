/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.jaxrs_resteasy;

import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * Note this class is instantiated per request by default.  See CustomerApp comments for 
 * further info.
 * 
 * curl -H 'Accept:application/json' http://localhost:8081/jaxrs_resteasy-1.0.1-SNAPSHOT/services/customers
 * curl -H 'Accept:text/plain' http://localhost:8081/jaxrs_resteasy-1.0.1-SNAPSHOT/services/customers
 * @author stevesouza
 */
@Path("/customers")
// you can override this per method
// "application/xml", 
@Produces({"application/json", "text/plain"})
public class CustomerService {
    
    private static CustomerDb  customers = new CustomerDb();
    
    /* sample call:
    http://localhost:8081/jaxrs_resteasy-1.0.1-SNAPSHOT/services/customers/1
    */
    @GET
    public Map<Integer, Customer> getCustomerList() {
        return customers.getCustomerList();
    }
    
    /* sample call (takes customer id)
    http://localhost:8081/jaxrs_resteasy-1.0.1-SNAPSHOT/services/customers/1
    */
    @GET
    @Path("{id}")
    public Customer getCustomer(@PathParam("id") int id) {
        return customers.getCustomer(id);
    }
    
}
