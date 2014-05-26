package com.stevesouza.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.statements.SpringRepeat;


/* Camel route builder that copies files from one directory to another and leaves original file in place
 */
@Component
public class FileCopyRouteBuilder extends SpringRouteBuilder {
    public FileCopyRouteBuilder() {
    }// body=${body}

    @Override
    public void configure() throws Exception {
        from("file:/Users/stevesouza/gitrepo/testproject/playground/src/main/resources/data/in?noop=true").
                routeId(getClass().getSimpleName()).
                setHeader("myheader", constant("my first header value")).
                log("messageid=${id}, myheader=${headers.myheader}, allheaders=${headers}").
               //marshal().json(JsonLibrary.Jackson).
                process(new MessagePeeker()).
                to("file:/Users/stevesouza/gitrepo/testproject/playground/src/main/resources/data/out").
                log("messageid=${id}, myheader=${headers.myheader}, allheaders=${in.headers}");
    }
}