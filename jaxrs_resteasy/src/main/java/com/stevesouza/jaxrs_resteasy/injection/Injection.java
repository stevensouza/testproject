/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.jaxrs_resteasy.injection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.MultivaluedMap;

/**
 * Note this class is instantiated per request by default.  See CustomerApp comments for 
 * further info.
 * 
 * curl -H 'Accept:application/json' http://localhost:8081/jaxrs/services/customers
 * curl -H 'Accept:text/plain' http://localhost:8081/jaxrs/services/customers
 * 
 * can also do 
 * curl  http://localhost:8081/jaxrs/services/customers.json
 * 
 * Testing of various jax-rs injection annotations. Note for per request resources these 
 * annotations may also be used on fields, setters and constructor arguments
 *  @PathParam
 *  @MatrixParam
 *  @QueryParam
 *  @FormParam
 *  @HeaderParam
 *  @CookieParam
 *  @Context
 *    
 * @author stevesouza
 */

// note I tried to move @Path to the interface and at least for RestEasy this threw an exception
@Path("/inject")
// you can override @Produces per method
// "application/xml", 
@Produces({"application/json", "text/plain", "application/xml"})
@Consumes({"application/json","application/xml"})
public class Injection {
    
 
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
    @Path("/headers")
    public HttpHeaders getHeaders(@Context HttpHeaders headers) {
        return headers;     
    }
    
    /**
     * no difference between the next 2 for the way the method is defined.  you may be able to distinguish by using
     * the api directly in the method though.
     * 
     * curl http://localhost:8081/jaxrs/services/inject/pathparams/steve;age=57/jones
     * curl http://localhost:8081/jaxrs/services/inject/pathparams/steve/jones;age=57
     * 
     * use age default
     * curl http://localhost:8081/jaxrs/services/inject/pathparams/steve/jones
     * curl http://localhost:8081/jaxrs/services/inject/pathparams/steve/jones?search=hi
     * @param fname
     * @param lname
     * @param search
     * @param age
     * @return 
     */
    @GET
    @Path("/pathparams/{fname}/{lname}")
    public Map<String, String> getNames(
            @PathParam("fname") String fname, 
            @PathParam("lname") String lname,
            @QueryParam("search") @DefaultValue("jobHistory") String search,
            @MatrixParam("age") @DefaultValue("99") int age) {
        
      Map<String, String> map = new HashMap<>();  
      map.put("uri", "/pathparams/{fname}/{lname}?search=anything");
      map.put("fname", fname);
      map.put("lname", lname);
      map.put("age", String.valueOf(age));
      map.put("search", search);

      return map;
    }

    /**
     * note you can reuse the same key
     *  curl http://localhost:8081/jaxrs/services/inject/queryparams?hi=steve&hi=souza&bye=joe
     * @param uriInfo
     * @return any query parameters that are submitted
     */
    @GET
    @Path("/queryparams")
    public MultivaluedMap<String, String> getQueryParameters(@Context UriInfo uriInfo)
    {
        return uriInfo.getQueryParameters();
    }



    
}
