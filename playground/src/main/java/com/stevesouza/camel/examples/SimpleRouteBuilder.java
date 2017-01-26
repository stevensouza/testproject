/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.camel.examples;

import com.stevesouza.camel.BaseRouteBuilder;

/**
 *
 * @author stevesouza
 */
public class SimpleRouteBuilder extends BaseRouteBuilder {
    
    @Override
    public void configure() throws Exception {
         from("direct:simpleroute").
         routeId("myrouteid:"+getClass().getSimpleName()). // note this gives the route a name in the jmx console.
         to("log:com.stevesouza.camel.SimpleRouteBuilder");
    }
    
}
