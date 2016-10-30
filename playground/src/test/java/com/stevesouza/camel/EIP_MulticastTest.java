/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.camel;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

/**
 * Same message to multiple endpoints. http://camel.apache.org/multicast.html
 * 
 * 
 *
 * @author stevesouza
 */
public class EIP_MulticastTest extends CamelTestSupport {
 
    @EndpointInject(uri = "mock:result1")
    protected MockEndpoint resultEndpoint1;
    @EndpointInject(uri = "mock:result2")
    protected MockEndpoint resultEndpoint2;
        
    @Produce(uri = "direct:start")
    protected ProducerTemplate template;


    @Test
    public void testMulticast()  throws Exception {
        String MESSAGE = "my message";
        resultEndpoint1.message(0).body().contains(MESSAGE);
        resultEndpoint2.message(0).body().contains(MESSAGE);
        
        template.sendBody(MESSAGE);
        
        resultEndpoint1.assertIsSatisfied();
        resultEndpoint2.assertIsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                // parallelProcessing() is not required.
                from("direct:start").routeId("multicast EIP test").multicast().parallelProcessing().to("mock:result1", "mock:result2");                
            }
        };
    }   
    
}
