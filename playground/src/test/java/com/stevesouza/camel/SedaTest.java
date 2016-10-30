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
 *
 * @author stevesouza
 */
public class SedaTest extends CamelTestSupport {
 
    @EndpointInject(uri = "mock:result")
    protected MockEndpoint resultEndpoint;
        
    @Produce(uri = "direct:start")
    protected ProducerTemplate template;


    @Test
    public void testSeda()  throws Exception {
        resultEndpoint.allMessages().body().contains("my seda message");
        resultEndpoint.expectedMessageCount(10);
        for (int i=0;i<10;i++) {
            template.sendBody("my seda message"+i);
        }
        
        resultEndpoint.assertIsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                from("direct:start").to("seda:orders");                
                from("seda:orders").to("mock:result");
            }
        };
    }   
    
}
