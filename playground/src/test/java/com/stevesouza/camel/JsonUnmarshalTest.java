/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.camel;

import com.stevesouza.Utils;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

/**
 * convert from json to pojo - i.e. unmarshal
 * @author stevesouza
 */
public class JsonUnmarshalTest  extends CamelTestSupport {
    @EndpointInject(uri = "mock:result")
    protected MockEndpoint resultEndpoint;

    @Produce(uri = "direct:start")
    protected ProducerTemplate template;
    
    
    @Test
    public void testUnmarshal() throws Exception {
        String contents = Utils.readFile(Utils.getIntput2DirPlain()+"/personsnamexstream.json");
        resultEndpoint.message(0).body().convertToString().contains("joel");
        template.sendBody(contents);
        
        resultEndpoint.assertIsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
               // jackson at least by default required PersonsName.class whereas xstream doesn't (class name is in the json)
               from("direct:start")
               .unmarshal().json().to("mock:result");
          }
        };
    }   
    
}
