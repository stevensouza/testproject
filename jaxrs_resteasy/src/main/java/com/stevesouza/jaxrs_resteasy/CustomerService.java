/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.jaxrs_resteasy;

import java.util.Map;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author stevesouza
 */
public interface CustomerService {

    /* sample call (takes customer id)
    http://localhost:8081/jaxrs_resteasy-1.0.1-SNAPSHOT/services/customers/1
     */
    @GET
    @Path(value = "{id}")
    public Customer getCustomer(@PathParam(value = "id") int id);

    /* sample call:
    http://localhost:8081/jaxrs_resteasy-1.0.1-SNAPSHOT/services/customers/1
     */
    @GET
    public Map<Integer, Customer> getCustomerList();
    
    @DELETE
    @Path(value = "{id}")
    public Response deleteCustomer(@PathParam(value = "id") int id);
    
    
    @POST
    public Response createCustomer(Customer customer);

    
}
