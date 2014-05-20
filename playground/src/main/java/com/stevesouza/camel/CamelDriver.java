package com.stevesouza.camel;

import org.apache.camel.*;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * Program that launches various camel tests.
 */
public class CamelDriver {

    public static void main(String[] ags) throws Exception {
        CamelContext camel = new DefaultCamelContext();
        camel.addStartupListener(new StartupListener() {
            @Override
            public void onCamelContextStarted(CamelContext context, boolean alreadyStarted) throws Exception {
                System.out.println("Seeing when startup strategy is called.  CamelContext=" + context);
            }
        });
        camel.addRoutes(new FileCopyRouteBuilder());
        camel.addRoutes(new MessageToFileRouteBuilder());
        camel.addRoutes(new PojoToJsonToFileRouteBuilder());
        camel.addRoutes(new FileToJsonToPojoRouteBuilder());
        camel.addRoutes(new PojoToJsonToFileRouteBuilderXstream());
        camel.addRoutes(new FileToJsonToPojoRouteBuilderXstream());

        ProducerTemplate template = camel.createProducerTemplate();

        camel.start();

        // Send to a specific queue
        template.sendBodyAndHeader("direct:messagetofile", "<hello>world!</hello>", "filename", "helloworld.txt");
        template.sendBodyAndHeader("direct:messagetofile", "<hello>steve!</hello>", "filename", "hellosteve.txt");
        template.sendBody("direct:personsname", new PersonsName("steve","souza"));
        template.sendBody("direct:personsname_xstream", new PersonsName("joel","souza"));


        Thread.sleep(10000);
        camel.stop();
    }


}
