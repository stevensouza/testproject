/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stevesouza.camel.examples;

import com.stevesouza.camel.BaseRouteBuilder;

/**
 * sample calling a bean method in the route.
 * <p>
 * bean is passed the message body and return value replaces it
 *
 * @author stevesouza
 */
public class BeanCallRouteBuilder extends BaseRouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:simpleroute").
                bean(BeanCallRouteBuilder.class, "toXml").
                to("log:steves_output");
    }

    public String toXml(String body) {
        return "<myxml>" + body + "</myxml>";
    }

    public static void main(String[] args) throws Exception {
        CamelDriver camelDriver = new CamelDriver(new BeanCallRouteBuilder());
        camelDriver.start();
        camelDriver.getTemplate().sendBody("direct:simpleroute", "hello world message!");
        camelDriver.stop();
    }
}
