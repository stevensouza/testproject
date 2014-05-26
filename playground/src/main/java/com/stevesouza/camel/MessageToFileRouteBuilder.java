package com.stevesouza.camel;

import org.apache.camel.Processor;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Named;


/* Camel route builder that copies files from one directory to another and leaves original file in place
 */
@Component
public class MessageToFileRouteBuilder extends SpringRouteBuilder {
    // (Processor)getApplicationContext().getBean("messagePeeker")
   @Inject
   @Named("messagePeeker")
     private Processor messagePeeker;
    public MessageToFileRouteBuilder() {
    }// body=${body}

    @Override
    public void configure() throws Exception {
       // messagePeeker = (Processor)getApplicationContext().getBean("messagePeeker");
        from("direct:messagetofile").
                routeId(getClass().getSimpleName()). // note this gives the route a name in the jmx console.
                log("messageid=${id}, myheader=${headers.myheader}, allheaders=${headers}").
                process(messagePeeker).
                to("file:/Users/stevesouza/gitrepo/testproject/playground/src/main/resources/data/out/?fileName=${headers.filename}");
    }
}