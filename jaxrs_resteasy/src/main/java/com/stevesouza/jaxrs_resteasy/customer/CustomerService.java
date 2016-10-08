/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.jaxrs_resteasy.customer;

import java.util.Map;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author stevesouza
 * 
 * Note it is more flexible to annotate the interface for jaxrs.  For example then you
 * can use the same interface for the client in resteasy and code will automatically
 * be created.
 */

public interface CustomerService {

    /* sample call (takes customer id)
    http://localhost:8081/jaxrs/services/customers/1
     */
    @GET
    @Path(value = "{id}")
    public Customer getCustomer(@PathParam(value = "id") int id);

    /* sample call:
        http://localhost:8081/jaxrs/services/customers/1
     */
    @GET
    public Map<Integer, Customer> getCustomerList();
    
    @DELETE
    @Path(value = "{id}")
    public Response deleteCustomer(@PathParam(value = "id") int id);
    
    
    @POST
    public Response createCustomer(Customer customer);
 
    /** note i only created a separate method as I couldn't get the form @Consumes annotation
     * to work with the other createCustomer method for some reason. to submit form you can use DHC
     * or the customers.html form.
     * @param customer
     * @return 
     */
    @POST
    @Path("/form")
    @Consumes("application/x-www-form-urlencoded")
    public Response createCustomerWithForm(@BeanParam Customer customer);

    
    @PUT
    @Path(value = "{id}")
    public Customer updateCustomer(@PathParam(value = "id") int id, Customer customer);
    
    /**
     *  Note instead of using String name you could also do the following to get the full Cookie
     * @CookieParam("name") Cookie cookie

     * @param name
     * @return 
     */
    @GET
    @Path("cookie")
    public Response cookie(@CookieParam("name") @DefaultValue("beck") String name);
}
