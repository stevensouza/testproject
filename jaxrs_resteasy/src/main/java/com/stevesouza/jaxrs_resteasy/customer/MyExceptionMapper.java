/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.jaxrs_resteasy.customer;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * You can map custom exception to be handled by jaxrs.  Note many exceptions are 
 * already handled by jaxrs such as NotFoundException or BadRequestException and
 * so no mapping is required.  You can also wrap your custom exceptions in 
 * WebApplicationException and jaxrs automatically handles.  The following approach
 * is more clean however and you can simply throw the app exception directly.
 * @author stevesouza
 */
public class MyExceptionMapper implements ExceptionMapper<MyNotFoundException> {

    @Override
    public Response toResponse(MyNotFoundException e) {
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    
}
