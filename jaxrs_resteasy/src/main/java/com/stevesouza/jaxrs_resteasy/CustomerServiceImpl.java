/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.jaxrs_resteasy;

import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

/**
 * Note this class is instantiated per request by default.  See CustomerApp comments for 
 * further info.
 * 
 * curl -H 'Accept:application/json' http://localhost:8081/jaxrs_resteasy-1.0.1-SNAPSHOT/services/customers
 * curl -H 'Accept:text/plain' http://localhost:8081/jaxrs_resteasy-1.0.1-SNAPSHOT/services/customers
 * @author stevesouza
 */

// note I tried to move @Path to the interface and at least for RestEasy this threw an exception
@Path("/customers")
// you can override @Produces per method
// "application/xml", 
@Produces({"application/json", "text/plain", "application/xml"})
public class CustomerServiceImpl implements CustomerService {
    
    private static CustomerDb  customers = new CustomerDb();
    
    /* sample call:
    http://localhost:8081/jaxrs_resteasy-1.0.1-SNAPSHOT/services/customers/1
    */
    @Override
    public Map<Integer, Customer> getCustomerList() {
        return customers.getCustomerList();
    }
    
    /* sample call (takes customer id)
    http://localhost:8081/jaxrs_resteasy-1.0.1-SNAPSHOT/services/customers/1
    */
    @Override
    public Customer getCustomer(int id) {
        return customers.getCustomer(id);
    }
    
    /** 
     * The @Context annotation allows you to inject instances of 
     * javax.ws.rs.core.HttpHeaders, javax.ws.rs.core.UriInfo, javax.ws.rs.core.Request, 
     * javax.servlet.HttpServletRequest, javax.servlet.HttpServletResponse, 
     * javax.servlet.ServletConfig, javax.servlet.ServletContext, and 
     * javax.ws.rs.core.SecurityContext objects.
     * ,
     * @param context
     * @return 
     */
    @GET
    @Path("headers")
    public HttpHeaders getHeaders(@Context HttpHeaders headers) {
        return headers;     
    }
    
}
