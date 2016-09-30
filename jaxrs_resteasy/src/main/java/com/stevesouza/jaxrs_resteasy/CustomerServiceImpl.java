/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.jaxrs_resteasy;

import java.text.MessageFormat;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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
@Consumes({"application/json","application/xml"})
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
    
    @Override
    public Response deleteCustomer(int id) {
      if (customers.contains(id)) {
         customers.deleteCustomer(id);   
         return Response.status(Status.NO_CONTENT).build();
      } else {
          return Response.noContent().build();
      }
    }
    
    
    /** because this method consumes xml and json it can automatically convert them
     *   to Customer (for xml jaxb tags are required in Customer class).  xml support is part of 
     * jaxrs whereas you must include jackson for json to work.
     */
    @Override
    public Response createCustomer(Customer customer) {
        customers.addCustomer(customer);
        // need to fix this to proper response.
        // note customer is automatically converted to xml or json based on the http header 'Accept:'
        // proper response is 200 when content and 204 when not.  
        return Response.ok().entity(customer).build();
    }
    
    /** update the customer with the given id with any non-empty information from within the passed in
     * Customer.
     * 
     * @param id customer to update
     * @param customer update with this customer info
     * @return 
     */
    @Override
    public Customer updateCustomer(int id, Customer customer) {
        if (customers.updateCustomer(id, customer)) {
          Customer updatedCustomer = customers.getCustomer(id);
          return updatedCustomer;
        }
        throw new NotFoundException(MessageFormat.format("The customer with id={0} does not exist.", id)); 
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
