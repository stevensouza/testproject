package com.stevesouza.camel;

import org.apache.camel.builder.RouteBuilder;


/* Camel route builder that copies files from one directory to another and leaves original file in place
 */
public class MessageToFileRouteBuilder extends RouteBuilder {
    public MessageToFileRouteBuilder() {
    }// body=${body}

    @Override
    public void configure() throws Exception {
        from("direct:messagetofile").
        log("messageid=${id}, myheader=${headers.myheader}, allheaders=${headers}").
                process(new MessagePeeker()).
                to("file:/Users/stevesouza/gitrepo/testproject/playground/src/main/resources/data/out/?fileName=${headers.filename}");
    }
}