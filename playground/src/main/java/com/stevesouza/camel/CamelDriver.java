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
        Thread.sleep(20000);
        camel.stop();
    }

    private static class FileCopyRouteBuilder extends RouteBuilder {

        // body=${body}
        @Override
        public void configure() throws Exception {
            from("file:/Users/stevesouza/gitrepo/testproject/playground/src/main/resources/data/in?noop=true").
            setHeader("myheader", constant("my first header value")).
            log("messageid=${id}, myheader=${headers.myheader}, allheaders=${headers}").
            to("file:/Users/stevesouza/gitrepo/testproject/playground/src/main/resources/data/out").
                    log("messageid=${id}, myheader=${headers.myheader}, allheaders=${in.headers}");
        }
    }
}
