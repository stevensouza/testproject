package com.stevesouza.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * Created by stevesouza on 4/12/14.
 */
public class CamelDriver {

    public static void main(String[] ags) throws Exception {
        CamelContext camel = new DefaultCamelContext();
        camel.addRoutes(new FileCopyRouteBuilder());
        camel.start();
        Thread.sleep(10000);
        camel.stop();
    }


}
