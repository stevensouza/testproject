/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 *
 * @author stevesouza
 */
public class CamelDriver {
    
    private final CamelContext camelContext;
    private final ProducerTemplate template;
    

    
    public CamelDriver() throws Exception {
        camelContext = new DefaultCamelContext();
        template = camelContext.createProducerTemplate();
    }
    
    public CamelDriver(RouteBuilder rb) throws Exception {
        this();
        camelContext.addRoutes(rb);
    }
    
    
    public CamelContext getCamelContext() {
        return camelContext;
    }

    public ProducerTemplate getTemplate() {
        return template;
    }
    
    public void start() throws Exception {
      camelContext.start();
    }
    
    public void stop() throws Exception {
        camelContext.stop();
    }
    
    

    
    public static void main(String[] args) throws Exception {
        CamelDriver camelDriver = new CamelDriver(new SimpleRouteBuilder());
        camelDriver.start();
        camelDriver.getTemplate().sendBody("direct:simpleroute", "hello world message!");
        camelDriver.stop();
    }
    
}
