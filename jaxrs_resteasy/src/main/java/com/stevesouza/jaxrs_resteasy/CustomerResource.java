/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.jaxrs_resteasy;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author stevesouza
 */
@Path("/customers")
public class CustomerResource {
    @GET
    @Produces("text/plain")
    public String getCustomerList() {
        return "my customer is Steve Souza";
    }
}
