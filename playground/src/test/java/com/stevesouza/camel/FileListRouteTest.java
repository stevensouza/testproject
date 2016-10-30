/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.camel;

import com.stevesouza.Utils;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import org.springframework.stereotype.Component;

/**
 *
 * @author stevesouza
 */
@Component
 public class FileListRouteTest extends CamelTestSupport {

   
    @Test
    public void testRun()   {
    }

    private String dir = Utils.getInputDir();
    
    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
              from(dir+"?noop=true").to("stream:out");      
            }
        };
    }  
}