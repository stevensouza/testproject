/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.camel;

import com.stevesouza.camel.lifecycle.MyInterceptProcessor;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.SpringRouteBuilder;
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
        return new SpringRouteBuilder() {
            public void configure() {
                
                // see below for when these are each called.
                interceptFrom().process(new MyInterceptProcessor("interceptFrom"));
                intercept().process(new MyInterceptProcessor("intercept")); 
                // note you can do wild cards on interceptFrom and interceptSendToEndPoint.
                interceptSendToEndpoint("stream:out").to("log:interceptSendToEndpoint");
                
                // before means that i think the interceptor is called before the actual camel 
                // method like 'log'.
                from("direct:start").routeId("StevesRouteID") // interceptFrom (before?) 
                  .log("hi") // intercept (before)
                  .log("world") // intercept (before)
                  .to("stream:out"); // intercept (before), interceptSendToEndpoint
            }
        };
    }   
}
