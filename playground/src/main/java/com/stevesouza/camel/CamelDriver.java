package com.stevesouza.camel;

import org.apache.camel.*;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * Program that launches various camel tests.
 */
public class CamelDriver {

    public static void main(String[] ags) throws Exception {
        CamelContext camel = new DefaultCamelContext();
        camel.addRoutes(new FileCopyRouteBuilder());
        camel.addRoutes(new MessageToFileRouteBuilder());
        camel.addRoutes(new PojoToJsonToFileRouteBuilder());
        camel.addRoutes(new FileToJsonToPojoRouteBuilder());
        ProducerTemplate template = camel.createProducerTemplate();

        camel.start();

        // Send to a specific queue
        template.sendBodyAndHeader("direct:messagetofile", "<hello>world!</hello>", "filename", "helloworld.txt");
        template.sendBodyAndHeader("direct:messagetofile", "<hello>steve!</hello>", "filename", "hellosteve.txt");
        template.sendBody("direct:personsname", new PersonsName("joel","souza"));

        Thread.sleep(10000);
        camel.stop();
    }


}
