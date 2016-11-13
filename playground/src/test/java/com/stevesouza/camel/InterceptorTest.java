/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.camel;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

/**
 *
 * @author stevesouza
 */
public class InterceptorTest extends CamelTestSupport {
 
    @Produce(uri = "direct:start")
    protected ProducerTemplate template;


    @Test
    public void testSysOut()  {
        template.sendBody("* my message body should be sent to sys out!");
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new BaseRouteBuilder() {
            public void configure() {
                
                interceptSendToEndpoint("stream:out").to("log:interceptSendToEndpoint");
                from("direct:start").routeId("StevesRouteID").log("hi").log("world").to("stream:out");
            }
        };
    }   
}
