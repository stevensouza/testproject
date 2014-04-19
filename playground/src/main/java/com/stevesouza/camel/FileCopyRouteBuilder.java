package com.stevesouza.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;


/* Camel route builder that copies files from one directory to another and leaves original file in place
 */
public class FileCopyRouteBuilder extends RouteBuilder {
    public FileCopyRouteBuilder() {
    }// body=${body}

    @Override
    public void configure() throws Exception {
        from("file:/Users/stevesouza/gitrepo/testproject/playground/src/main/resources/data/in?noop=true").
                setHeader("myheader", constant("my first header value")).
                log("messageid=${id}, myheader=${headers.myheader}, allheaders=${headers}").
               //marshal().json(JsonLibrary.Jackson).
                process(new MessagePeeker()).
                to("file:/Users/stevesouza/gitrepo/testproject/playground/src/main/resources/data/out").
                log("messageid=${id}, myheader=${headers.myheader}, allheaders=${in.headers}");
    }
}